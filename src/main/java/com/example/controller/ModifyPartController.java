package com.example.controller;
/**
 * @author Ashley Jette
 *
 * */
import com.example.model.InhousePart;
import com.example.model.Inventory;
import com.example.model.OutsourcedPart;
import com.example.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * initializes modify part menu, which allows user to modify pre-existing parts and save changes*/
public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;

    Part modifypart = null;


    public RadioButton inhouse;
    public ToggleGroup source;
    public RadioButton outsourced;
    public Label name;
    public Label price;
    public Label inv;
    public Label max;
    public Label machineId;
    public Label min;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineIdField;
    public TextField minField;
    public Button save;
    public Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    /**
     * assigns machineid text with "Machine ID" when inhouse radio button is selected
     * */
    @FXML
    private void onInhouse(ActionEvent actionEvent) {
        machineId.setText("Machine ID");
    }
    /**
     * assigns machineid text with "Company Name" when outsourced radio button is selected
     * */
    @FXML
    private void onOutsourced(ActionEvent actionEvent) {
        machineId.setText("Company Name");
    }
    /**
     * compiles data from fields to modify existing part, which will be updated in the inventory, taking into consideration whether it is outsourced or inhouse
     * */
    public void modifyPart(Part selectedPart) {
        modifypart = selectedPart;

        idField.setText(String.valueOf(modifypart.getId()));
        nameField.setText(modifypart.getName());
        invField.setText(String.valueOf(modifypart.getStock()));
        priceField.setText(String.valueOf(modifypart.getPrice()));
        maxField.setText(String.valueOf(modifypart.getMax()));
        minField.setText(String.valueOf(modifypart.getMin()));

        if (selectedPart instanceof InhousePart) {
            machineIdField.setText("Machine ID");
            machineIdField.setText(String.valueOf(((InhousePart) modifypart).getMachineId()));
            inhouse.setSelected(true);
        }
        else {
            machineIdField.setText("Company Name");
            machineIdField.setText(((OutsourcedPart) modifypart).getCompanyName());
            outsourced.setSelected(true);
        }
    }
    /**
     *
     Performs checks to ensure that provided data is valid before saving and compiling data*/

    @FXML
    private void onSave(ActionEvent actionEvent) throws IOException {
        try{
            int id = modifypart.getId();
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

            int index = Inventory.getAllParts().indexOf(modifypart);

            if(inhouse.isSelected()) {
                int machineId = Integer.parseInt(machineIdField.getText());

                Inventory.updatePart(index, new InhousePart(id, name, price, stock, min, max, machineId));
            }
            else {
                String companyName = machineIdField.getText();

                Inventory.updatePart(index, new OutsourcedPart(id, name, price, stock, min, max, companyName));
            }

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }
    /**
     * returns user to main menu without saving changes*/

    @FXML
    private void onCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel changes?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            idField.clear();
            nameField.clear();
            invField.clear();
            priceField.clear();
            minField.clear();
            maxField.clear();
            machineIdField.clear();
            inhouse.setSelected(false);
            outsourced.setSelected(false);

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
}






