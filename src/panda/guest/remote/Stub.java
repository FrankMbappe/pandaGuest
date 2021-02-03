package panda.guest.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import panda.host.models.Authentication;
import panda.host.models.Post;
import panda.host.models.filters.Credentials;
import panda.host.models.filters.PostFilter;
import panda.host.server.PandaRemote;
import panda.host.utils.Current;

import java.lang.reflect.Type;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import static panda.host.utils.Panda.*;

public class Stub {
    PandaRemote remote;

    public Stub(boolean useLog){
        try {
            // Getting the default method remotely from the server
            if (useLog) System.out.println(String.format("[Stub] | Accessing the remote service at the address %s.\r", DEFAULT_REMOTE_URL));

            Registry registry = LocateRegistry.getRegistry(DEFAULT_PORT);
            remote = (PandaRemote) registry.lookup(DEFAULT_REMOTE_URL);

            //remote.register(Current.ID, new Gson().toJson(new SyncChannelImpl()));

            if (useLog) System.out.println("[Stub] | Successfully connected to the server.");

        } catch (RemoteException | NotBoundException e){
            if (useLog) System.err.println("[Stub] | Cannot access the server..\r");
            // if (useLog) e.printStackTrace();
        }
    }

    public PandaRemote get(){
        return remote;
    }

    public synchronized void register(){
        System.out.println(String.format("[Stub, register()] | Registering server with id: '%s'.", Current.ID));
        try {
            remote.register(Current.ID, new SyncChannelImpl());

        } catch (RemoteException e) {
            System.out.println(String.format("[Stub, register()] | Failed to register. Id: '%s'.", Current.ID));
            e.printStackTrace();
        }
    }

    public synchronized void unregister(){
        System.out.println(String.format("[Stub, unregister()] | Server unregistering with id: '%s'.", Current.ID));
        try {
            remote.unregister(Current.ID);

        } catch (RemoteException e) {
            System.out.println(String.format("[Stub, unregister()] | Failed to unregister. Id: '%s'.", Current.ID));
            e.printStackTrace();
        }
    }

    public synchronized ArrayList<Post> getPosts(PostFilter filter){
        try{
            if (filter == null) filter = new PostFilter(true, "", "");

            System.out.println(String.format("[Stub] | Requesting posts using filters: '%s'.", filter.toString()));

            // Deserializing the post list using Gson
            String postListToJson = remote.getPosts(new Gson().toJson(filter));

            if(postListToJson != null){
                // Creating an object containing the ArrayList<Post>() type
                Type postListType = new TypeToken<ArrayList<Post>>() {}.getType();

                // Passing that type to Gson to deserialize the data
                ArrayList<Post> retrievedPosts = new Gson().fromJson(postListToJson, postListType);

                System.out.println(String.format("[Stub] | %d post(s) were retrieved.", retrievedPosts.size()));

                return retrievedPosts;
            }

        } catch(NullPointerException nullPointerException){
            System.err.println("[Stub] | Error: The data retrieved was null.");
            // System.exit(-1);
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized ArrayList<Post> getPosts(){
        return getPosts(null);
    }

    public synchronized boolean addPost(Post post){
        try{
            remote.addPost(new Gson().toJson(post));
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public synchronized Authentication logUserIn(Credentials credentials){
        try {
            String credentialsToJson = new Gson().toJson(credentials);
            System.out.println(String.format("[Stub] | Requesting an auth using the credentials: '%s'.", credentials));

            // Deserializing the auth object using Gson
            String authObjectToJson = remote.logUserIn(credentialsToJson);
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

    public synchronized boolean logUserOut(String username){
        System.out.println(String.format("[Stub] | Logging out user with id: '%s'.", username));
        try{
            remote.logUserOut(username);

            // If the user logs out, I don't have his auth anymore
            Current.auth = null;

            System.out.println(String.format("[Stub] | User '%s' has logged out successfully.", username));
            return true;

        } catch (Exception e){
            System.err.println(String.format("[Stub] | Something went wrong while logging out user '%s'.", username));
            e.printStackTrace();
        }
        return false;
    }

    public synchronized byte[] downloadPostFile(Post post){
        try {
            return remote.downloadPostFile(post.getAuthorId(), post.getFileId(), post.getFileExt());

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public synchronized boolean serverIsAccessible(){
        try{
            return remote.serverIsAccessible();

        } catch (Exception e){
            return false;
        }
    }


}
