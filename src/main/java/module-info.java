module com.example.ajjavafxmlapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example to javafx.fxml;
    exports com.example;
    exports com.example.model;
    opens com.example.model to javafx.fxml;
    exports com.example.controller;
    opens com.example.controller to javafx.fxml;
}