package com.example.model;
/**
 * @author Ashley Jette
 * */

/**
 * inhouse parts class. */
public class InhousePart extends Part {
    /**
     * machine ID integer for inhouse parts*/
    private int machineId;

    /**
     * Constructor for in house parts.
     * */
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineId = machineID;
    }

   /**
    * machine id getter
    * */
    public int getMachineId() {
        return machineId;
    }
    /**
     * machine id setter
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}
