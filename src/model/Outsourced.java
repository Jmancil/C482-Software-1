package model;

/**
 *
 * @author James Mancil
 */

import javafx.scene.control.Alert;



public class Outsourced extends Part {
    /**
     * companyName string extends part Class
     */
    private String companyName;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * companyName getter and setter
     * @param companyName
     */
    public void setCompanyName(String companyName){

    this.companyName = companyName;
    }

    public String getCompanyName(){

        return companyName;
    }

    /**
     * Boolean outsourced part check for user input
     * @return
     * @throws Exception
     */

    public boolean oPartCheck() throws Exception {

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

        } else if (getCompanyName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid company name");
            alert.showAndWait();
            throw new Exception("Must enter company name");

        } else if (getStock() < getMin() || getStock() > getMax()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inventory should be between Max and Min");
            alert.showAndWait();
            throw new Exception("Not a number");
        }
        return true;
    }

}
