package panda.guest.ui.models.filtering;

import javafx.collections.ObservableList;
import panda.host.models.Post;

import java.util.List;

public class PostFilterByTags {
    String text;

    public PostFilterByTags(String text){
        this.text = text;
    }

    public ObservableList<Post> filter(ObservableList<Post> posts) {
//        return posts.filtered(post -> (post.getTagsToList().stream().anyMatch(text.toLowerCase()::contains)));
        return posts.filtered(post -> (post.getTagsToList().stream().anyMatch(tag -> tag.toLowerCase().contains(text.toLowerCase()))));
    }
}
