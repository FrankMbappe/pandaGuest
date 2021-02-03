package panda.guest.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AddPostScene implements PandaScene {
    Scene addPostScene;

    public AddPostScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/add_post.fxml"));
        this.addPostScene = new Scene(root);
    }

    @Override
    public Scene get() {
        return addPostScene;
    }
}
