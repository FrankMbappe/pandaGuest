package panda.guest.ui.models.sorting;

import panda.host.models.Post;

import java.util.List;

public interface PostSorting {
    void sort(List<Post> posts);
}
