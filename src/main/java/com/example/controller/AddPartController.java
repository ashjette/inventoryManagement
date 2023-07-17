package com.example.controller;
/**
 * @author Ashley Jette
 *
 *
 * */

import com.example.model.InhousePart;
import com.example.model.Inventory;
import com.example.model.OutsourcedPart;
import com.example.model.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * AddPartController class will implement the add part form, which allows for new parts to be added into the inventory
 * */
public class AddPartController implements Initializable

{
    Stage stage;
    Parent scene;


    public RadioButton outsourced;
    public ToggleGroup Source;
    public Label changeMe;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineIdField;
    public TextField minField;
    public Button save;
    public Button cancel;
    public RadioButton inHouse;

/**
 * Initializes controller
 * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
    /**
     * assigns machineid text with "Machine ID" when inhouse radio button is selcted
     * */

    public void onInhouse(ActionEvent actionEvent) {
        boolean isInhouse;
        isInhouse = inHouse.isSelected();
        changeMe.setText("Machine ID");


    }
    /**
     * assigns machineidtext with "Company Name" when outsourced radio button is selcted
     * */

    public void onOutsourced(ActionEvent actionEvent) {
        boolean isOutsourced;
        isOutsourced = outsourced.isSelected();
        changeMe.setText("Company Name");



    }
    /**
     * compiles field data to create new part. Adds part to inventory and redirects to main menu
     * */

    public void onSave(ActionEvent actionEvent) throws IOException {
        try {
            ObservableList<Part> allParts = Inventory.getAllParts();

            int id = 0;
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if(!(max > min) || !(min >= 1)) {
                throw new NumberFormatException("Min must be less than Max");
            }
            if( (stock > max) || (stock < min)) {
                throw new NumberFormatException("Inventory Level must be between Min and Max");
            }
            for(int i = 0; i <= allParts.size(); ++i) {
                id = i + 1;
            }
            if(inHouse.isSelected()) {
                int machineId = Integer.parseInt(machineIdField.getText());

                Inventory.addPart(new InhousePart(id, name, price, stock, min, max, machineId));
            }
            else {
                String companyName = machineIdField.getText();

                Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, companyName));
            }

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }
    /**
     * returns user to main menu without saving part
     * */


    public void onCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }



}