package panda.guest.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import panda.guest.remote.Stub;
import panda.host.models.Post;
import panda.host.models.filters.PostFilter;
import panda.host.server.PandaRemote;
import panda.host.utils.Panda;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        System.out.println("\n\n[Test] | Test started.");

        // Creating my remote helper class
        Stub stub = new Stub();

        PostFilter filter = new PostFilter(false, "pdf", "3iac_tic_pam_2");

        ArrayList<Post> posts = stub.getPosts(filter);

        System.out.println("[Test] | Test ended.");

    }
}
