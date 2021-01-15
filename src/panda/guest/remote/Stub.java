package panda.guest.remote;

import panda.host.server.PandaRemote;
import panda.host.utils.Panda;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Stub {
    PandaRemote stub;

    public Stub(){
        try {
            // Getting the default method remotely from the server
            System.out.println(String.format("[Test] | Accessing the remote service at the address %s.", Panda.DEFAULT_REMOTE_URL));
            System.setProperty("java.rmi.server.hostname","192.168.173.1");
            LocateRegistry.getRegistry(Panda.DEFAULT_PORT);
            stub = (PandaRemote) Naming.lookup(Panda.DEFAULT_REMOTE_URL);

        } catch (RemoteException | NotBoundException | MalformedURLException e){
            e.printStackTrace();
        }
    }

    public PandaRemote get(){
        return stub;
    }
}
