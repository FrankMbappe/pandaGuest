package panda.guest.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import panda.guest.config.Configs;
import panda.guest.remote.Stub;
import panda.guest.ui.scenes.AddPostScene;
import panda.host.utils.Current;
import panda.host.utils.Panda;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try{
            // SERVER@REGISTER
            Current.serverIsRunning.addListener(event -> {
                if (Current.serverIsRunning.getValue()){
                    new Stub(true).register();
                }
            });

            // If a valid auth exists in the configs' file
//            if(Configs.fileIsValid()){
//                // The app goes directly to Home using it
//                Current.auth = Configs.getSavedAuth();
//                primaryStage.setScene(new HomeScene().get());
//
//            } else {
//                // Else, the app asks the user to login
//                primaryStage.setScene(new LoginScene().get());
//            }

            // @TEST

            Current.auth = Configs.getSavedAuth();
            primaryStage.setScene(new AddPostScene().get());

            primaryStage.getIcons().add(new Image(Panda.PATH_TO_APP_ICON));
            primaryStage.setTitle(Panda.APP_NAME);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Panda.exit();
    }

}
