package panda.guest.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import panda.host.models.Post;
import panda.host.server.SyncChannel;
import panda.host.utils.Current;

import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SyncChannelImpl extends UnicastRemoteObject implements SyncChannel {
    protected SyncChannelImpl() throws RemoteException {
        super();
    }

    @Override
    public void updatePosts(String postListToJson) throws RemoteException {
        Type postListType = new TypeToken<ObservableList<Post>>(){}.getType();
        Current.postList = new Gson().fromJson(postListToJson, postListType);
    }

    @Override
    public void updateServerStatus(boolean serverIsRunning) throws RemoteException {
        Current.serverIsRunning.setValue(serverIsRunning);
    }
}
