package panda.guest.ui.models.sorting;

import panda.host.models.Post;

import java.util.Comparator;
import java.util.List;

public class FileSizeSorting implements PostSorting {
    @Override
    public void sort(List<Post> posts) {
        posts.sort(Comparator.comparing(Post::getFileSize));
    }

    @Override
    public String toString() {
        return "File size";
    }
}
