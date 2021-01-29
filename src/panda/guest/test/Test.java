package panda.guest.test;

import panda.guest.remote.Stub;
import panda.host.models.Post;
import panda.host.models.User;

import java.io.File;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        System.out.println("\n\n[Test] | Test started.");

        // Creating my remote helper class
        Stub stub = new Stub(true);

//        System.out.println("--- TEST GET POSTS");
//        PostFilter filter = new PostFilter(false, "pdf", "3iac_tic_pam_2");
//        ArrayList<Post> posts = stub.getPosts(filter);
//
//        System.out.println("--- TEST LOGIN");
//        Authentication auth = stub.logUserIn(new Credentials(getUser()));
//        // If the authentication object is valid,
//        if (auth.isValid()){
//            // I put the retrieved authentication as the current one for the session
//            Current.auth = auth;
//        }
        File file = new File("C:/Users/hp/Documents/easyship.txt");
        Post post = new Post(
                "mabel.parrot18@myiuc.com",
                "Hello there, this is the seventh test.",
                "btech;swe;it",
                file
        );
        stub.addPost(post);

        System.out.println("[Test] | Test ended.");

    }
    static User getUser(){
        String username, password;
        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        username = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();

        return new User(username, password);
    }

}
