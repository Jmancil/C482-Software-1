package model;
/**
 *
 * @author James Mancil
 */
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import java.io.IOException;

public class Product {

    /**
     * Variables for product
     */

    private int productID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * part list for associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * constructor for products
     * @param productID
     * @param name
     * @param stock
     * @param price
     * @param max
     * @param min
     */

    public Product(int productID, String name, int stock, double price, int max, int min){

        this.productID = productID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Getters and Setters for everything
     */

    public Product() { }

    public void setProductID(Integer productID){
        this.productID = productID;
    }

    public int getProductID(){
        return this.productID;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){

        return this.name;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public void setStock(int stock){
        this.stock = stock;
    }

    public int getStock(){
        return this.stock;
    }

    public void setMin(int min){
        this.min = min;
    }

    public int getMin(){
        return this.min;
    }

    public void setMax(int max){
        this.max = max;
    }

    public int getMax(){
        return this.max;
    }

    public boolean deleteAssociatedPart(Part selectAssociatedPart){
        return associatedParts.remove(selectAssociatedPart);
    }

    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
    }

    public void setProductID(int id){
        this.productID = id;
    }

    public int getProductId() {
        return this.productID;
    }

    public void setPartID(int id){
        this.productID = id;
    }

    public int getPartID(){
        return associatedParts.size();
    }

    /**
     * Boolean checker for user input on products
     * @return
     * @throws Exception
     */

    public boolean productCheck() throws Exception {

        if(getName().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter name");
            alert.showAndWait();
            throw new Exception("No Name");
        }
        else if (getStock() < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter positive stock");
            alert.showAndWait();
            throw new Exception("Not a positive number");
        }
        else if (getPrice() < 0.00){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter Positive cost");
            alert.showAndWait();
            throw new Exception("Not a positive number");
        }
        else if (getMax() < getMin()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Maximum should be larger than minimum");
            alert.showAndWait();
            throw new Exception("Max must be larger than Min");
        }
        else if (getMin() > getMax()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Minimum should be smaller than maximum");
            alert.showAndWait();
            throw new Exception("Min must be less than max");
        }
        else if (getStock() > getMax() || getStock() < getMin()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Inventory should be between Max and Min");
            alert.showAndWait();
            throw new Exception("Stock must be between Max and Min");
        }
        return true;
    }
}
