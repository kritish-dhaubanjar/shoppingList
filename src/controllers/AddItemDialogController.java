package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.ShoppingItem;
import service.ShoppingList;

import java.io.IOException;

public class AddItemDialogController {

    @FXML
    TextField itemName, itemPriority, itemPrice, itemQty;

    // Alternative to ProgException, with Error Alert Dialogs.

    private void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    // This method handles getting itemName, itemPriority, itemPrice, itemQty from the dialog.

    public void addItem(ShoppingList myList){
        String itemName = this.itemName.getText();
        int itemPriority = 0;
        double itemPrice = 0;
        int itemQty = 0;

        try {
            itemPriority = Integer.parseInt(this.itemPriority.getText());
            itemPrice = Double.valueOf(this.itemPrice.getText());
            itemQty = Integer.parseInt(this.itemQty.getText());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        itemName = itemName.replaceAll("[^a-zA-Z]", "");

        // Handle Invalid Inputs in the Add Item Dialog.

        if (itemPriority < 1 || itemPriority > 7) {
            showErrorAlert("Priority: Enter Valid Priority from 1 to 7");
        }
        else if (itemPrice < 1) {
            showErrorAlert("Price: Only positive numbers can be entered.");
        }
        else if (itemQty < 1) {
            showErrorAlert("Quantity: Only positive numbers can be entered.");
        }

        // Check if the to-be-added item attributes confirms to specifications

        if(itemName.length() > 0 && itemPriority > 0 && itemPriority < 8 && itemPrice > 0 && itemQty > 0) {

            // Check if the item is already present in the ShoppingList

            for (ShoppingItem shoppingItems : ShoppingList.shoppingList){
                if(shoppingItems.getItemName().toLowerCase().equals(itemName.toLowerCase())){
                    showErrorAlert("ItemName: Item already in the list.");
                    return;
                }
            }
            try {
                myList.addItem(itemName, itemPriority, itemPrice, itemQty);
            } catch (ClassNotFoundException | IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
            }

            // Reset the values of the dialog if item was added successfully, else retains the previous values.

            this.itemName.setText("");
            this.itemPriority.setText("");
            this.itemPrice.setText("");
            this.itemQty.setText("");
        }
    }
}
