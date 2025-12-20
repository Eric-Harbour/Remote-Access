package it386Project.RMI;

import it386Project.honorsproject.RemoteAccess.RMIClient;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIApplication extends Application {

    @Override
    public void start(Stage stage) throws NotBoundException, RemoteException {
        // load icon image
        //Image icon = new Image("src/main/resources/images/icon.png");
        Image icon = new Image("resources/images/icon.png");
        System.out.println("Test");
        // load the client
        RMIClient clientObj = new RMIClient("192.168.4.24",5289);
        MouseMoveHandler handler = new MouseMoveHandler(clientObj);

        // Create a VBox pane

        Pane pane = new VBox();
        // we need to take a screenshot from the "server"
        ImageView image = new ImageView("file:///C:/Users/harbo/OneDrive/ISU/Fall2025/IT386/HonorsProject/JavaFX/src/main/resources/images/screenshot.png");



        pane.getChildren().add(image);

        pane.setOnMouseMoved(handler);

        Scene primaryScene = new Scene(pane, 1280, 720);
        stage.getIcons().add(icon);
        stage.setTitle("Honors Remote Access");

        // The primary Stage (this will be the last thing built
        // and then we set the scene

        stage.setTitle("Honors Remote Access");

        stage.show();
    }

    class MouseMoveHandler implements MouseMotionListener, EventHandler<javafx.scene.input.MouseEvent> {
        public final RMIClient clientObj;

        MouseMoveHandler(RMIClient clientObj) {
            this.clientObj = clientObj;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            try {
                System.out.println(e.getX());
                this.clientObj.remoteCallMouseMovement(e.getX(), e.getY());
            } catch (AWTException | RemoteException ex) {
                System.out.println(ex);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {

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


}
