package panda.guest.ui;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import panda.guest.config.Configs;
import panda.guest.remote.Stub;
import panda.guest.ui.scenes.HomeScene;
import panda.guest.ui.scenes.LoginScene;
import panda.host.utils.Current;
import panda.host.utils.Panda;

import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        try{
            // SERVER@OFFLINE
            // Initially
                checkIfTheServerIsAccessible();
            // Listener
                Current.serverIsRunning.addListener(event -> {
                        // Whenever the server goes offline
                        if (! Current.serverIsRunning.getValue()){
                            // The app starts continuously checking if it is accessible
                            checkIfTheServerIsAccessible();
                        }
                    });

            // If a valid auth exists in the configs' file
            if(Configs.fileIsValid()){
                // The app goes directly to Home using it
                Current.auth = Configs.getSavedAuth();
                primaryStage.setScene(new HomeScene().get());

            } else {
                // Else, the app asks the user to login
                primaryStage.setScene(new LoginScene().get());
            }

            // @TEST
//            Current.auth = Configs.getSavedAuth();
//            primaryStage.setScene(new HomeScene().get());

            primaryStage.getIcons().add(new Image(Panda.PATH_TO_APP_ICON));
            primaryStage.setTitle(Panda.APP_NAME);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void checkIfTheServerIsAccessible(){
        TimerTask check = new TimerTask() {
            @Override
            public void run() {
                // If the server is accessible,
                if (new Stub(false).serverIsAccessible()){

                    // The client register the server
                    new Stub(false).register();

                    // And stop checking
                    cancel();

                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(check, 500, 500);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        Panda.exit();
    }

}
