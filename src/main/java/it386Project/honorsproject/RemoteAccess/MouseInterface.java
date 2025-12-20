package it386Project.honorsproject.RemoteAccess;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.*;

public interface MouseInterface extends Remote {
    public double printMessage(double num1, double num2) throws RemoteException;
    public void updateMouse(double x_coord, double y_coord) throws AWTException, RemoteException;
    public byte[] getScreenshot() throws AWTException, IOException;

    public void mouseClick() throws RemoteException;
}
