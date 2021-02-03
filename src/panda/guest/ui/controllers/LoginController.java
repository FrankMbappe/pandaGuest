package panda.guest.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import panda.guest.config.Configs;
import panda.guest.remote.Stub;
import panda.guest.ui.scenes.HomeScene;
import panda.host.models.Authentication;
import panda.host.models.filters.Credentials;
import panda.host.utils.Current;

import static panda.host.utils.Panda.*;

public class LoginController {
    public Circle circ_serverStatus;
    public Label lb_serverStatus;
    public TextField txt_username;
    public Label lb_hintLoginError;
    public PasswordField txt_password;
    public CheckBox check_keepLoggedIn;
    public Button btn_validate;
    public Button btn_joinAsGuest;


    public void initialize(){
        // Automatically updating the server status
        setServerStatusInRealtime();

        // Hiding the error label
        setNodeVisibility(false, lb_hintLoginError);
    }


    // ACTION EVENTS
    public void validate(ActionEvent actionEvent) {
        boolean fieldsAreValid = txtsAreNotEmpty(txt_username, txt_password);

        // I display an error message if fields value are not valid
        if (fieldsAreValid){
            setNodeVisibility(false, lb_hintLoginError);

            // I create a cred object to log in the user using the stub
            Credentials credentials = new Credentials(txt_username.getText(), txt_password.getText());
            // I retrieve the auth object from the server
            Authentication auth = new Stub(true).logUserIn(credentials);

            // If everything is okay,
            if (auth.isValid() && Current.serverIsRunning.getValue()){
                // * and the user wants to remain logged in,
                    if(check_keepLoggedIn.isSelected()){
                        // I save that auth and I go to the home scene
                        Configs.saveAuth(auth);
                    }
                // * and the user doesn't want to remain logged in,
                    else {
                        // The auth isn't saved, and is just used to update the current auth
                        Current.auth = auth;
                    }

                try {
                    switchScene(btn_validate.getScene(), new HomeScene());

                } catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                lb_hintLoginError.setText("Sorry, no users found with these credentials.");
                setNodeVisibility(true, lb_hintLoginError);
            }

        } else {
            lb_hintLoginError.setText("Please make sure to fill every required field");
            setNodeVisibility(true, lb_hintLoginError);
        }

    }

    public void joinAsGuest(ActionEvent actionEvent) {
        // I get a default auth (with Panda.DEFAULT_GUEST_NAME as the username), then I take it as my current auth
        Current.auth = new Authentication(Authentication.Status.GRANTED, Authentication.Type.GUEST);
        try {
            switchScene(btn_joinAsGuest.getScene(), new HomeScene());

        } catch (Exception e){
            e.printStackTrace();
        }

    }


    // @REALTIME
    void setServerStatusInRealtime(){
        Current.serverIsRunning.addListener(event -> {
            setServerStatus(Current.serverIsRunning.getValue());
            System.out.println("[LoginCtrl] | Server is running: " + Current.serverIsRunning.getValue());
        });
    }


    // LOCAL METHODS
    void setServerStatus(boolean serverIsRunning){
        if(serverIsRunning){
            circ_serverStatus.getStyleClass().setAll("circ-server-status", "connected");
            btn_validate.setDisable(false);
            btn_joinAsGuest.setDisable(false);
            //lb_serverStatus.setText("Server is connected");
        } else {
            circ_serverStatus.getStyleClass().setAll("circ-server-status");
            btn_validate.setDisable(true);
            btn_joinAsGuest.setDisable(true);
            //lb_serverStatus.setText("Server is disconnected");
        }
    }

}

