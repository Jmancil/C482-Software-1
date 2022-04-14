package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Product;
import model.Inventory;


/**
 * Future enhancement - The ability to save the information within this application seems vital, in the future I would
 * include an SQL database connection. Utilizing a database would make the information query-able for enhanced data driven decisions
 * also increasing organization and keeping all the data in a centralized location, allowing data backup and redundancies
 * to be implemented.
 *
 * Error reso:
 * The biggest problem I faced when creating this project was the implementation and utilization of observable list arrays
 * and how to manipulate them - specifically the associated parts. Initially I populated all the parts to the add and modify
 * product screen but adding and saving to each product proved tricky for myself. When I first wrote my add associated part
 * button I was adding associated parts to a product, but could not add more parts to different products past the first.
 * I realized this was due to not having a separated associated part list and creating one and assigning the parts to
 * that fixed my issues.
 */


public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main Screen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1000, 800));
        stage.show();

        /**
         * Project main launches project
         * @param Stage
         * @throws Exception
         */

    }

    public static void main(String[] args){

        /**
         * Main Initialization
         * @param args
         */

        Inventory invRef = new Inventory();

        InHouse test = new InHouse(99, "Joe", 1.0, 1, 1, 1, 1);
        InHouse test2 = new InHouse(98, "Gordon", 2, 1, 1, 1, 1);
        InHouse test3 = new InHouse(97, "Alabaster", 3, 1, 1, 1, 1);
        InHouse test4 = new InHouse(96, "Jordan", 4, 2, 3, 4, 1);

        Product test5 = new Product(99, "test5",5, 20, 10,2);
        Product test6 = new Product(98, "test6", 5,20,10,2);
        Product test7 = new Product(97, "test7", 5,20,10,2);
        Product test8 = new Product(96, "test8", 195, 1, 1, 1);

        invRef.addPart(test);
        invRef.addPart(test2);
        invRef.addPart(test3);
        invRef.addPart(test4);

        invRef.addProduct(test5);
        invRef.addProduct(test6);
        invRef.addProduct(test7);
        invRef.addProduct(test8);

        launch(args);
    }
}