package panda.guest.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import panda.guest.config.Configs;
import panda.guest.ui.scenes.HomeScene;
import panda.guest.ui.scenes.LoginScene;
import panda.host.utils.Current;
import panda.host.utils.Panda;

import java.util.TimerTask;

import static panda.host.utils.Panda.PATH_TO_APP_ICON;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // If a valid auth exists in the configs' file
//        if(Configs.fileIsValid()){
//            // The app goes directly to Home using it
//            Current.auth = Configs.getSavedAuth();
//            primaryStage.setScene(new HomeScene().get());
//
//        } else {
//            // Else, the app asks the user to login
//            primaryStage.setScene(new LoginScene().get());
//        }
        Current.auth = Configs.getSavedAuth();
        primaryStage.setScene(new HomeScene().get());

        primaryStage.getIcons().add(new Image(Panda.PATH_TO_APP_ICON));
        primaryStage.setTitle(Panda.APP_NAME);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Panda.exit();
    }

}
