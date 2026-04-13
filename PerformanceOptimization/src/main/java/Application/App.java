package Application;

import Controller.AppController;
import Repo.RepoDB;
import Service.ServiceApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("Setting up services");
        RepoDB repo = new RepoDB();
        ServiceApp service = new ServiceApp(repo);

        logger.info("setting up controller");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("App");
        stage.setScene(scene);

        AppController controller = fxmlLoader.getController();
        controller.setService(service);

        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
