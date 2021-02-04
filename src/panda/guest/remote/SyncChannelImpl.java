package panda.guest.remote;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import panda.host.models.Post;
import panda.host.server.SyncChannel;
import panda.host.utils.Current;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SyncChannelImpl extends UnicastRemoteObject implements SyncChannel {
    protected SyncChannelImpl() throws RemoteException {
        super();
    }

    @Override
    public void updatePosts(String postListToJson) throws RemoteException {
        var postList = new Gson().fromJson(postListToJson, Post[].class);
        //Current.postList.clear();
        Current.postList.setAll(FXCollections.observableArrayList(postList));
        Current.postList.notifyAll();
    }

    @Override
    public void updateServerStatus(boolean serverIsRunning) throws RemoteException {
        Current.serverIsRunning.setValue(serverIsRunning);
    }
}
