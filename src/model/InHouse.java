package model;

import javafx.scene.control.Alert;

/**
 *
 * @author James Mancil
 */

public class InHouse extends Part {      /**  InHouse class to extend part class */

    /**
     * private Var added to part class
     */
    private int machineID;

    /**
     * Super constructor extends part class
     * @param id
     * @param name
     * @param price
     * @param price
     * @param stock
     * @param min
     * @param max
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max); /** Super uses parts default constructor */
        this.machineID = machineID;
    }

    /**
     * machine id GET & SET
     * @param machineID
     */
    public void setMachineID(int machineID) {  /** setter and getter */
        this.machineID = machineID;
    }

    public int getMachineID() {

        return machineID;
    }

    /**
     * Boolean method that checks parts for correct field input
     * @return
     * @throws Exception
     */

    public boolean partCheck() throws Exception {

        if (getName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter a name");
            alert.showAndWait();
            throw new Exception("Must enter a name");
        }
          else if (getPrice() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter value greater than 0");
            alert.showAndWait();
            throw new Exception("Price must be larger than 0");

        } else if (getMax() < getMin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Max should be larger than Min");
            alert.showAndWait();
            throw new Exception("Max should be larger than Min");

        } else if (getMin() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter value greater than 0");
            alert.showAndWait();
            throw new Exception("Minimum should be larger than 0");

        } else if (getMachineID() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid machine id");
            alert.showAndWait();
            throw new Exception("Machine ID should be larger than 0");

        } else if (getStock() < getMin() || getStock() > getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inventory should be between Max and Min");
            alert.showAndWait();
            throw new Exception("Not a number");
//            return false;
        }
        return true;
    }
}
