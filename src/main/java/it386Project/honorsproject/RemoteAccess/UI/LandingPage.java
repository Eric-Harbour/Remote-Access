/**
 * This will be the first page the user interacts with after the application opens.
 * The user must be able to do the following:
 *
 * 1. If the user wants to connect to a remote system, they must be able to seelct the 'Connect to Remote System' button
 * 2. If the user wants to allow for remote connections, they must be able to select the 'Allow Remote Connections' button
 *
 * **/

package it386Project.honorsproject.RemoteAccess.UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LandingPage{
    public Scene loadLandingPage() throws FileNotFoundException{
        Image icon;
        Scene landingPageScene;
        try {
            icon = new Image(new FileInputStream("src/resources/images/icon.png"));
        } catch (FileNotFoundException e) {
            icon = null;
        }

        // Create the panes we will use
        BorderPane welcomePane = new BorderPane();

        /**
         * The client button and remote system button should
         * probably be broken into their own individual sections
         * Landing Page would be used to show the
         * **/
        // create items related to 'Connect to Remote System' (client) button
        Pane buttonPane = new VBox(100);
        Button clientButton = new Button("Connect to Remote System");
        clientButton.setPrefHeight(50);
        clientButton.setPrefWidth(200);

        // create items related to 'Allow Remote Connection' (server)
        Button serverButton = new Button("Allow Remote Connection");
        serverButton.setPrefHeight(50);
        serverButton.setPrefWidth(200);

        // set the buttons into button pane
        buttonPane.getChildren().addAll(clientButton, serverButton);

        // set the buttonPane inside welcomePane
        welcomePane.setCenter(buttonPane);

        // set the welcomePane inside the scene
        landingPageScene = new Scene(welcomePane);

        return landingPageScene;
    }
    // The following variables will be used on multiple screens,

}
