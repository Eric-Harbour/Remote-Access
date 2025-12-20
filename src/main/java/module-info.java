module it386Project.honorsproject.RemoteAccess {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.swing;
    //requires it386Project.honorsproject.RemoteAccess;


    opens it386Project.honorsproject.RemoteAccess to javafx.fxml;
    exports it386Project.honorsproject.RemoteAccess;
    exports it386Project.RMI;
    opens it386Project.RMI to javafx.fxml;


    //exports it386Project.honorsproject.RemoteAccess.RMIClient;
    //opens it386Project.honorsproject.RemoteAccess.RMIClient to javafx.fxml;
}