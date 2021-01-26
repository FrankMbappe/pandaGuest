package panda.guest.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;
import panda.guest.remote.Stub;
import panda.host.utils.Panda;

import java.util.Timer;
import java.util.TimerTask;

public class LoginController {
    Stub stub = new Stub(false);

    public Circle circ_serverStatus;
    public Label lb_serverStatus;
    public TextField txt_db_username;
    public Label lb_hintLoginError;
    public PasswordField txt_db_password;
    public CheckBox check_keepLoggedIn;
    public Button btn_validate;
    public Button btn_cancel;

    public void initialize(){
        setServerStatus(stub.serverIsAccessible());

//        Thread async = new Thread(() -> {
//            while (true) {
//                setServerStatus(new Stub(false).serverIsAccessible());
//            }
//        });
//        async.start();

        TimerTask checkServerStatus = new TimerTask() {
            @Override
            public void run() {
                setServerStatus(new Stub(false).serverIsAccessible());
            }
        };
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(checkServerStatus, 1000, 1000);

        txt_db_username.setText("After timer start");

    }

    public void validate(ActionEvent actionEvent) {

    }

    public void cancel(ActionEvent actionEvent) {
        Panda.exit();
    }

    public void setServerStatus(boolean isConnected){
        if(isConnected){
            circ_serverStatus.getStyleClass().setAll("circ-server-status", "connected");
            //lb_serverStatus.setText("Server is connected");
        } else {
            circ_serverStatus.getStyleClass().setAll("circ-server-status");
            //lb_serverStatus.setText("Server is disconnected");
        }
    }

}

