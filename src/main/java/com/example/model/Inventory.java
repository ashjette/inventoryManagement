package com.example.model;

/**
 * @author Ashley Jette
 * */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class.
 Contains methods for altering parts and products* */
public class Inventory {
    //fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static boolean partFound;
    public static boolean productFound;

/**
 * Add part method.
 * adds new parts to inventory*/
    public static void addPart(Part newPart) { allParts.add(newPart);}

    /**
     * Add product method.
     * adds new products to inventory*/
    public static void addProduct(Product newProduct) { allProducts.add(newProduct);}

    /**
     * Lookup part method.
     * iterates through parts lists to find part with matching criteria*/
    public static Part lookupPart(int partId) {
        partFound = false;

        for(Part part : allParts) {
            if(part.getId() == partId) {
                partFound = true;
                return part;
            }
        }
        return null;
    }
    /**
     * Lookup product method.
     * iterates through products lists to find part with matching criteria*/
    public static Product lookupProduct(int productId) {
        productFound = false;

        for(Product product : allProducts) {
            if(product.getId() == productId) {
                productFound = true;
                return product;
            }
        }
        return null;
    }

    /**
     * Lookup part method.
     * iterates through parts lists to find part with matching criteria*/
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        partFound = false;

        for(Part part : allParts) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())){
                filteredParts.add(part);
            }
        }
        if(filteredParts.isEmpty()) {
            return allParts;
        }
        partFound = true;
        return filteredParts;
    }

    /**
     * Lookup product method.
     * iterates through products lists to find part with matching criteria*/
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        productFound = false;

        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        if(filteredProducts.isEmpty()) {
            return allProducts;
        }
        productFound = true;
        return filteredProducts;
    }

/**
 * update part method.
 * Used to update a specific part*/
    public static void updatePart(int index, Part selectedPart) { allParts.set(index, selectedPart);}

    /**
     * update product method.
     * Used to update a specific product*/
    public static void updateProduct(int index, Product newProduct) { allProducts.set(index, newProduct);}

/**
 * delete part method.
 * Used to delete specific part from list*/
    public static boolean deletePart(Part selectedPart) { return allParts.remove(selectedPart);}

    /**
     * delete product method.
     * Used to delete specific product from list*/
    public static boolean deleteProduct(Product selectedProduct) { return allProducts.remove(selectedProduct);}

    /**
     * returns all parts list*/
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * returns all products list*/
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}

