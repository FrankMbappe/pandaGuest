package panda.guest.ui.models;

import javafx.scene.layout.VBox;
import panda.host.models.Post;

public class PostVBox extends VBox {
    Post post;

    public PostVBox(Post post){
        super();
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}
