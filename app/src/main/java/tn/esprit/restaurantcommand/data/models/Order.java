package tn.esprit.restaurantcommand.data.models;

import java.util.List;

public class Order {
    String _id;
    String userId;
    String bill;
    List<Item> items;


    public Order(String _id, String userId, String bill, List<Item> items) {
        this._id = _id;
        this.userId = userId;
        this.bill = bill;
        this.items = items;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", bill='" + bill + '\'' +
                ", items=" + items +
                '}';
    }
}
