package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Part;
import model.Product;
import controller.MainScreen;
import java.util.ArrayList;

/**
 *
 * @author James Mancil
 */


public class Inventory {
    /**
     * observable array list for parts and products initialization
     * */

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * auto-gen ID
     */

    private static int partGenID = 0;

    /**
     * Adding part method
     * @param newPart
     */

    public static void addPart(Part newPart){

    allParts.add(newPart);
    }

    /**
     * Getters and setters for lists
     * @return
     */

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static void addProduct(Product product){

        allProducts.add(product);
        }
    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * partgen Getter
     * @return
     */

    public static int getPartGenID() {
        partGenID++;
        return partGenID;
    }

    /**
     * Booleans for deleting parts and products
     * @param selectedPart
     * @return
     */

    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);

        }
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
        }
        return true;
    }

    /**
     * product and part update methods
     * @param id
     * @param part
     */

    public static void partUpdate(int id, Part part){
        int index = -1;
        for(Part parts : Inventory.getAllParts()){
            index++;
            if(parts.getId() == id){
                allParts.set(index, part);
                return;
            }
        }
    }

    public static void productUpdate(int id, Product product, ObservableList<Part> associatedPartList) {
        int index = -1;
        for (Product products : Inventory.getAllProducts()) {
            index++;
            if (products.getProductId() == id){
                allProducts.set(index, product);
                allProducts.get(index).setAssociatedParts(associatedPartList);

                return;
            }
        }
    }

    /**
     * methods for part and product search / lookup
     * @param partialName
     * @param allParts
     * @return
     */

    public static ObservableList<Part> lookUpPart(String partialName, ObservableList<Part> allParts) {   /** observable list parses partName using lookUpPart method */

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
            for (Part part : allParts) {
                String x = Integer.toString(part.getId());
                if (part.getName().toLowerCase().contains(partialName.toLowerCase())) {
                    filteredParts.add(part);
                }
                else if (x.contains(partialName)) {
                    filteredParts.add(part);
                }

            }
            if (filteredParts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No part found");
                alert.showAndWait();
            }
            return FXCollections.observableArrayList(filteredParts);
        }

        public static ObservableList<Product> lookUpProduct(String partialName, ObservableList<Product> allProducts){/** observable list parses productName using lookUpProduct method */
            ObservableList<Product> filteredProduct = FXCollections.observableArrayList();
            for (Product product : allProducts) {
                String T = Integer.toString(product.getProductId());
                if (product.getName().toLowerCase().contains(partialName.toLowerCase())) {
                    filteredProduct.add(product);
                }
                else if (T.contains(partialName)){
                    filteredProduct.add(product);
                }
            }
            if(filteredProduct.size() == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No Product found");
                alert.showAndWait();
            }
            return FXCollections.observableArrayList(filteredProduct);
        }
    }

