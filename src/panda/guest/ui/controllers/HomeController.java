package panda.guest.ui.controllers;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import panda.guest.remote.Stub;
import panda.guest.ui.controllers.data.Dummy;
import panda.guest.ui.models.PostVBox;
import panda.guest.ui.models.UIModel;
import panda.guest.ui.models.filtering.PostFilterByFileExt;
import panda.guest.ui.models.filtering.PostFilterByTags;
import panda.guest.ui.models.sorting.PostSorting;
import panda.guest.ui.scenes.AddPostScene;
import panda.guest.ui.scenes.LoginScene;
import panda.host.models.Post;
import panda.host.utils.Current;
import panda.host.utils.Panda;

//import static panda.host.server.SyncChannelImpl.postObservableList;
import java.util.*;

import static panda.guest.ui.models.UIModel.showErrorAlert;
import static panda.guest.ui.models.UIModel.showInfoAlert;
import static panda.host.utils.Panda.*;

public class HomeController {
    public Circle circ_serverStatus;
    public Button btn_postAdd;
    public Button btn_logOut;
    public Button btn_refresh;
    public TextField txt_search;
    public ComboBox<PostSorting> cb_sortBy;
    public ComboBox<PostFilterByFileExt> cb_filterFileExt;
    public ListView<PostVBox> list_postFlow;
    public RadioButton radio_orderAsc;
    public RadioButton radio_orderDesc;

    // POST - Fields
    Label lbPostUserName;
    Label lbPostUserRole;
    Label lbPostTags;
    Label lbPostLbLastUpdate;
    Label lbPostValLastUpdate;
    Label lbPostMessage;
    Label lbPostFileName;
    Label lbPostFileInfo;
    Button btnDownload;


    public void initialize() {

        // @TEST with dummy data
        // Current.postList = Dummy.posts;

        // @INIT Initializing the current post list
        Current.postList.setAll(new Stub(true).getPosts());

        // Hiding nodes depending on the user privileges
        applyPrivileges();

        // Initializing my combo boxes
        initComboBoxes();

        // Initializing my post layout
        initPostsLayout();

        // Automatically updating the server status
        updateContentInRealtime();

    }

    // @REALTIME
    private void updateContentInRealtime() {
        // Updating the server status in realtime
        setServerStatusInRealtime();
        // Updating the post list in realtime
        setPostListInRealtime();
    }

    private void setPostListInRealtime() {
        // Initially
        fillPostLayout(Current.postList);

        // Listener
        Current.postList.addListener((InvalidationListener) event -> {
            // Notifying the user that an update has been done
            notifyPostListHasBeenUpdated();

            // Displaying the updated post list
            fillPostLayout(Current.postList);
        });
    }

    public void setServerStatusInRealtime(){
        // Initially
        setServerStatus(Current.serverIsRunning.getValue());

        // Listener
        Current.serverIsRunning.addListener(event -> {
            setServerStatus(Current.serverIsRunning.getValue());
            System.out.println("[HomeCtrl] | Server is running: " + Current.serverIsRunning.getValue());
        });
    }


    // ACTION EVENTS
    public void addPost(ActionEvent actionEvent) {
        try {
            switchScene(btn_logOut.getScene(), new AddPostScene());

        } catch (Exception e){ e.printStackTrace(); }
    }

    public void logOut(ActionEvent actionEvent) {
        if (Current.auth != null  && Current.auth.isValid()) {
            boolean userHasLoggedOut = new Stub(false).logUserOut(Current.auth.getUser().getUsername());
            if (userHasLoggedOut){
                try {
                    switchScene(btn_logOut.getScene(), new LoginScene());

                } catch (Exception e){ e.printStackTrace(); }

            } else {
                showErrorAlert("Sorry, an error occurred while logging out.\nPlease try again.");
            }
        }
        else {
            Current.auth = null;
            try {
                switchScene(btn_logOut.getScene(), new LoginScene());

            } catch (Exception e){ e.printStackTrace(); }
        }
    }

    public void refresh(ActionEvent actionEvent) {
        // I simply fill the post layout with the post list
        fillPostLayout(Current.postList);
    }

    public void onCbSortByItemSelected(ActionEvent actionEvent) {
        // I get the sorting type depending on which one has been selected
        PostSorting sortingType = cb_sortBy.getValue();

        // Then I sort the displayed posts
        var displayedPosts = getDisplayedPosts();
        if(sortingType != null) sortingType.sort(displayedPosts);

        // And finally, I display the result in my post layout
        fillPostLayout(displayedPosts);
    }

    public void onCbFilterFileExtSelected(ActionEvent actionEvent) {
        // I reset the sorting ComboBox to no item selected
        cb_sortBy.getSelectionModel().clearSelection();

        // This method will filter the post list depending on the selected filter
        fillPostLayout(Current.postList);
    }

