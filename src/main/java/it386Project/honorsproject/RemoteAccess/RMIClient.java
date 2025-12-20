package it386Project.honorsproject.RemoteAccess;


import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    //public static void main(String[] args) {
    private MouseInterface remoteObject;
    public RMIClient(String remoteIP, int portNumber) throws RemoteException, NotBoundException {

        try {
//            int portNumber = Integer.parseInt(args[0]);

            // Create the registry (make sure to pass in the portNumber)
            Registry registry = LocateRegistry.getRegistry(remoteIP, portNumber);

            // creating the remote object
            this.remoteObject = (MouseInterface) registry.lookup("MouseService");



        } catch (Exception e) {
            System.out.println(e);
        }

    }



    public void remoteCallMouseMovement(int x_coord, int y_coord) throws AWTException, RemoteException {
        try {
            this.remoteObject.updateMouse(x_coord, y_coord);
        } catch(AWTException | RemoteException ex) {
            System.out.println(ex);
        }


    }

    public Image getScreenshot() throws AWTException, RemoteException {
        byte[] incomingScreenshot = null;
        Image image = null;
        try {
            incomingScreenshot = this.remoteObject.getScreenshot();

            ByteArrayInputStream newScreenshot = new ByteArrayInputStream(incomingScreenshot);

            image = new Image(newScreenshot);


        } catch (AWTException | IOException ex) {
            System.out.println("ERROR OCCURRED:");
            System.out.println(ex);
        }
        return image;
    }

    public void mouseClick() throws RemoteException{
        this.remoteObject.mouseClick();

    }
}

