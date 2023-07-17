package com.example.model;

/**
 * @author Ashley Jette
 * */
/**
 * outsourced part class. */
public class OutsourcedPart extends Part {

    /** used for outsourced parts*/
    private String companyName;

    /**
     * outsourced part constructor.
     * @param id **
     * @param max **
     * @param companyName **
     * @param name **
     * @param min **
     * @param stock **
     * @param price ***/
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;

        }

    /**
     * @return company name */
    public String getCompanyName() {
        return companyName;
    }
/**
 * company name setter*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