    public void onTxtSearchTypingIn(KeyEvent actionEvent) {
        String textTyped = txt_search.textProperty().get();

        if(txtsAreNotEmpty(txt_search)) fillPostLayout(new PostFilterByTags(textTyped).filter(Current.postList));

        else fillPostLayout(Current.postList);
    }

    public void orderAscSelected(ActionEvent event) {
        // Inverting
        radio_orderDesc.setSelected(! radio_orderAsc.isSelected());
        // TODO: ASC-DESC Sorting (Inverting the list each time I check one the radio button)
        onCbSortByItemSelected(event);
    }

    public void orderDescSelected(ActionEvent event) {
        // Inverting
        radio_orderAsc.setSelected(! radio_orderDesc.isSelected());
        onCbSortByItemSelected(event);
    }


    // LOCAL METHODS
    void initComboBoxes() {
        cb_sortBy.getItems().setAll(UIModel.getPostSortingTypes());
        cb_filterFileExt.getItems().setAll(UIModel.getPostFilteringExtensionTypes());
        cb_filterFileExt.getSelectionModel().select(0);
        radio_orderAsc.setSelected(true);
    }

    void initPostsLayout() {
        // To make the post flow container grow when resizing the window
        VBox.setVgrow(list_postFlow, Priority.ALWAYS);

        // Then I instantiate the controls
        lbPostUserName = new Label();
        lbPostUserRole = new Label();
        lbPostTags = new Label();
        lbPostLbLastUpdate = new Label();
        lbPostValLastUpdate = new Label();
        lbPostMessage = new Label();
        lbPostFileName = new Label();
        lbPostFileInfo = new Label();
        btnDownload = new Button();

        // Initializing the post layout
        fillPostLayout(Current.postList);

    }

    void applyPrivileges() {
        if (Current.auth == null || !Current.auth.isValid()){
            Panda.setNodeVisibility(false, btn_postAdd);
        }
    }

    void setServerStatus(boolean serverIsRunning) {
        if (serverIsRunning) circ_serverStatus.getStyleClass().setAll("circ-server-status", "connected");

        else circ_serverStatus.getStyleClass().setAll("circ-server-status");
    }

    void fillPostLayout(ObservableList<Post> posts) {
        // Removing everything inside
        list_postFlow.getItems().clear();

        var postsToDisplay = posts;

        // Filtering using the combo box selected filter
        if (cb_filterFileExt.getValue() != null) postsToDisplay = cb_filterFileExt.getValue().filter(postsToDisplay);

        // Filtering using the search box
        String textSearched = txt_search.textProperty().get();
        if (txtsAreNotEmpty(txt_search)) postsToDisplay = new PostFilterByTags(textSearched).filter(postsToDisplay);

        // Then I add the filtered posts
        for (var post: postsToDisplay){
            list_postFlow.getItems().add(postToUI(post));
        }
    }

    void notifyPostListHasBeenUpdated() {
        // TODO: Display something that indicates that the list has been updated
        System.out.println("[HomeCtrl] | The posts list has been updated.");
    }

