package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShoppingItem implements Serializable {

    private String itemName;
    private double itemPrice;
    private int itemQty;
    private int itemPriority;
    private double totalPrice;

    public ShoppingItem() {
        itemName = "NA";
        itemPriority = 0;
        itemPrice = 0;
        itemQty = 0;
        totalPrice = 0;

    }

    public ShoppingItem(String itemName, int itemPriority, double itemPrice, int itemQty, double totalPrice)
    {
        this.itemName = itemName;
        this.itemPriority = itemPriority;
        this.itemPrice = itemPrice;
        this.itemQty = itemQty;
        this.totalPrice = totalPrice;

    }

    public String getItemName() {
        return itemName;
    }

    public int getItemPriority() {
        return itemPriority;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQty() {
        return itemQty;
    }

    public double getTotalPrice() {
        return totalPrice = itemPrice * itemQty;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }


    public void setItemPriority(int itemPriority) {
        this.itemPriority = itemPriority;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }


    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public String toString() {
        return "Item Name - " + itemName + "\n" + "Item Price - " + "$ " + itemPrice + "\n" + "Item Quantity - "
                + itemQty + "\n" + "Priority - " + itemPriority + "\n" + "Total Price - " + totalPrice;
    }


}