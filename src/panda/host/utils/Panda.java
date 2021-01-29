package panda.host.utils;


import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import panda.guest.config.Configs;
import panda.guest.ui.scenes.PandaScene;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Panda.
 */
public class Panda {
    // Default remote object URL
    public static final String DEFAULT_REMOTE_URL = "rmi://localhost/panda";

    // Default app port
    public static final int DEFAULT_PORT = 77;

    // Default file path
    public static final String PATH_TO_CONFIG_FILE = "src/panda/guest/config/configs.json";
    public static final String PATH_TO_APP_ICON = "panda/guest/ui/resources/images/logo.png";

    // Default values
    public static final String APP_NAME = "PandaGuest";
    public static final String DEFAULT_SPLIT_CHAR = ";";
    public static final String DEFAULT_DATE_FORMAT = "E, dd MMM yyyy";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm";
    public static final String DEFAULT_GUEST_NAME = "Guest";


    // Marshalling and Unmarshalling patterns
    public enum PandaOperation {
        PANDAOP_REQUEST_GET_POSTS, PANDAOP_RESPONSE_GET_POSTS,
        PANDAOP_REQUEST_GET_CONNECTION, PANDAOP_RESPONSE_GET_CONNECTION
    }
    public static final HashMap<PandaOperation, String> PANDA_ENCODING_PATTERNS = new HashMap<>(){{
        put(PandaOperation.PANDAOP_REQUEST_GET_CONNECTION,
                "panda@connect?user='%s'&password='%s'");
        put(PandaOperation.PANDAOP_REQUEST_GET_POSTS,
                "panda@getpost?all='%s'&filetype='%s'&class='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_CONNECTION,
                "panda@connect?rescode='%s'&user='%s'&grant='%s'");
        put(PandaOperation.PANDAOP_RESPONSE_GET_POSTS,
                "panda@getpost?rescode='%s'&data='%s'");
    }};

    // Closing PandaGuest app
    public static void exit(){
        Configs.saveAuth(Current.auth);
        System.exit(0);
    }

    // Getting a formatted date
    public static String getFormattedDate(@NotNull LocalDateTime dateTime){

        LocalDate date = dateTime.toLocalDate();

        // Initializing the prefix that will contains either 'Today', 'Yesterday' or the corresponding date
        String prefix = dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        String time = dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));

//        System.out.println(String.format("[Panda, getFormattedDate()] | DateTime to date: '%s'.", prefix));

        if (LocalDate.now().equals(date)) {
            prefix = "Today";
        } else if (LocalDate.now().minusDays(1).equals(date)) {
            prefix = "Yesterday";
        }

        return String.format("%s, %s", prefix, time);
    }

    // Converting a size from long to KB
    public static double convertToKB(long size){
        return size >> 10;
    }

    // Converting a size from long to MB
    public static double convertToMB(long size){
        return size >> 20;
    }

    // Converting a size from long whether KB or MB, depending on the value
    public static String convertLongSizeToString(long size){
        double sizeInMB = convertToMB(size);
        if(sizeInMB >= 1){
            return sizeInMB + "MB";
        } else {
            return convertToKB(size) + "KB";
        }
    }



    /*     FXML     */

    // Hiding nodes in a scene
    public static void setNodeVisibility(boolean isVisible, Node... nodes){
        for (Node node: nodes) {
            // Associating the fact that a node is visible, to the fact that it currently exists
            node.managedProperty().bind(node.visibleProperty());
            // Hiding the node
            node.setVisible(isVisible);
        }
    }

    // Switching scenes
    public static void switchScene(Scene currentScene, PandaScene nextScene){
        // To switch scenes, I firstly get the window
        Stage window = (Stage) currentScene.getWindow();

        try{
            // Then I switch to a new scene
            window.setScene(nextScene.get());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Iterating through a set of textFields to check if at least one of them is empty
    public static boolean txtsAreNotEmpty(TextField... textFields){
        boolean txtsAreNotEmpty = true;
        for(var txt : textFields){
            // A textfield is not empty if when deleting all spaces, the text isn't equal to ""
            txtsAreNotEmpty = txtsAreNotEmpty && !txt.getText().replaceAll("\\s", "").equals("");
        }
        return txtsAreNotEmpty;
    }

    // Getting a file from a FileDialog
    public static File openFileDialog(Node aNode, FileChooser.ExtensionFilter... filters){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");

        fileChooser.getExtensionFilters().addAll(filters);

        return fileChooser.showOpenDialog(aNode.getScene().getWindow());
    }

    // Getting a file extension filter
    public static  FileChooser.ExtensionFilter genFileFilter(String name, String extension){
        if (extension.equals("")){
            return  new FileChooser.ExtensionFilter(name, "*");
        }
        return new FileChooser.ExtensionFilter(name, "*." + extension);
    }


}
