package it386Project.honorsproject.RemoteAccess;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer{
    private MouseInterface remoteObject;
    public RMIServer(String hostIP ,int portNumber) throws RemoteException, AWTException {
        try {

            //String hostIP = InetAddress.getLocalHost().getHostAddress();
            System.out.println(hostIP);

            System.setProperty("java.rmi.server.hostname", hostIP);


            // create the registry
            Registry rmiRegistry = LocateRegistry.createRegistry(portNumber);

            // initialize the RMI remote object
            remoteObject = new MouseImpl(portNumber);

            // bind the remoteObject to the registry
            rmiRegistry.rebind("MouseService", remoteObject);

            System.out.println("The RMI server is ready");



        } catch (AWTException | IOException e) {
            System.out.println(e);

        }
    }
    }


