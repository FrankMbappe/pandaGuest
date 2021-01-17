package panda.guest.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import panda.host.models.Post;
import panda.host.models.filters.PostFilter;
import panda.host.server.PandaRemote;
import panda.host.utils.Panda;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Stub {
    PandaRemote stub;

    public Stub(){
        try {
            // Getting the default method remotely from the server
            System.out.println(String.format("[Stub] | Accessing the remote service at the address %s.", Panda.DEFAULT_REMOTE_URL));
            Registry registry = LocateRegistry.getRegistry(Panda.DEFAULT_PORT);
            stub = (PandaRemote) registry.lookup(Panda.DEFAULT_REMOTE_URL);

        } catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }
    }

    public PandaRemote get(){
        return stub;
    }

    public ArrayList<Post> getPosts(PostFilter filter){
        try{
            // Getting request format
            String getPostRequestFormat = Panda.PANDA_ENCODING_PATTERNS.get(Panda.PandaOperation.PANDAOP_REQUEST_GET_POSTS);

            // Creating the panda code to request the posts list
            String postRequest = String.format(getPostRequestFormat, filter.isAll(), filter.getFileType(), filter.getSchoolClassId());
            System.out.println(String.format("[Stub] | Requesting posts using the panda code: '%s'.", postRequest));

            // Deserializing the post list using Gson
            String postListToJson = stub.getPosts(postRequest);

            if(postListToJson != null){
                // Creating an object containing the ArrayList<Post>() type
                Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();
                // Passing that type to Gson to deserialize the data
                ArrayList<Post> retrievedPosts = new Gson().fromJson(postListToJson, postListType);

                System.out.println(String.format("[Stub] | %d post(s) were retrieved.", retrievedPosts.size()));

                return retrievedPosts;
            }

        } catch(NullPointerException nullPointerException){
            System.out.println("[Stub] | Error: The data retrieved was null. The program will now exit.");
            System.exit(-1);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }
}
