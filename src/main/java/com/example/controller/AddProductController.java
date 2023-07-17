package com.example.controller;
/**
 *
 * @author Ashley Jette
 * */
import com.example.model.Inventory;
import com.example.model.Part;
import com.example.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * implements addProduct menu which can be used to add new products to the inventory
 * */
public class AddProductController implements Initializable {

    public TableView<Part> addProductTable;
    public TableView<Part> removeProductTable;
    Stage stage;
    Parent scene;


    @FXML
    public Label id;
    @FXML
    public Label name;
    @FXML
    public Label inv;
    @FXML
    public Label price;
    @FXML
    public Label max;
    @FXML
    public Label min;
    @FXML
    public TextField idField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField invField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField maxField;
    @FXML
    public TextField minField;
    @FXML
    public TableColumn<Part, Integer> addPartIdCol;
    @FXML
    public TableColumn<Part, String> addPartNameCol;
    @FXML
    public TableColumn<Part, Integer> addInvLevelCol;
    @FXML
    public TableColumn<Part, Double> addPriceCol;
    @FXML
    public Button add;
    @FXML
    public TableColumn<Part, Integer> rePartIdCol;
    @FXML
    public TableColumn<Part, String> rePartName;
    @FXML
    public TableColumn<Part, Integer> reInvLevelCol;
    @FXML
    public TableColumn<Part, Double> rePriceCol;
    @FXML
    public Button removePart;
    @FXML
    public Button save;
    @FXML
    public Button cancel;
    @FXML
    public TextField partSearch;

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     *       adds associated part to the second table view which represents parts associated with a specific product*/
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }
        associatedParts.add(selectedPart);
        removeProductTable.setItems(associatedParts);

    }
    /**
     * removes associated part from the second table view which represents parts associated with a specific product*/

    @FXML
    private void onRemovePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) removeProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Item not found.");
            alert.showAndWait();

            return;
        }

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setContentText("Are you sure you want to remove this part?");

        Optional<ButtonType> result = alert1.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            associatedParts.remove(selectedPart);
        }
    }
    /**
     * saves fields to a new product entry in inventory
     * */

    @FXML
    private void onSave(ActionEvent actionEvent) throws IOException {
        try {
            ObservableList<Product> allProducts = Inventory.getAllProducts();

            int id = 0;
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int max = Integer.parseInt(maxField.getText());
            int min = Integer.parseInt(minField.getText());

            if (!(max > min) || !(min >= 1)) {
                throw new NumberFormatException("Min must be less than Max");
            }
            if ((stock > max) || (stock < min)) {
                throw new NumberFormatException("Inventory Level must be between Min and Max");
            }
            for (int i = 0; i <= allProducts.size(); ++i) {
                id = i + 1000;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);

            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

            stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } 
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Exception: " + e.getMessage() + ". Please enter a valid value for each text field.");
            alert.showAndWait();
        }
    }

    /**
     * returns to main menu without saving product
     * */

    @FXML
    private void onCancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * initializes tables with pre-existing data*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTable.setItems(Inventory.getAllParts());

        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        removeProductTable.setItems(associatedParts);

        rePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        rePartName.setCellValueFactory(new PropertyValueFactory<>("name"));;
        reInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        rePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;
    }

    /**
     * searches existing parts by filtering based on either name or product ID, highlighting product IDs and isolating view
     * for names*/
    @FXML
    private void onPartSearch(ActionEvent actionEvent) {
        try {
            addProductTable.setItems(Inventory.getAllParts());
            int partId = Integer.parseInt(partSearch.getText());
            Part part = Inventory.lookupPart(partId);
            addProductTable.getSelectionModel().select(part);
            addProductTable.scrollTo(part);
            addProductTable.requestFocus();
        } catch (NumberFormatException e) {
            String partName = partSearch.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);
            addProductTable.setItems(parts);
        }
        if (!Inventory.partFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }
}