package panda.guest.ui.models.sorting;

import panda.host.models.Post;

import java.util.Comparator;
import java.util.List;

public class FileSizeSorting implements PostSorting {
    @Override
    public void sort(List<Post> posts, Order order) {
        // Applying the original post order (id:ASC)
        posts.sort(Comparator.comparing(Post::getId));

        switch (order){
            case ASC -> posts.sort(Comparator.comparing(Post::getFileSize));
            case DESC -> posts.sort(Comparator.comparing(Post::getFileSize).reversed());
        }
    }

    @Override
    public String toString() {
        return "File size";
    }
}
