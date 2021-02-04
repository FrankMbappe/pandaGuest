package panda.guest.ui.scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class StatsScene implements PandaScene {
    Scene statsScene;

    public StatsScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/stats.fxml"));
        this.statsScene = new Scene(root);
    }

    @Override
    public Scene get() {
        return statsScene;
    }
}
