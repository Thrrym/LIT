package main.java.de.tuberlin.tkn.lit.model;

public class Collection extends Object {

    private String[] items;
    private int totalItems;
    private String[] first;
    private String[] last;

    public Collection(int totalItems, String[] first, String[] last, String[] current) {
        this.totalItems = totalItems;
        this.first = first;
        this.last = last;
        this.current = current;
    }

    public Collection(String[] items, int totalItems, String[] first, String[] last, String[] current) {
        this(totalItems,first,last,current);
        this.items = items;
    }


    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String[] getFirst() {
        return first;
    }

    public void setFirst(String[] first) {
        this.first = first;
    }

    public String[] getLast() {
        return last;
    }

    public void setLast(String[] last) {
        this.last = last;
    }

    public String[] getCurrent() {
        return current;
    }

    public void setCurrent(String[] current) {
        this.current = current;
    }

    private String[] current;
}