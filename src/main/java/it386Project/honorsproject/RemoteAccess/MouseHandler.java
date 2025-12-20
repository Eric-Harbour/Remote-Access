package it386Project.honorsproject.RemoteAccess;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;

public class MouseHandler implements MouseMotionListener, EventHandler<MouseEvent> {
    public final RMIClient clientObj;

    public MouseHandler(RMIClient clientObj) {
        this.clientObj = clientObj;
    }

//    public MouseHandler() {
//    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        try {
            System.out.println(e.getX());
            this.clientObj.remoteCallMouseMovement(e.getX(), e.getY());
        } catch (AWTException | RemoteException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {

    }


    @Override
    public void handle(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            this.clientObj.remoteCallMouseMovement((int) mouseEvent.getX(), (int) mouseEvent.getY());
        } catch (AWTException | RemoteException exception) {
            throw new RuntimeException(exception);

        }
    }
}

