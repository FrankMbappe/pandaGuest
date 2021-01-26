package panda.guest.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import panda.host.models.Authentication;
import panda.host.models.Post;
import panda.host.models.filters.Credentials;
import panda.host.models.filters.PostFilter;
import panda.host.server.PandaRemote;

import java.lang.reflect.Type;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import static panda.host.utils.Panda.*;

public class Stub {
    PandaRemote stub;

    public Stub(boolean useLog){
        try {
            // Getting the default method remotely from the server
            if (useLog) System.out.println(String.format("[Stub] | Accessing the remote service at the address %s.\r", DEFAULT_REMOTE_URL));

            Registry registry = LocateRegistry.getRegistry(DEFAULT_PORT);
            stub = (PandaRemote) registry.lookup(DEFAULT_REMOTE_URL);

            if (useLog) System.out.println("[Stub] | Successfully connected to the server.");

        } catch (RemoteException | NotBoundException e){
            if (useLog) System.err.println("[Stub] | Cannot access the server..\r");
        }
    }

    public PandaRemote get(){
        return stub;
    }

    public ArrayList<Post> getPosts(PostFilter filter){
        try{
            // Getting request format
            String postRequestFormat = PANDA_ENCODING_PATTERNS.get(PandaOperation.PANDAOP_REQUEST_GET_POSTS);

            // Creating the panda code to request the posts list
            String postRequest = String.format(postRequestFormat, filter.isAll(), filter.getFileType(), filter.getSchoolClassId());
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
            System.out.println("[Stub] | Error: The data retrieved was null.");
//            System.exit(-1);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    public Authentication logUserIn(Credentials credentials){
        try {
            // Getting connection request format
            String authRequestFormat = PANDA_ENCODING_PATTERNS.get(PandaOperation.PANDAOP_REQUEST_GET_CONNECTION);

            // Creating the panda code to request the authentication object
            // Notice that I put the 'Guest' property to false, since I'll manage the guests internally.
            String authRequest = String.format(authRequestFormat, credentials.getUsername(), credentials.getPassword());

            System.out.println(String.format("[Stub] | Requesting an auth using the panda code: '%s'.", authRequest));

            // Deserializing the auth object using Gson
            String authObjectToJson = stub.logUserIn(authRequest);
            if(authObjectToJson != null){
                // I do it here
                Authentication auth = new Gson().fromJson(authObjectToJson, Authentication.class);

                System.out.println(String.format("[Stub] | Authentication provided: '%s'.", auth.toString()));

                return auth;

            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] downloadPostFile(Post post){
        try {
            return stub.downloadPostFile(post.getAuthorId(), post.getFileId(), post.getFileExt());

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean addPost(Post post){
        try{
            stub.addPost(new Gson().toJson(post));
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean serverIsAccessible(){
        try{
            return stub.serverIsAccessible();

        } catch (Exception e){
            return false;
        }
    }

}
