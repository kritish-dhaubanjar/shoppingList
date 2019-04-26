package service;

import model.ShoppingItem;
import service.AbstractShopping;

import java.util.Comparator;

// Sorts the priority in ascending order.

abstract public class PrioritySort extends AbstractShopping {

    public class PrioritySorter implements Comparator<ShoppingItem> {

        @Override
        public int compare(ShoppingItem priority1, ShoppingItem priority2) {
            return Integer.compare(priority1.getItemPriority(), priority2.getItemPriority());
        }
    }
}
