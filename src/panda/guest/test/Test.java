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
        try {
            System.out.println("\n\n[Test] | Test started.");

            // Getting the remote service
            PandaRemote stub = new Stub().get();

            // Getting request format
            String getPostRequestFormat = Panda.PANDA_ENCODING_PATTERNS.get(Panda.PandaOperation.PANDAOP_REQUEST_GET_POSTS);
            // I create a PostFilter to add filters in my request
            PostFilter filter = new PostFilter(true, "pdf", "3iac_tic_pam_2");
            // Creating the panda code to request the posts list
            String postRequest = String.format(getPostRequestFormat, filter.isAll(), filter.getFileType(), filter.getSchoolClassId());
            System.out.println(String.format("[Test] | Requesting posts using the panda code: '%s'.", postRequest));

            // Deserializing the post list using Gson
            String postListToJson = stub.getPosts(postRequest);

            if(postListToJson != null){
                // Creating an object containing the ArrayList<Post>() type
                Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
                // Passing that type to Gson to deserialize the data
                ArrayList<Post> retrievedPosts = new Gson().fromJson(postListToJson, postListType);

                System.out.println(String.format("[Test] | %d posts were retrieved.", retrievedPosts.size()));
            }

            System.out.println("[Test] | Test ended.");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
