package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import model.Inventory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainScreen implements Initializable {

    @FXML public TableColumn partID;
    @FXML public TableColumn partName;
    @FXML public TableColumn partInventoryLevel;
    @FXML public TableColumn partPriceCost;
    @FXML public TableColumn productID;
    @FXML public TableColumn productName;
    @FXML public TableColumn productInventoryLevel;
    @FXML public TableColumn productPriceCost;
    @FXML public Button productAdd;
    @FXML public Button ProductModify;
    @FXML public Button productDelete;
    @FXML public Button partAdd;
    @FXML public Button partModify;
    @FXML public Button partDelete;
    @FXML public Button exit;
    @FXML public TextField searchParts;
    @FXML public TextField searchProducts;
    @FXML public TableView partTable;
    @FXML public TableView productTable;
    Stage stage;
    Parent scene;

    /**
     * new invref
     */
    Inventory invRef = new Inventory();

    /**
     * Initialization for tables
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partTable.setItems(invRef.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(invRef.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * product add button using screen change method
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductAdd(ActionEvent actionEvent) throws IOException {

        screenChange(actionEvent,"/view/Add Product.fxml","Add Product", true);
    }
    /**
     * product modify button using screen change method
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductModify(ActionEvent actionEvent) throws IOException {

        Product modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();
        controller.modifyProduct.setGetPassProduct(modifyProduct);
        try {

            screenChange(actionEvent, "/view/Modify Product.fxml", "Modify Product", true);

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }

    }
    /**
     * product delete button
     * @param actionEvent
     * @throws IOException
     */
    public void clickProductDelete(ActionEvent actionEvent)  {
        try {
        Product productDelete = (Product) productTable.getSelectionModel().getSelectedItem();
        ObservableList<Part> partAssociated = productDelete.getAssociatedParts();

        if (partAssociated.size() >= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Associated part - Can not delete");
            alert.showAndWait();
        } else if (partAssociated.size() == 0) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                invRef.deleteProduct(productDelete);
            }
            productTable.setItems(invRef.getAllProducts());
        }
        }catch (Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please select a product to delete");
        alert.showAndWait();
        }
    }
    /**
     * part add button using screen change method
     * @param actionEvent
     * @throws IOException
     */
    public void clickPartAdd(ActionEvent actionEvent) throws IOException {

        screenChange(actionEvent,"/view/Add Part.fxml","Add Part",true);
    }
    /**
     * part modify button using screen change method
     * @param actionEvent
     * @throws IOException
     */
    public void clickPartModify(ActionEvent actionEvent) throws IOException {
        Part modifyPart = (Part) partTable.getSelectionModel().getSelectedItem();
        controller.modifyPart.setPassPart(modifyPart);
    try {
        screenChange(actionEvent, "/view/Modify Part.fxml", "Modify Part", true);

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("please select a part to modify");
            alert.showAndWait();
        }
    }
    /**
     * part delete button
     * @param actionEvent
     * @throws IOException
     */
    public void clickPartDelete(ActionEvent actionEvent) {
        try {
            Part partDelete = (Part) partTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part? ");
            alert.setContentText(String.valueOf("Are you sure you want to delete this part? " + partDelete.getId()));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                invRef.deletePart(partDelete);

            }
            partTable.setItems(invRef.getAllParts());
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select part do be deleted");
            alert.showAndWait();
        }
    }

    /**
     * exits application
     * @param actionEvent
     */
    public void clickExit(ActionEvent actionEvent) {

        System.exit(0);
    }

    /**
     * search for products
     * @param actionEvent
     */
    public void enterSearchProduct(ActionEvent actionEvent) {
        String A = searchProducts.getText();
        ObservableList<Product> searchedProducts = Inventory.lookUpProduct(A, invRef.getAllProducts());

        productTable.setItems(searchedProducts);
    }

    /**
     * search for parts
     * @param actionEvent
     */
    public void enterSearchPart(ActionEvent actionEvent) {
        String q = searchParts.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPart(q, invRef.getAllParts());

        partTable.setItems(searchedParts);
    }

    /**
     * Screen change method
     * @param actionEvent
     * @param resourceName
     * @param title
     * @param isResize
     * @throws IOException
     */
    public void screenChange(ActionEvent actionEvent, String resourceName, String title, boolean isResize) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(resourceName));
        stage.setScene(new Scene(scene, 900, 900));
        stage.setTitle(title);
        stage.setResizable(isResize);
        stage.show();
    }
}
