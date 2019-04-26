package service;

import java.io.FileNotFoundException;
import java.io.IOException;

abstract public class AbstractShopping {

    abstract public void addItem() throws FileNotFoundException, IOException, ClassNotFoundException;

    // Sorts all the item according to Priority.
    abstract public void sortPriority();

    // Display list and total number of items.
    abstract public void displayItem();

    abstract public String calculateCost();


}