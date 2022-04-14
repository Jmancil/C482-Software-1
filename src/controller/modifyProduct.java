package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyProduct implements Initializable {
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
    @FXML private Label label;
    @FXML private Label errorLabel;
    Stage stage;
    Parent scene;
    /**
     * passproduct variable
     */
    public static Product getPassProduct = null;
    /**
     * link to mainscreen method for screenChange
     */
    private MainScreen mainScreenController = new MainScreen();

    /**
     * creating of modify associated parts list
     */
    ObservableList<Part> modifyAssociatedProductList = FXCollections.observableArrayList();

    /**
     * setter for pass prodcut
     * @param getPassProduct
     */
    public static void setGetPassProduct(Product getPassProduct) {
        modifyProduct.getPassProduct = getPassProduct;
    }

    /**
     * initialization for parts, passproducts and associated parts
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        showPart();
        showPassProduct(getPassProduct);
        showAssociatedParts();
    }

    /**
     * passing parts in to table
     */
    private void showPart(){
        partTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * passing products in
     * @param passProduct
     */
    private void showPassProduct(Product passProduct) {

        productID.setText((String.valueOf(passProduct.getProductId())));
        productName.setText(String.valueOf(passProduct.getName()));
        inventoryProduct.setText(String.valueOf(passProduct.getStock()));
        priceProduct.setText(String.valueOf(passProduct.getPrice()));
        maxProduct.setText(String.valueOf(passProduct.getMax()));
        minProduct.setText(String.valueOf(passProduct.getMin()));
    }

    /**
     * passing associated parts in
     */
    private void showAssociatedParts(){
        modifyAssociatedProductList = getPassProduct.getAssociatedParts();
        productTable.setItems(modifyAssociatedProductList);
        associatedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * add button for associated parts
     * @param actionEvent
     * @throws IOException
     */
    public void addButton(ActionEvent actionEvent) throws IOException {
        Part addToAssociatedProductTable = (Part) partTable.getSelectionModel().getSelectedItem();

        if(addToAssociatedProductTable != null) {

            modifyAssociatedProductList.add(addToAssociatedProductTable);
            productTable.setItems(modifyAssociatedProductList);
            associatedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            for(int i = 0; i < getPassProduct.getAssociatedParts().size(); i++) {
                System.out.println(getPassProduct.getAssociatedParts().get(i).getName());
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select part to be added");
            alert.showAndWait();
        }
    }

    /**
     * delete button for associated parts
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductDelete(ActionEvent actionEvent) throws IOException {
        Part removeAssociated = (Part) productTable.getSelectionModel().getSelectedItem();

        if(removeAssociated != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this associated part?");
            Optional<ButtonType> result = alert.showAndWait();
        }
        getPassProduct.deleteAssociatedPart(removeAssociated);
    }

    /**
     * save button for modify product
     * @param actionEvent
     * @throws IOException
     */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {

            int id = getPassProduct.getProductID();
            String pName = productName.getText();
            int partInv = Integer.parseInt(inventoryProduct.getText());
            double cost = Double.parseDouble(priceProduct.getText());
            int partM = Integer.parseInt(maxProduct.getText());
            int partMi = Integer.parseInt(minProduct.getText());
            Product product = new Product(id, pName, partInv, (int) cost, partM, partMi);

            modifyAssociatedProductList = getPassProduct.getAssociatedParts();
            Inventory.productUpdate(id, product, modifyAssociatedProductList);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Updating Product");
            Optional<ButtonType> result = alert.showAndWait();

        } catch (java.lang.RuntimeException e) {
            System.out.println(e.toString());
        }
        finally {
            mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System", true);
        }
    }

    /**
     * search method for modify part screen
     * @param actionEvent
     */
    public void enterSearchPart(ActionEvent actionEvent) {
        String q = searchPart.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPart(q, Inventory.getAllParts());

        partTable.setItems(searchedParts);
    }

    /**
     * cancel button for modify part screen
     * @param actionEvent
     * @throws IOException
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        for(int i = 0; i < getPassProduct.getAssociatedParts().size(); i++) {
            System.out.println(getPassProduct.getAssociatedParts().get(i).getName());
        }
            System.out.println("Cancel pushed");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel and return");
            alert.setHeaderText("Are you sure");
            alert.setContentText("Ok to continue");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.out.println("Cancel Pushed");
                mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System",true);
            } else {
                errorLabel.setText("Returned to the add part screen.");
            }
        }
}



