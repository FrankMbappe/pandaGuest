package panda.guest.ui.controllers;

import panda.host.models.Post;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @org.junit.jupiter.api.Test
    void postToUI() {
        File file = new File("C:/Users/hp/Documents/ChiffrementRSA.xlsx");
        Post post = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file
        );
        post.setAuthorId("mabel.parrot18@myiuc.com");

        assertNotNull(new HomeController().postToUI(post));
    }
}