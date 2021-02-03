package panda.guest.ui.models.filtering;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import panda.host.models.Post;

import java.util.List;
import java.util.Objects;

public class PostFilterByFileExt {
    String fileType;
    String[] extensions;

    public PostFilterByFileExt(String fileType){
        this.fileType = fileType;
    }

    public PostFilterByFileExt(String fileType, String... extensions){
        this.fileType = fileType;
        this.extensions = extensions;
    }

    public ObservableList<Post> filter(ObservableList<Post> posts) {
        if(extensions == null){
            return FXCollections.observableList(posts);

        } else {
            return posts.filtered(post -> (List.of(extensions).stream().anyMatch(post.getFileExt()::equalsIgnoreCase)));
            //return posts.filtered(post -> (List.of(extensions).contains(post.getFileExt())));
        }
    }

    @Override
    public String toString() {
        return fileType;
    }
}
