package replier;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ReplierMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String fxml = "replier.fxml";
        URL url = getClass().getClassLoader().getResource(fxml);
        if (url != null) {
            FXMLLoader loader = new FXMLLoader(url);
            ReplierController controller = new ReplierController();
            loader.setController(controller);
            Parent root = loader.load();
            primaryStage.setTitle("JMS Replier");
            primaryStage.setScene(new Scene(root, 486, 318));
            primaryStage.setOnCloseRequest(t -> {
                controller.stop();
                Platform.exit();
                System.exit(0);
            });

            primaryStage.show();
        } else {
            System.err.println("Error: Could not load frame from "+ fxml);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
