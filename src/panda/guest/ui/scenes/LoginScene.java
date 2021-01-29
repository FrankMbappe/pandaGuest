package panda.guest.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginScene implements PandaScene {
    Scene loginScene;

    public LoginScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/login.fxml"));
        this.loginScene = new Scene(root);
    }

    @Override
    public Scene get() {
        return loginScene;
    }

}
