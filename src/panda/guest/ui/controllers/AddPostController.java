package panda.guest.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import panda.guest.remote.Stub;
import panda.guest.ui.scenes.HomeScene;
import panda.host.models.Post;
import panda.host.utils.Current;
import panda.host.utils.Panda;

import java.io.File;

import static panda.guest.ui.models.UIModel.showErrorAlert;
import static panda.guest.ui.models.UIModel.showInfoAlert;
import static panda.host.utils.Panda.setNodeVisibility;
import static panda.host.utils.Panda.switchScene;

public class AddPostController {
    public Button btn_back;
    public Circle circ_serverStatus;
    public Button btn_Browse;
    public VBox vbox_BrowseFile;
    public Button btn_EditFile;
    public HBox hbox_SelectedFile;
    public TextArea txt_message;
    public TextField txt_tags;
    public Button btn_validate;
    public Button btn_cancel;
    public Label lb_fileName;
    public Label lb_fileInfo;
    public StackPane stk_filePhoto;

    File selectedFile;


    public void initialize(){
        // Initializing the server status
        setServerStatus(Current.serverIsRunning.getValue());

        // Automatically updating the server status
        setServerStatusInRealtime();

        // Initializing the file container
        initFileBox();

    }


    // @REALTIME
    void setServerStatusInRealtime(){
        Current.serverIsRunning.addListener(event -> {
            setServerStatus(Current.serverIsRunning.getValue());
            System.out.println("[LoginCtrl] | Server is running: " + Current.serverIsRunning.getValue());
        });
    }


    // ACTION EVENTS
    public void backHome(ActionEvent event) {
        try{
            switchScene(btn_back.getScene(), new HomeScene());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void browseFile(ActionEvent event) {
        File file = Panda.openFileDialog(btn_Browse);

        if (file != null) {
            selectedFile = file;
            setSelectedFile(selectedFile);

        } else {
            if (selectedFile == null) {
                setNodeVisibility(true, vbox_BrowseFile);
                setNodeVisibility(false, hbox_SelectedFile);
            }
        }
    }

    public void validate(ActionEvent event) {
        System.out.println("[AddPostCtrl] | Generated post:\n" + getPost());

        boolean thePostHasBeenAdded = new Stub(false).addPost(getPost());

        if(thePostHasBeenAdded){
            showInfoAlert("Successful operation","Your post has been successfully added !");

            backHome(event);

        } else {
            showErrorAlert("Sorry, something went wrong while adding your post.\nPlease try again.");
        }
    }

    public void cancel(ActionEvent event) {
        // Reusability
        backHome(event);
    }


    // LOCAL METHODS
    void initFileBox(){
        setNodeVisibility(true, vbox_BrowseFile);
        setNodeVisibility(false, hbox_SelectedFile);
    }

    void setSelectedFile(@NotNull File file){
        String completeName = FilenameUtils.getName(file.getName());
        String extension = FilenameUtils.getExtension(file.getName());
        long size = FileUtils.sizeOf(file);

        // File photo
        stk_filePhoto.getStyleClass().setAll("stk-post-file-photo", extension);

        // File name
        lb_fileName.textProperty().setValue(completeName);

        // File info
        lb_fileInfo.textProperty().setValue(String.format("%s File | %s",
                extension.toUpperCase(), Panda.convertLongSizeToString(size)));

        setNodeVisibility(false, vbox_BrowseFile);
        setNodeVisibility(true, hbox_SelectedFile);

    }

    Post getPost(){
        // Considering that the user's current auth here is valid
        String userId = Current.auth.getUser().getUsername();

        // Include the selected file if it exists, otherwise not
        return  (selectedFile != null) ?
                new Post(userId, txt_message.getText(), txt_tags.getText(), selectedFile) :
                new Post(userId, txt_message.getText(), txt_tags.getText());
    }

    void setServerStatus(boolean serverIsRunning){
        if(serverIsRunning){
            circ_serverStatus.getStyleClass().setAll("circ-server-status", "connected");
            btn_validate.setDisable(false);
        } else {
            circ_serverStatus.getStyleClass().setAll("circ-server-status");
            btn_validate.setDisable(true);
        }
    }

}
