package requester;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import shared.model.ListLine;
import shared.model.MyReply;
import shared.model.MyRequest;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RequesterController implements Initializable {
    private Connection connection;
    private Session session;
    private Destination replyDestination;
    private String replyDestinationString;
    private Destination requestDestination;
    private MessageProducer producer;

    private HashMap<String, MyRequest> requestsHashMap;

    @FXML
    public TextField tfMessage;
    @FXML
    public ListView<ListLine<MyRequest, MyReply>> lvRequestReply;

    private final Logger logger = Logger.getLogger(RequesterController.class.getName());

    private ListLine<MyRequest, MyReply> getRequestReply(MyRequest request) {
        for (ListLine<MyRequest, MyReply> listLine : lvRequestReply.getItems()) {
            if (listLine.getRequest() != null)
                if (listLine.getRequest().equals(request)) {
                    return listLine;
                }
        }
        return null;
    }

    @FXML
    public void btnSendClicked() {

        String requestText = tfMessage.getText();
         /// TODO add fields to MyRequest and initialize values here. Add controls on the form if necessary...
        logger.log(Level.INFO, "Sending the request " + requestText);

        try {
            MyRequest myRequest = new MyRequest(requestText);
            Message message = session.createTextMessage(myRequest.getSerialized());

            ListLine<MyRequest, MyReply> listLine = new ListLine<>(myRequest);
            lvRequestReply.getItems().add(listLine);

            message.setJMSReplyTo(this.replyDestination);
            producer.send(message);

            requestsHashMap.put(message.getJMSMessageID(), myRequest);
        } catch(JMSException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing requester application...");
        //@TODO Add code for starting up the application here.

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        requestsHashMap = new HashMap<>();

        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            replyDestinationString = "replyQueue" + UUID.randomUUID();
            replyDestination = session.createQueue(replyDestinationString);
            requestDestination = session.createQueue("requestsQueue");
            producer = session.createProducer(requestDestination);
            MessageConsumer consumer = session.createConsumer(replyDestination);

            consumer.setMessageListener(mesg -> {
                try {
                    String body = ((TextMessage)mesg).getText();
                    MyRequest request = this.requestsHashMap.get(mesg.getJMSCorrelationID());
                    MyReply reply = MyReply.getDeserialized(body);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            ListLine<MyRequest, MyReply> listLine = getRequestReply(request);

                            if(listLine != null) {
                                listLine.setReply(reply);
                                lvRequestReply.refresh();
                            }
                        }
                    });
                } catch(JMSException e) {
                    System.out.println(e.getMessage());
                }
            });

            connection.start();
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This is executed when the form is closed. See SenderMain
     */
    public void stop() {
        logger.info("Stopping requester application...");
        try {
            if (producer != null) {
                producer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch(JMSException e) {
            System.out.println(e.getMessage());
        }
    }
}
