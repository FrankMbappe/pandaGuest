package panda.guest.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;
import panda.host.models.Post;
import panda.host.utils.Panda;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;

public class HomeController {
    public Circle circ_serverStatus;
    public Button btn_postAdd;
    public Button btn_logOut;
    public Button btn_refresh;
    public ComboBox cb_sortBy;
    public ComboBox cb_filterFileType;
    public ComboBox cb_filterSchool;
    public ComboBox cb_filterSpeciality;
    public ListView<VBox> list_postFlow;

    // POST - Fields
    Label lbPostUserName;
    Label lbPostUserRole;
    Label lbPostTags;
    Label lbPostLbLastUpdate;
    Label lbPostValLastUpdate;
    Label lbPostFileName;
    Label lbPostFileInfo;
    Button btnDownload;


    public void initialize(){
        File file = new File("C:/Users/hp/Documents/ChiffrementRSA.xlsx");
        Post post = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file
        );
        post.setLastUpdate(Timestamp.from(Instant.now()));
        File file2 = new File("C:/Users/hp/Documents/PRÉSENTATION RAPPORT OVER SOFT SOLUTION.pptx");
        Post post2 = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file2
        );
        post2.setLastUpdate(Timestamp.from(Instant.now()));
        File file3 = new File("C:/Users/hp/Documents/RAPPORT DE STAGE OVERSOFT SOLUTION.pdf");
        Post post3 = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file3
        );
        post3.setLastUpdate(Timestamp.from(Instant.now()));
        File file4 = new File("C:/Users/hp/Documents/RAPPORT DE STAGE OVERSOFT SOLUTION.docx");
        Post post4 = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file4
        );
        post4.setLastUpdate(Timestamp.from(Instant.now()));

        initializePostLayout();

        list_postFlow.getItems().add(postToUI(post));
        list_postFlow.getItems().add(postToUI(post2));
        list_postFlow.getItems().add(postToUI(post3));
        list_postFlow.getItems().add(postToUI(post4));
    }
    public void addPost(ActionEvent actionEvent) {

    }

    public void logOut(ActionEvent actionEvent) {

    }

    public void refresh(ActionEvent actionEvent) {

    }

    void initializePostLayout(){
        lbPostUserName = new Label();
        lbPostUserRole = new Label();
        lbPostTags = new Label();
        lbPostLbLastUpdate = new Label();
        lbPostValLastUpdate = new Label();
        lbPostFileName = new Label();
        lbPostFileInfo = new Label();
        btnDownload = new Button();
    }

    VBox postToUI(@NotNull Post post){
        VBox vBoxPost = new VBox();
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
                        lbPostUserName = new Label(post.getAuthorId()); // Author.Name
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

        // Putting child nodes into corresponding parent nodes (Starting from the bottom and adding gradually)
                        vBoxPostFileName.getChildren().addAll(lbPostFileName, lbPostFileInfo);
                        hBoxPostFileInfo.getChildren().addAll(stkPostFilePhoto, vBoxPostFileName);
                    hBoxPostFile.getChildren().addAll(hBoxPostFileInfo, stkGrowAlways2, btnDownload);

                    hBoxPostLastUpdate.getChildren().addAll(lbPostLbLastUpdate, lbPostValLastUpdate);
                hBoxPostInfo.getChildren().addAll(lbPostTags, separatorV, hBoxPostLastUpdate);

                vBoxPostUserInfo.getChildren().addAll(lbPostUserName, lbPostUserRole);
                hBoxPostUserName.getChildren().addAll(stkPostUserPhoto, vBoxPostUserInfo);

            hBoxPostTitle.getChildren().addAll(hBoxPostUserName, stkGrowAlways1, hBoxPostInfo);

        vBoxPost.getChildren().addAll(hBoxPostTitle, hBoxPostFile);

        return vBoxPost;
    }
}
