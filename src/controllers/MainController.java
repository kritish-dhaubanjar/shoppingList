package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.ShoppingItem;
import service.ShoppingList;

import java.io.IOException;
import java.util.Optional;

/*
* Controller of /layouts/main.fxml
* */
public class MainController {

    // JavaFX Scene Layouts and Control references to /layouts/main.fxml
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button addItem, budgetBtn;
    @FXML
    TextField budget;
    @FXML
    TableView<ShoppingItem> table;
    @FXML
    TableColumn<ShoppingItem, String> itemName;
    @FXML
    TableColumn<ShoppingItem, Integer> itemPriority, itemQty;
    @FXML
    TableColumn<ShoppingItem, Double> itemPrice, totalPrice;
    @FXML
    Text result;
    @FXML
    Button clear;

    // Shopping List
    private static ShoppingList myList = new ShoppingList();

    // initialize() runs at the very start every time the scene is created.
    @FXML
    public void initialize(){
        System.out.println("Initialized");
        this.addItem.setDisable(true);                              // initially 'Add Item' button is disabled
        this.budget.setOnAction(e->setBudget());                    // Event Handler for budget on 'Enter' key
        this.budgetBtn.setOnAction(e->setBudget());                 // Event Handler for 'Okay' button
        this.addItem.setOnAction(e->addItem());                     // Event Handler for 'Add Item' button
        this.clear.setOnAction(e->resetAll());

        // TableColumns of TableView of main.fxml is bounded to corresponding attributes names from ShoppingItem
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriority.setCellValueFactory(new PropertyValueFactory<>("itemPriority"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        itemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));


        // Setting the Table with the shopping list, which is observable
        table.setItems(ShoppingList.shoppingList);
    }

    // Event Handler to Set the budget
    private void setBudget(){
        myList.setBudget(Double.valueOf(budget.getText()));
        this.budgetBtn.setDisable(true);
        this.budget.setEditable(false);
        this.addItem.setDisable(false);
    }

    // Event Handler to prompt a dialog to add Items, 6 successful times.
    private void addItem(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.anchorPane.getScene().getWindow());
        dialog.setTitle("Add Item");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layouts/addItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            e.printStackTrace();
        }
        dialog.setTitle("Add an item");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result;

        // For only 6 items
        /*
        int count = 1;

        do{
            dialog.setTitle("Add item " + count + " of 6.");
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AddItemDialogController addItemController = fxmlLoader.getController();
                if(addItemController.addItem(myList)){
                    count ++;
                }
            }
        }while (count <= 6);
        */

        //For unlimited Items
        boolean exit = false;

        do {
            result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.NEXT) {
                AddItemDialogController addItemController = fxmlLoader.getController();
                addItemController.addItem(myList);
            }else if(result.isPresent() && result.get() == ButtonType.CANCEL){
                exit = true;
            }
        }while (!exit);

        myList.sortPriority();

        // Finally present the result of the shopping list at the scroll pane.
        this.result.setText(myList.calculateCost());
    }

    //Reset the State
    private void resetAll(){
        ShoppingList.shoppingList.clear();
        this.budget.setEditable(true);
        this.budgetBtn.setDisable(false);
    }

}
