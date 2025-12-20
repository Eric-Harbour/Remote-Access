package it386Project.honorsproject.RemoteAccess;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class RMIWelcomeScreen extends Application {
    public ImageView remoteScreenImageView;
    public Scene connectionScene;
    public Pane connectionPane;
    public Button backButton = new Button("<-- Back");
        @Override
        public void start(Stage stage) throws FileNotFoundException {
            // load icon
            Image icon = new Image(new FileInputStream("src/resources/images/icon.png"));
//
            // Create our parent pane
            BorderPane welcomePane = new BorderPane();

            Pane buttonPane = new VBox(100);
            javafx.scene.control.Button clientButton = new javafx.scene.control.Button("Connect to Another Computer");
            clientButton.setPrefHeight(50);
            clientButton.setPrefWidth(200);



            javafx.scene.control.Button serverButton = new Button("Allow Remote Connection");
            serverButton.setPrefHeight(50);
            serverButton.setPrefWidth(200);



            // set the buttons into button pane
            buttonPane.getChildren().addAll(clientButton, serverButton);
            buttonPane.setPadding(new Insets(0,50,50,50));

            // set the buttonPane to the bottom of the welcomePane
            welcomePane.setBottom(buttonPane);

            // create the scene
            Scene welcomeScene = new Scene(welcomePane, 300, 400);

            // set the stage and show
            stage.setScene(welcomeScene);
            stage.getIcons().add(icon);
            stage.setTitle("Remote Connection");

            stage.show();

            clientButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    remoteInfoStage(stage);
                }
            });

            serverButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        portAssignmentStage(stage);
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        };

        // client method
        public void remoteInfoStage(Stage stageToUpdate) {
            // parent pane for entering remote computer information
            BorderPane remoteInfoPane = new BorderPane();

            // Back button
            backButton = new Button("<-- Back");


            // create HBox for input
            VBox remoteInfoVBox = new VBox(50);
            remoteInfoVBox.setPadding(new Insets(50,50,50,50));

            // remote IP section
            HBox remoteIPHBox = new HBox(50);
            Label remoteIPLabel = new Label("Remote IP: ");
            TextField remoteIPTF = new TextField();
            remoteIPTF.setPromptText("Insert IP Address of Remote Computer");
            remoteIPTF.setPrefWidth(100);
            remoteIPHBox.getChildren().addAll(remoteIPLabel, remoteIPTF);

            // port number section
            HBox portNumberHBox = new HBox(35);
            Label portNumberLabel = new Label("Port number: ");
            TextField portNumberTF = new TextField();
            portNumberTF.setPromptText("Insert Port Number");
            portNumberTF.setPrefWidth(100);
            portNumberHBox.getChildren().addAll(portNumberLabel, portNumberTF);

            Button confirmConnectionButton = new Button("Connect");
            confirmConnectionButton.setPrefWidth(100);
            remoteInfoVBox.getChildren().addAll(remoteIPHBox, portNumberHBox, confirmConnectionButton);



            remoteInfoPane.setCenter(remoteInfoVBox);
            remoteInfoPane.setTop(backButton);

            Scene remoteInfoScene = new Scene(remoteInfoPane, 350, 300);
            stageToUpdate.setScene(remoteInfoScene);

            confirmConnectionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String ipAddress = remoteIPTF.getText();
                    int portNumber = Integer.parseInt(portNumberTF.getText());
                    try {
                        remoteConnectionStage(stageToUpdate,ipAddress, portNumber );
                    } catch (NotBoundException | RemoteException | FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (AWTException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        start(stageToUpdate);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

//          Pane testPane = new Pane();
//          Scene clientScene = new Scene(testPane);
//          stageToUpdate.setScene(clientScene);
//          stageToUpdate.show();
//          System.out.println("Client scene selected");
        }
        // server method
        public void portAssignmentStage(Stage stageToUpdate) throws UnknownHostException {
            // create a pane
            BorderPane portAssignmentPane = new BorderPane();

            // create a final VBox for elements
            VBox finalVBox = new VBox(50);

            // Create Listview to list ip addresses
            ListView ipList = new ListView();

            ipList.setMaxWidth(100);

            ipList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


            InetAddress[] ipArray = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());

            int numAddress = 0;
            for(InetAddress address: ipArray) {
                // just want ipv4 addresses
                System.out.println(address.getClass());
                if (address instanceof java.net.Inet4Address) {
                    ipList.getItems().add(address.getHostAddress());
                    numAddress++;
                }

                //System.out.println(address.getHostAddress());
            }
            // "dynamically set size"
            ipList.setPrefHeight(numAddress * 25);
            //create a HBox for the network port selection
            HBox portInformationHBox = new HBox(50);
            portInformationHBox.setPadding(new Insets(0,0,0,50));

            // Create the port label
            Label portLabel = new Label("Port: ");

            // Create port textfield
            TextField portTF = new TextField();
            portTF.setPromptText("Enter desired port");
            portTF.setPrefWidth(80);
            //portTF.setPadding(new Insets(0,0,0,50));

            // assign label and textfield to hbox
            portInformationHBox.getChildren().addAll(portLabel, portTF);

            // create start button
            Button startBtn = new Button("Start Server");

            // add port HBox and start button to final vbox
            finalVBox.getChildren().addAll(ipList,portInformationHBox, startBtn);
            finalVBox.setAlignment(Pos.CENTER);

            portAssignmentPane.setCenter(finalVBox);
            portAssignmentPane.setTop(backButton);

            Scene scene = new Scene(portAssignmentPane, 300,400);

            stageToUpdate.setScene(scene);

            stageToUpdate.show();

            startBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {

                        serverDisplayStage(stageToUpdate ,ipList.getSelectionModel().getSelectedItem().toString(),Integer.parseInt(portTF.getText()));
                    } catch (RemoteException | AWTException e) {
                        throw new RuntimeException(e);
                    }


                }
            });

            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        start(stageToUpdate);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        // server method
        public void serverDisplayStage(Stage stageToUpdate, String ipAddress, int portNumber) throws RemoteException, AWTException {
            RMIServer server = new RMIServer(ipAddress, portNumber);

        }
        // client method
        public void remoteConnectionStage(Stage stageToUpdate, String ipAddress, int portNumber) throws NotBoundException, RemoteException, FileNotFoundException, AWTException {

            RMIClient clientObj = new RMIClient(ipAddress, portNumber);
            MouseHandler handler = new MouseHandler(clientObj);

            connectionPane = new VBox();

            /**
             * Future update: make a method to create the updated screenshot screen */
            // grab screenshot from the remote computer and put inside the ImageView
            Image screenshot = clientObj.getScreenshot();
            remoteScreenImageView = new ImageView(screenshot);



            connectionPane.getChildren().add(remoteScreenImageView);

            connectionPane.setOnMouseMoved(handler);



            connectionScene = new Scene(connectionPane);
            stageToUpdate.setScene(connectionScene);
            stageToUpdate.show();

            connectionPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    try {
                        clientObj.mouseClick();
                        Thread.sleep(100);
                        Image screenshot = clientObj.getScreenshot();
                        remoteScreenImageView.setImage(screenshot);
                        stageToUpdate.show();

                    } catch (AWTException | RemoteException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

}




