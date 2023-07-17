package com.example.controller;
/**
 * @author Ashley Jette
 * */
import com.example.model.Inventory;
import com.example.model.Part;
import com.example.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
 * initializes main menu screen, which allows user to navigate to various other screens or search existing products/parts*/
public class MainMenuController implements Initializable {

    public Button exit;
    public TextField partSearch;
    public TextField productSearch;
    Stage stage;
    Parent scene;

    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> pPartIdCol;
    public TableColumn<Part, String> pPartNameCol;
    public TableColumn<Part, Integer> pInventoryLevelCol;
    public TableColumn<Part, Double> pPriceCol;
    public Button addPart;
    public Button modifyPart;
    public Button deletePart;
    public TableView<Product> productsTable;
    public TableColumn<Product, Integer> prPartIdCol;
    public TableColumn<Product, String> prPartNameCol;
    public TableColumn<Product, Integer> prInventoryLevelCol;
    public TableColumn<Product, Double> prPriceCol;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    /**
     * <p>
     *     FUTURE ENHANCEMENT:
     *     Add more graphical elements and flair to improve aesthetics and user experience. Adding borders around tables
     *     and more variation in text fonts and styles would make the program more readable
     * </p>*/

    /**
     * populates parts table with existing information*/
    private void updatePartsTable() {
        partsTable.setItems(Inventory.getAllParts());

        pPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * opens add part menu, which allows user to add new parts to inventory*/
    public void onaddpart(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }
    /**
     * opens modify part menu, which allows user to modify existing parts and save changes*/
    public void onmodifypart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ModifyPart.fxml"));
        Parent scene = loader.load();

        ModifyPartController modPartController = loader.getController();

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        modPartController.modifyPart(selectedPart);

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * deletes existing part from inventory/view, prompting user first to confirm selection*/
    public void ondeletepart(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
            updatePartsTable();
        }

    }
    /**
     * opens add product menu, which allows user add new products to inventory*/
    public void onaddproduct(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/com/example/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * opens modify product menu, which allows user to modify existing products and save changes*/
    public void onmodifyproduct(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/ModifyProduct.fxml"));
        Parent scene = loader.load();

        ModifyProductController modProductController = loader.getController();

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        modProductController.modifyProduct(selectedProduct);

        stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * deletes existing product from inventory/view, prompting user first to confirm selection*/
    public void ondeleteproduct(ActionEvent actionEvent) {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        try {
            if(!selectedProduct.getAllAssociatedParts().isEmpty()) {
                throw new Exception();
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();

            if ((result.isPresent() && result.get() == ButtonType.OK)) {
                Inventory.deleteProduct(selectedProduct);
                System.out.println("Item successfully deleted!");
            }
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Product cannot be deleted until associated parts are removed.");
            alert.showAndWait();
        }
    }
    /**
     * prompts user to confirm action, and upon confirmation terminates program*/
    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Exit?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent() && result.get() == ButtonType.OK)) {
            System.exit(0);
        }
    }
    /**
     * searches existing parts by either part Id or name, highlighting related product IDs and isolating product names
     * */
    public void onPartSearch(ActionEvent actionEvent) {
        try {
            partsTable.setItems(Inventory.getAllParts());
            int partId = Integer.parseInt(partSearch.getText());
            Part part = Inventory.lookupPart(partId);
            partsTable.getSelectionModel().select(part);
            partsTable.scrollTo(part);
            partsTable.requestFocus();
        } catch (NumberFormatException e) {
            String partName = partSearch.getText();
            ObservableList<Part> parts = Inventory.lookupPart(partName);
            partsTable.setItems(parts);
        }
        if (!Inventory.partFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }
    /**
     * searches existing products by either product Id or name, highlighting related product IDs and isolating product names
     * */
    public void onProductSearch(ActionEvent actionEvent) {
        try {
            productsTable.setItems(Inventory.getAllProducts());
            int productId = Integer.parseInt(productSearch.getText());
            Product product = Inventory.lookupProduct(productId);
            productsTable.getSelectionModel().select(product);
            productsTable.scrollTo(product);
            productsTable.requestFocus();
        } catch (NumberFormatException e) {
            String productName = productSearch.getText();
            ObservableList<Product> products = Inventory.lookupProduct(productName);
            productsTable.setItems(products);
        }
        if (!Inventory.productFound) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No item was found.");
            alert.showAndWait();
        }
    }
    /**
     * initializes table data*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());

        pPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        pPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        pInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        pPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;

        productsTable.setItems(Inventory.getAllProducts());

        prPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));;
        prInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));;
        prPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));;
    }
}
