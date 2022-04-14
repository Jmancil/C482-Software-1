package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.util.Optional;
import java.awt.event.MouseEvent;

public class AddPart {

    @FXML  public Label machineID;
    @FXML private Label label;
    @FXML private Label errorLabel;
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
    @FXML private TextField id;

    /**
     * variable for part auto gen
     */
    private int autoPartID;
    /**
     * Link to mainscreen to use method for screen changing
     */
    private MainScreen mainScreenController = new MainScreen();

    /**
     * ref to inv
     */
    Inventory invRef = new Inventory();

    /**
     *  Saving part to inv
     * @param actionEvent
     * @throws Exception
     */
    public void partSave(javafx.event.ActionEvent actionEvent) throws Exception {

    try {
/**
 * incrementing part ID
 */
        autoPartID = Inventory.getPartGenID();
        /**
         * checks inhouse vs outsourced part selection
         */
        if (inHouseRadio.isSelected()) {
            int id = autoPartID;
            String pName = partName.getText();
            int partInv = Integer.parseInt(partInventory.getText());
            double cost = Double.parseDouble(partCost.getText());
            int partM = Integer.parseInt(partMax.getText());
            int partMi = Integer.parseInt(partMin.getText());
            int machine = Integer.parseInt(partMachineID.getText());
            InHouse inHouse = new InHouse(id, pName, cost, partInv, partMi, partM, machine);

            /**
             * user value check method
             */
            if (inHouse.partCheck()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding In House Part:");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    invRef.addPart(inHouse);

                    System.out.println("Added Part");

                    mainScreenController.screenChange(actionEvent, "/view/Main Screen.fxml", "Inventory Management System", true);
                }
            }
        }
        /**
         * checks if outsource selected
         */
        if (outsourcedRadio.isSelected()) {
            int id = autoPartID;
            String pName = partName.getText();
            int partInv = Integer.parseInt(partInventory.getText());
            double cost = Double.parseDouble(partCost.getText());
            int partM = Integer.parseInt(partMax.getText());
            int partMi = Integer.parseInt(partMin.getText());
            String companyName = partMachineID.getText();
            Outsourced outsourced = new Outsourced(id, pName, cost, partInv, partMi, partM, companyName);
            /**
            * outsource part check method
             */
            if (outsourced.oPartCheck()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Adding OutSourced Part:");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    invRef.addPart(outsourced);
                    System.out.println("Added Part");
                    mainScreenController.screenChange(actionEvent, "/view/Main Screen.fxml", "Inventory Management System", true);
                }
            }
        }
    }catch (java.lang.RuntimeException e){
        System.out.println(e.toString());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Enter valid input");
        alert.showAndWait();
        }
    }

    /**
     * part add cancel to main
     * @param actionEvent
     * @throws IOException
     */
        public void clickPartCancel (javafx.event.ActionEvent actionEvent) throws IOException {
            System.out.println("Cancel pushed");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel and return");
            alert.setHeaderText("Are you sure");
            alert.setContentText("Ok to continue");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.out.println("Cancel Pushed");
                mainScreenController.screenChange(actionEvent,"/view/Main Screen.fxml","Inventory Management System",true);
            } else {
                errorLabel.setText("Returned to the add part screen.");
            }
        }

    /**
     * radio selection to change machineID to and from Company name
     * @param actionEvent
     */
        public void inHouseRadio (javafx.event.ActionEvent actionEvent){
            if (inHouseRadio.isSelected()) {
                machineID.setText("Machine ID");

            }
        }
    /**
     * radio selection to change machineID to and from Company name
     * @param actionEvent
     */
        public void outsourcedRadio (javafx.event.ActionEvent actionEvent){
            if (outsourcedRadio.isSelected()) {
                machineID.setText("Company Name");
                System.out.println("outSourced Selected");
            }
        }


    }

