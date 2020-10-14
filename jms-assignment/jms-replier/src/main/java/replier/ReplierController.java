package replier;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import shared.model.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ReplierController implements Initializable {
    private Connection connection;
    private Session session;
    private Destination sendDestination;
    private MessageProducer producer;

    @FXML
    TextField tfAnswer;

    //@TODO each time a new request arrives, add a new item to lvRequestReply: new ListLine<MyRequest, MyReply>(request)
    @FXML
    ListView<ListLine<MyRequest, MyReply>> lvRequestReply;

    private final Logger logger =  Logger.getLogger(ReplierController.class.getName());

    private HashMap<MyRequest, Message> requestHashMap;


    @FXML
    public void btnSendClicked() {

        String replyText = tfAnswer.getText();
        MyReply myReply = new MyReply(replyText);

        ListLine<MyRequest, MyReply> listLine = lvRequestReply.getSelectionModel().getSelectedItem();
        if (listLine != null) {
            MyRequest request = listLine.getRequest();
            listLine.setReply(myReply);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Message message = session.createTextMessage(myReply.getSerialized());
                        Message requestMessage = requestHashMap.get(request);

                        message.setJMSCorrelationID(requestMessage.getJMSMessageID());
                        Destination returnAddress = requestHashMap.get(request).getJMSReplyTo();
                        producer.send(returnAddress,message);
                        lvRequestReply.refresh();
                    } catch(JMSException e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
            logger.info("Sending replyText " + replyText + " for request " + request);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("Initializing replier application...");

        requestHashMap = new HashMap<>();

        try {
            ConnectionFactory connectionFactory;
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            sendDestination = session.createQueue("requestsQueue");
            producer = session.createProducer(null);
            MessageConsumer consumer = session.createConsumer(sendDestination);

            consumer.setMessageListener(mesg -> {
                try {
                    String body = ((TextMessage) mesg).getText();

                    MyRequest request = MyRequest.getDeserialized(body);
                    this.lvRequestReply.getItems().add(new ListLine<MyRequest, MyReply>(request));

                    requestHashMap.put(request, mesg);
                } catch(JMSException e) {
                    System.out.println(e.getMessage());
                }
            });


            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        logger.info("Stopping replier application...");
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
