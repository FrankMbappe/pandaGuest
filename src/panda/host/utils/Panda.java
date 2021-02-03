package panda.host.utils;


import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import panda.guest.config.Configs;
import panda.guest.remote.Stub;
import panda.guest.ui.scenes.PandaScene;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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
    public static final String DEFAULT_USER_GUEST_SESSION_NAME = "Guest";


    // Marshalling and Unmarshalling patterns (@DEPRECATED)
    @Deprecated
    public enum PandaOperation {
        PANDAOP_REQUEST_GET_POSTS, PANDAOP_RESPONSE_GET_POSTS,
        PANDAOP_REQUEST_GET_CONNECTION, PANDAOP_RESPONSE_GET_CONNECTION
    }

    @Deprecated
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
        if(Current.serverIsRunning.getValue()){
            new Stub(true).unregister();
            System.out.println("[Panda, exit()] | Successfully unregistered.");
        }
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
            return sizeInMB + " MB";
        } else {
            return convertToKB(size) + " KB";
        }
    }



    /* ---  FXML  --- */

    // Hiding nodes in a scene
    public static void setNodeVisibility(boolean isVisible, Node... nodes) {
        for (Node node: nodes) {
            // Associating the fact that a node is visible, to the fact that it currently exists
            node.managedProperty().bind(node.visibleProperty());
            // Hiding the node
            node.setVisible(isVisible);
        }
    }

    // Switching scenes
    public static void switchScene(Scene currentScene, PandaScene nextScene) {
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
    public static File openFileDialog(Node aNode, String... extensions){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file");

        for (String extension : extensions){
            fileChooser.getExtensionFilters().add(gnrtExtensionFilter(extension));
        }

        return fileChooser.showOpenDialog(aNode.getScene().getWindow());
    }

    // Save a file using a FileDialog
    public static boolean saveFileDialog(Node aNode, String fileName, byte[] fileContent, String fileExt){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save a file");

        // I set the initial file name when opening the FileDialog
        fileChooser.setInitialFileName(fileName);

        // I want to use 2 filters - The first for all the files and the other for the specific extension
        var filterAll = gnrtExtensionFilter("");
        var filterFileExt = gnrtExtensionFilter(fileExt); // e.g. PDF Files, pdf

        fileChooser.getExtensionFilters().addAll(filterAll, filterFileExt);
        fileChooser.setSelectedExtensionFilter(filterFileExt);

        // The showSaveDialog() returns an empty file, that will have to be written using the fileContent arg
        File emptyCreatedFile = fileChooser.showSaveDialog(aNode.getScene().getWindow());

        if (emptyCreatedFile != null){
            try{
                FileUtils.writeByteArrayToFile(emptyCreatedFile, fileContent);
                return true;

            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    // Getting a file extension filter
    public static FileChooser.ExtensionFilter gnrtExtensionFilter(String extension){
        if (extension.equals("")){
            return  new FileChooser.ExtensionFilter("All files", "*");
        }
        return new FileChooser.ExtensionFilter(extension.toUpperCase() + " files", "*." + extension);
    }


}
