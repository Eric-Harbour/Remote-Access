package it386Project.honorsproject.RemoteAccess;

import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.event.InputEvent;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.Object.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MouseImpl extends UnicastRemoteObject implements MouseInterface {
    private int portNumber;
    private Robot robot= new Robot();
    public MouseImpl(int portNumber) throws RemoteException, AWTException {
        this.portNumber = portNumber;
    }

    @Override
    public double printMessage(double num1, double num2) {
        System.out.println("This is the on the remote server");
        return num1 + num2;
    }

    @Override
    public void updateMouse(double x_coord, double y_coord) {
        this.robot.mouseMove((int)x_coord, (int)y_coord);
    }

    @Override
    public byte[] getScreenshot() throws IOException {
        byte[] imageBytes = null;
        try {
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(1280, 720));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //Raster byteData = screenshot.getData();

            ImageIO.write(screenshot, "png", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            return imageBytes;
        } catch (Exception ex) {
            System.out.println("GETSCREENSHOT() ERROR" + ex);
        }
        return imageBytes;

        //javafx.scene.image.Image screenshot = SwingFXUtils.toFXImage(takenScreenshot, null);
        //Image screenshot = ImageIO.read(new BufferedImage(robot.createScreenCapture(new Rectangle(1280,720))));
    }
    @Override
    public void mouseClick() throws RemoteException{
        int left_mouse = InputEvent.BUTTON1_DOWN_MASK;
        try {
            robot.mousePress(left_mouse);
            robot.mouseRelease(left_mouse);
        } catch (IllegalArgumentException ex) {
            System.out.println("MOUSECLICK EXCEPTION" + ex);
        }

    }
}
