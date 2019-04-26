package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ShoppingItem;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class ShoppingList extends PrioritySort implements PrintingOutput {

    // A list that allows 'TableView' in 'main.fxml' to track changes when they occur.

    public static ObservableList<ShoppingItem> shoppingList = FXCollections.observableArrayList();

    private double budget;

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public void addItem() throws FileNotFoundException, IOException, ClassNotFoundException {
        /* For console interaction, inputs could be taken through addItem() and Scanners with exception handling.
        *  However, through Views and Controllers, it is necessary to convert addItem() to API like
        *  calls with parameters, and all the exception handling is done in the Controllers.
        * */
    }

    // Takes input of item name, priority, price, and quantity as an api call.

    public void addItem(String itemName, int itemPriority, double itemPrice, int itemQty) throws IOException, ClassNotFoundException {
        File file = new File("shoppingList.txt");
        double totalPrice = itemPrice * itemQty;

        ShoppingItem Item = new ShoppingItem(itemName, itemPriority, itemPrice, itemQty, totalPrice);
        shoppingList.add(Item);
        System.out.println("Item Added");

        // Serialize the collection of students
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        for (ShoppingItem shop : shoppingList) {
            output.writeObject(shop);
        }
        output.close();
        fo.close();

        // deserializable the file back into the collection
        FileInputStream fi = new FileInputStream(file);
        @SuppressWarnings("resource")
        ObjectInputStream input = new ObjectInputStream(fi);

        ArrayList<ShoppingItem> shoppingListdups = new ArrayList<ShoppingItem>();

        try {
            while (true) {
                ShoppingItem shop = (ShoppingItem) input.readObject();
                shoppingListdups.add(shop);
            }
        } catch (EOFException ex) {
            System.out.println(ex.getMessage());
        }
        for (ShoppingItem shop : shoppingListdups) {
            System.out.println("You Have This item on Cart. ");
            System.out.println(shop);
            System.out.println("________________________________");
        }
    }

    // After user enter all the data the sortPriority method helps to
    // sort the priority in ascending order.

    @Override
    public void sortPriority() {
        Collections.sort(shoppingList, new PrioritySorter());
    }

    // The following method displays the output in sorted order.
    @Override
    public void displayItem() {
        {
            System.out.println(shoppingList.size() + " items. ");
            for (ShoppingItem x : shoppingList) {
                System.out.println(x.toString());
                System.out.println("_________");
            }
        }
    }

    // Printing the end result of Shopping

    @Override
    public String calculateCost() {
        StringBuilder result = new StringBuilder();
        double moneyLeft = this.budget;
        if (moneyLeft < 0) {
            moneyLeft = 0;
        }
        for (ShoppingItem shoppingItem : shoppingList) {
            if (moneyLeft > shoppingItem.getTotalPrice()) {
                moneyLeft = moneyLeft - shoppingItem.getTotalPrice();
                result.append("Purchased Item: ");
                result.append(shoppingItem.getItemName());
                result.append("\n");
                result.append("The Money Left in Bank Account is:");
                result.append(moneyLeft);
                result.append("\n");
            }
            else {
                result.append("Sorry. Not Enough Balance in account\n");
                result.append(shoppingItem.getItemName());
                result.append(" was not purchased\n");
            }

        }
        return result.toString();
    }
}