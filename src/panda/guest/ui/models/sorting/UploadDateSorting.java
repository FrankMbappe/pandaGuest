package panda.guest.ui.models.sorting;

import panda.host.models.Post;

import java.util.Comparator;
import java.util.List;

public class UploadDateSorting implements PostSorting {
    @Override
    public void sort(List<Post> posts) {
        posts.sort(Comparator.comparing(Post::getUploadDate));
    }

    @Override
    public String toString() {
        return "Upload date";
    }
}