    PostVBox postToUI(@NotNull Post post) {
        PostVBox vBoxPost = new PostVBox(post);
        vBoxPost.getStyleClass().add("vbox-post");
            // POST - Title
            HBox hBoxPostTitle = new HBox();
            hBoxPostTitle.getStyleClass().add("hbox-post-title");
                // POST - Title - Username
                HBox hBoxPostUserName = new HBox();
                hBoxPostUserName.getStyleClass().add("hbox-post-user-name");
                    StackPane stkPostUserPhoto = new StackPane();
                    stkPostUserPhoto.getStyleClass().add("stk-post-user-photo"); // Author.Photo
                    VBox vBoxPostUserInfo = new VBox();
                        lbPostUserName = new Label((post.getAuthorId() != null) ? post.getAuthorId() : "PandaHost"); // Author.Name
                        lbPostUserName.getStyleClass().add("lb-post-user-name");
                        lbPostUserRole = new Label("Student"); // Author.Role
                        lbPostUserRole.getStyleClass().add("lb-post-user-role");

                // POST - Title - Space-between
                StackPane stkGrowAlways1 = new StackPane();
                HBox.setHgrow(stkGrowAlways1, Priority.ALWAYS);


                // POST - Title - Info
                HBox hBoxPostInfo = new HBox();
                hBoxPostInfo.getStyleClass().add("hbox-post-info");
                    lbPostTags = new Label(post.getTagsToString()); // Post.Tags
                    lbPostTags.getStyleClass().add("lb-post-tags");
                    Separator separatorV = new Separator();
                    separatorV.getStyleClass().add("separator-v");
                    HBox hBoxPostLastUpdate = new HBox();
                    hBoxPostLastUpdate.getStyleClass().add("hbox-post-last-update");
                        lbPostLbLastUpdate = new Label("Last update:");
                        lbPostLbLastUpdate.getStyleClass().add("lb-post-lb-last-update");
                        lbPostValLastUpdate = new Label(Panda.getFormattedDate(post.getLastUpdate().toLocalDateTime())); // Post.LastUpdate
                        lbPostValLastUpdate.getStyleClass().add("lb-post-val-last-update");

            // POST - Message
            lbPostMessage = new Label(post.getMessage());
            lbPostMessage.getStyleClass().add("lb-post-message");

            // POST - File
            HBox hBoxPostFile = new HBox();
            hBoxPostFile.getStyleClass().addAll("hbox-post-file", post.getFileExt());
            // e.g: hbox-post-file, pdf: will set the red color to the bg

                // POST - File - Info
                HBox hBoxPostFileInfo = new HBox();
                hBoxPostFileInfo.getStyleClass().add("hbox-post-file-info");
                    StackPane stkPostFilePhoto = new StackPane();
                    stkPostFilePhoto.getStyleClass().addAll("stk-post-file-photo", post.getFileExt()); // Post.File.Type
                    VBox vBoxPostFileName = new VBox();
                    vBoxPostFileName.getStyleClass().add("vbox-post-file-name");
                        lbPostFileName = new Label(post.getCompleteFileName()); // Post.File.Name
                        lbPostFileName.getStyleClass().add("lb-post-file-name");
                        lbPostFileInfo = new Label(
                                post.getFileExt().toUpperCase() + " | " + Panda.convertLongSizeToString(post.getFileSize())
                        ); // Post.File.Type + Post.File.Size
                        lbPostFileInfo.getStyleClass().add("lb-post-file-info");

                // POST - File - Space-between
                StackPane stkGrowAlways2 = new StackPane();
                HBox.setHgrow(stkGrowAlways2, Priority.ALWAYS);

                // POST - File - Download
                btnDownload = new Button("Download");
                btnDownload.getStyleClass().add("btn-post-file-download"); // Download()

                btnDownload.setOnAction(event -> {
                    System.out.println(String.format("[HomeCtrl, aPost] | User requested to download '%s' from '%s'.",
                            post.getCompleteFileName(), post.getAuthorId()));

                    // Downloading the file content
                    byte[] fileContent = new Stub(true).downloadPostFile(post);

                    // Saving the file
                    boolean theFileHasBeenSaved = saveFileDialog(list_postFlow, post.getFileName(), fileContent, post.getFileExt());
                    //boolean theFileHasBeenSaved = saveFileDialog(list_postFlow, post.getFileName(), post.getFileToBytes(), post.getFileExt());

                    if (theFileHasBeenSaved) showInfoAlert("Your file has been downloaded and successfully saved");

                });

        // Putting child nodes into corresponding parent nodes (Starting from the bottom and adding gradually)
                        vBoxPostFileName.getChildren().addAll(lbPostFileName, lbPostFileInfo);
                        hBoxPostFileInfo.getChildren().addAll(stkPostFilePhoto, vBoxPostFileName);
                    hBoxPostFile.getChildren().addAll(hBoxPostFileInfo, stkGrowAlways2, btnDownload);

                    hBoxPostLastUpdate.getChildren().addAll(lbPostLbLastUpdate, lbPostValLastUpdate);
                hBoxPostInfo.getChildren().addAll(lbPostTags, separatorV, hBoxPostLastUpdate);

                vBoxPostUserInfo.getChildren().addAll(lbPostUserName, lbPostUserRole);
                hBoxPostUserName.getChildren().addAll(stkPostUserPhoto, vBoxPostUserInfo);

            hBoxPostTitle.getChildren().addAll(hBoxPostUserName, stkGrowAlways1, hBoxPostInfo);

        // The post file vbox is only included if the post contains a file
        if (post.containsAFile())
            vBoxPost.getChildren().addAll(hBoxPostTitle, new Separator(Orientation.HORIZONTAL), lbPostMessage, hBoxPostFile);
        else
            vBoxPost.getChildren().addAll(hBoxPostTitle, new Separator(Orientation.HORIZONTAL), lbPostMessage);

        return vBoxPost;
    }

    ObservableList<Post> getDisplayedPosts(){
        ArrayList<Post> displayedPosts = new ArrayList<>();

        for (PostVBox postVBox : list_postFlow.getItems()){
            displayedPosts.add(postVBox.getPost());
        }

        return FXCollections.observableList(displayedPosts);
    }
}
