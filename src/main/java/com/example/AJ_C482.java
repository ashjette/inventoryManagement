package com.example;
/**
 * @author Ashley Jette
 * */
import com.example.model.InhousePart;
import com.example.model.Inventory;
import com.example.model.OutsourcedPart;
import com.example.model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * main class.
 * <p>RUNTIME ERROR: I experienced NullPointerException error when trying to start the program. I had to restructure my files in order to comply with Maven. After putting FXML files
 * resources folder program ran as intended and error cleared</p>
 * */
public class AJ_C482 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /**
     * adds parts and products to inventory for testing
     * */
    public static void main(String[] args) {


        //create part objects
        InhousePart part1 = new InhousePart(362, "GPU", 769.00, 876, 50, 1000, 9872);
        InhousePart part2 = new InhousePart(546, "RAM", 119.99, 5899, 50, 10000, 4641);
        InhousePart part3 = new InhousePart(781, "Chassis", 79.99, 898, 50, 1000, 7841);
        OutsourcedPart part4 = new OutsourcedPart(9437, "Motherboard", 215.00, 89, 50, 1000, "ZoomZoop");
        OutsourcedPart part5 = new OutsourcedPart(1396, "CPU", 359.89, 489, 50, 1000, "ExtraTerrestrial");
        OutsourcedPart part6 = new OutsourcedPart(7941, "Power Supply", 89.99, 788, 50, 1000, "HiP");

        //Add Test Parts to Observable List
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);

        //Add Products for Testing
        Product product1 = new Product(999,"Basic Desktop",656.99,85,50,1000);
        Product product2 = new Product(998,"High Performance Laptop",1199.99,44,25,1000);
        Product product3 = new Product(997,"Current Gen High Performance Desktop",1899.99,565,50,1000);

        //Add Products to Observable List
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        //Add Associated Parts to Products
        product1.addAssociatedPart(part6);
        product2.addAssociatedPart(part5);
        product3.addAssociatedPart(part2);
        launch();
    }

}
