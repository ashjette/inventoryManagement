package com.example.model;

/**
 * @author Ashley Jette
 * */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
/**
 * product class.
 * */
public class Product extends Inventory{
    //Declare Fields
    /**
     * associated parts list. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * product constructor. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * name getter. */
    public String getName() {
        return name;
    }
    /**
     * name setter. */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * price getter. */
    public double getPrice() {
        return price;
    }
    /**
     * price setter. */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * stock getter. */
    public int getStock() {
        return stock;
    }
    /**
     * stock setter. */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**
     * min getter. */
    public int getMin() {
        return min;
    }
    /**
     * min setter. */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * max getter. */
    public int getMax() {
        return max;
    }
    /**
     * max setter. */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * id getter. */
    public int getId() {
        return id;
    }

    /**
     * id setter. */
    public void setId(int id) {
        this.id = id;

    }
    /**
     * add associated part method.
     * used to add an associated part to a product*/
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * delete associated part method.
     * deletes associated part from product*/
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(!(selectedAssociatedPart == null)) {
            return associatedParts.remove(selectedAssociatedPart);
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("No item was found.");
        alert.showAndWait();
        return false;
    }
    /**
     * @return  associated parts list*/
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}