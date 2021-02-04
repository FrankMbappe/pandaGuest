package panda.guest.ui.models.sorting;

import panda.host.models.Post;

import java.util.Comparator;
import java.util.List;

public class FileExtSorting implements PostSorting {
    @Override
    public void sort(List<Post> posts, Order order) {
        // Applying the original post order (id:ASC)
        posts.sort(Comparator.comparing(Post::getId));

        switch (order){
            case ASC -> posts.sort(Comparator.comparing(Post::getFileExt));
            case DESC -> posts.sort(Comparator.comparing(Post::getFileExt).reversed());
        }
    }

    @Override
    public String toString() {
        return "File type";
    }
}
