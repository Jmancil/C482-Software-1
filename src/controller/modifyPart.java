package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class modifyPart implements Initializable {
    @FXML public ToggleGroup inHouseOutSourced;
    @FXML public RadioButton outSourcedRadio;
    @FXML public javafx.scene.control.Label machineID;
    @FXML public TextField partID;
    @FXML public TextField partName;
    @FXML public TextField partInventory;
    @FXML public TextField partCost;
    @FXML public TextField partMax;
    @FXML public TextField partMachineID;
    @FXML public TextField partMin;
    @FXML public Button partSave;
    @FXML public Button partCancel;
    @FXML public RadioButton inHouseRadio;
    @FXML public RadioButton outsourcedRadio;
    @FXML private Label label;
    @FXML private Label errorLabel;
    @FXML private static Part passPart = null;
    Stage stage;
    Parent scene;
    /**
     * link to mainscreen change screen method
     */
    private MainScreen mainScreenController = new MainScreen();
    /**
     * new inv ref
     */
    Inventory invRef = new Inventory();

    /**
     * Initialization of passpart
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPassPart(passPart);
    }

    /**
     * setter for passpart
     * @param passPart
     */
    public static void setPassPart(Part passPart){
        modifyPart.passPart = passPart;
    }

    /**
     * showing passpart from mainscreen on modify
     * @param passPart
     */
    private void showPassPart(Part passPart) {

        partID.setText((String.valueOf(passPart.getId())));
        partName.setText(String.valueOf(passPart.getName()));
        partCost.setText(String.valueOf(passPart.getPrice()));
        partInventory.setText(String.valueOf(passPart.getStock()));
        partMin.setText(String.valueOf(passPart.getMin()));
        partMax.setText(String.valueOf(passPart.getMax()));
        /**
         * if else to determine if inhouse or outsourced
         */
        if (passPart instanceof InHouse) {
            partMachineID.setText(Integer.toString(((InHouse) passPart).getMachineID()));
            machineID.setText("Machine ID ");
            inHouseRadio.setSelected(true);

        } else {
            partMachineID.setText(String.valueOf(((Outsourced) passPart).getCompanyName()));
            machineID.setText("Company Name ");
            outSourcedRadio.setSelected(true);
        }
    }

    /**
     * cancel from modify screen to main screen
     * @param actionEvent
     * @throws IOException
     */
    public void partCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Click to continue");
        alert.setHeaderText("Are you sure?");
        alert.setTitle("Cancel and Return");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Cancel Pushed");
            mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System",true);
        }
        else{
            errorLabel.setText("You have returned to the modify part screen.");
        }
    }

    /**
     * saving part after modifying
     * @param actionEvent
     * @throws IOException
     */
    public void partSave(ActionEvent actionEvent) throws IOException {
/**
 * if to determine inhouse selection
 */
        if (inHouseRadio.isSelected()) {
            int id = passPart.getId();
            String pName = partName.getText();
            int partInv = Integer.parseInt(partInventory.getText());
            double cost = Double.parseDouble(partCost.getText());
            int partM = Integer.parseInt(partMax.getText());
            int partMi = Integer.parseInt(partMin.getText());
            int machine = Integer.parseInt(partMachineID.getText());
            InHouse inHouse = new InHouse(id, pName, cost, partInv, partMi, partM, machine);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding inhouse:");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    invRef.partUpdate(id, inHouse);

                    System.out.println("Added Part");
                    mainScreenController.screenChange(actionEvent, "/view/Main Screen.fxml", "Inventory Management System", true);
                }
        }
        /**
         * if to determine outsource selection
         */
        if (outSourcedRadio.isSelected()) {
            int id = passPart.getId();
            String pName = partName.getText();
            int partInv = Integer.parseInt(partInventory.getText());
            double cost = Double.parseDouble(partCost.getText());
            int partM = Integer.parseInt(partMax.getText());
            int partMi = Integer.parseInt(partMin.getText());
            String companyName = partMachineID.getText();
            Outsourced outsourced = new Outsourced(id, pName, cost, partInv, partMi, partM, companyName);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Adding Outsourced:");
            alert.setContentText("id " + id + "Part name " + partName + "Stock " + partInventory + "Cost " + partCost + "Max " + partMax + "Min " + partMin
                    + "Company Name " + machineID);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                invRef.partUpdate(id, outsourced);
                System.out.println("Added Part");
                mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System",true);
            }
        }
    }

    /**
     * changing of machine id to and from company name
     * @param actionEvent
     */
    public void inHouseRadio(ActionEvent actionEvent) {
        if (inHouseRadio.isSelected()) {
            machineID.setText("Machine ID");
        }
    }

    public void outSourcedRadio(ActionEvent actionEvent) {
        if(outSourcedRadio.isSelected()){
            machineID.setText("Company Name");
        }
    }

}





