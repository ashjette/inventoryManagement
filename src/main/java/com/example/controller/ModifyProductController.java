package com.example.controller;
/**
 * @author Ashley Jette
 * */
import com.example.model.Inventory;
import com.example.model.Part;
import com.example.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * initializes modify product menu, which allows user to modify pre-existing product and save changes in inventory*/
public class ModifyProductController implements Initializable {
    public TableView<Part> modifyProductTable;
    public TableView<Part> addProductTable;
    Stage stage;
    Parent scene;
    public Label id;
    public Label name;
    public Label inv;
    public Label price;
    public Label max;
    public Label min;
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TableColumn<Part, Integer> addPartIdCol;
    public TableColumn<Part, String> addPartNameCol;
    public TableColumn<Part, Integer> addInvLevelCol;
    public TableColumn<Part, Double> addPriceCol;
    public Button add;
    public TableColumn<Part, Integer> rePartIdCol;
    public TableColumn<Part, String> rePartName;
    public TableColumn<Part, Integer> reInvLevelCol;
    public TableColumn<Part, Double> rePriceCol;
    public Button removePart;
    public Button save;
    public Button cancel;
    public TextField partSearch;

    Product modifyPart = null;
    /**
     * list of associated parts to be used in product*/
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * populates text fields
     * */
    public void modifyProduct(Product selectedProduct) {
        modifyPart = selectedProduct;

        idField.setText(String.valueOf(modifyPart.getId()));
        nameField.setText(modifyPart.getName());
        invField.setText(String.valueOf(modifyPart.getStock()));
        priceField.setText(String.valueOf(modifyPart.getPrice()));
        maxField.setText(String.valueOf(modifyPart.getMax()));
        minField.setText(String.valueOf(modifyPart.getMin()));

        modifyProductTable.setItems(modifyPart.getAllAssociatedParts());
        associatedParts = modifyPart.getAllAssociatedParts();                      //RUNTIME_ERROR
    }
    /**
     * initialize method, calls method from inventory to assign parts to table*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTable.setItems(Inventory.getAllParts());

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        addInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;

        modifyProductTable.setItems(associatedParts);

        rePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        reInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        rePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    /**
     * removes associated part from product, represented in table view. Prompts user to confirm selection
     * */
    public void onRemovePart(ActionEvent actionEvent) {
        System.out.println("Remove Associated Part Button Clicked!");

        Part selectedPart = (Part) modifyProductTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setContentText("You have chosen to remove a part from your product, do you wish to continue?");

        Optional<ButtonType> result = alert1.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }
    }
    /**
     * adds passociated part to product, represented in table view
     * */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) addProductTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }
        associatedParts.add(selectedPart);
        modifyProductTable.setItems(associatedParts);
    }

    /**
     * performs checks to ensure that provided data is valid before updating inventory with modified product information
     * */
    public void onSave(ActionEvent actionEvent) {
        try{
            int id = modifyPart.getId();
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

            int index = Inventory.getAllProducts().indexOf(modifyPart);
            Product newProduct = new Product(id, name, price, stock, min, max);



            Inventory.addProduct(newProduct);
            Inventory.deleteProduct(modifyPart);

            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(NumberFormatException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each Text Field!");
            alert.showAndWait();
        }
    }
    /**
     * returns user to main menu without saving changes
     * */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * searches parts by ID or name, filtering respectively by highlighting or isolating
     * */
    public void onPartSearch(ActionEvent actionEvent) {
        String searchText = partSearch.getText();

        ObservableList<Part> parts = Inventory.lookupPart(searchText);

        if (parts.size() == 0) {
            try {
                int searchId = Integer.parseInt(searchText);
                Part searchPart = Inventory.lookupPart(searchId);
                if (searchPart != null)
                    parts.add(searchPart);
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        addProductTable.setItems(parts);
        partSearch.clear();

    }
}