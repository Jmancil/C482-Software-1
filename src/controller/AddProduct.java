package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProduct implements Initializable {


    @FXML public TextField productID;
    @FXML public TextField productName;
    @FXML public TextField inventoryProduct;
    @FXML public TextField priceProduct;
    @FXML public TextField maxProduct;
    @FXML public TextField minProduct;
    @FXML public TableColumn partIDColumn;
    @FXML public TableColumn partNameColumn;
    @FXML public TableColumn partInventoryColumn;
    @FXML public TableColumn partCostColumn;
    @FXML public TableColumn associatedIDColumn;
    @FXML public TableColumn associatedPartColumn;
    @FXML public TableColumn associatedInventoryColumn;
    @FXML public TableColumn associatedPriceColumn;
    @FXML public TextField searchPart;
    @FXML public Button addButton;
    @FXML public Button productDelete;
    @FXML public Button saveButton;
    @FXML public Button cancelButton;
    @FXML public TableView productTable;
    @FXML public TableView partTable;
    @FXML private Label errorLabel;
    @FXML private Part addSelectedPart;
    @FXML private Part addSelectedAssociatedPart;
    @FXML private Product productSelection;
    Stage stage;
    Parent scene;
    /**
     * auto part var
     */
    private int autoPartID;
    /**
     * link to main screen for screen change method
     */
    private MainScreen mainScreenController = new MainScreen();

    /**
     * associated part list for products
     */
    private ObservableList<Part> associatedProductList = FXCollections.observableArrayList();
    /**
     * inv ref initialize
     */
    Inventory invRef = new Inventory();

    /**
     * Initializer
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showPart();
    }

    /**
     * shows parts
     */
    private void showPart(){
        partTable.setItems(invRef.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Cancels from add product screen to main
     * @param actionEvent
     * @throws IOException
     */
    public void cancelProduct(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Click to continue");
        alert.setHeaderText("Are you sure?");
        alert.setTitle("Cancel and Return");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Cancel Pushed");
            mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Add product",true);
        } else {
            errorLabel.setText("Returned to product screen.");
        }
    }

    /**
     * saves product and or associated parts to a product
     * @param actionEvent
     * @throws Exception
     */
    public void saveProduct(ActionEvent actionEvent) throws Exception {

        try {
            /**
             * auto ID gen
             */
            autoPartID = Inventory.getPartGenID();
            int id = autoPartID;
            String pName = productName.getText();
            int partInv = Integer.parseInt(inventoryProduct.getText());
            double cost = Double.parseDouble(priceProduct.getText());
            int partM = Integer.parseInt(maxProduct.getText());
            int partMi = Integer.parseInt(minProduct.getText());
            Product product = new Product(id, pName, partInv, (int) cost, partM, partMi);
/**
 * checking user input values method
 */
            if (product.productCheck()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding Product:");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    invRef.addProduct(product);
                    invRef.productUpdate(id, product, associatedProductList);
                    mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System",true);

                } else{
                    mainScreenController.screenChange(actionEvent,"/view/Modify Product.fxml","Modify Product",true);
                }
            }
                } catch(java.lang.RuntimeException e){
                    System.out.println(e.toString());
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Enter valid input");
                }
            }


    /**
     * adds associated parts to product
     * @param actionEvent
     * @throws IOException
     */
    public void addButton(ActionEvent actionEvent) throws IOException {
        Part addToAssociatedProductTable = (Part) partTable.getSelectionModel().getSelectedItem();

        if(addToAssociatedProductTable != null) {

            associatedProductList.add(addToAssociatedProductTable);
            productTable.setItems(associatedProductList);
            associatedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select part to be added");
            alert.showAndWait();
        }
    }

    /**
     * Removes Associated parts from a product
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductDelete(ActionEvent actionEvent) throws IOException {
        Product productDelete = (Product) productTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            invRef.deleteProduct(productDelete);
        }
        productTable.setItems(invRef.getAllProducts());

    }

    /**
     * search for products in add product screen
     * @param actionEvent
     */
    public void enterSearchPart(ActionEvent actionEvent) {
        String q = searchPart.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPart(q, invRef.getAllParts());

        partTable.setItems(searchedParts);

    }


















}
