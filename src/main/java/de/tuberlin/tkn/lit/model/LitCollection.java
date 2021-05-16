package de.tuberlin.tkn.lit.model;

public class LitCollection extends LitObject {

    private LitObject[] items;
    private int totalItems;
    private LitObject[] first;
    private LitObject[] last;

    public LitCollection(int totalItems, LitObject[] first, LitObject[] last, LitObject[] current) {
        this.totalItems = totalItems;
        this.first = first;
        this.last = last;
        this.current = current;
    }

    public LitCollection(LitObject[] items, int totalItems, LitObject[] first, LitObject[] last, LitObject[] current) {
        this(totalItems,first,last,current);
        this.items = items;
    }


    public LitObject[] getItems() {
        return items;
    }

    public void setItems(LitObject[] items) {
        this.items = items;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public LitObject[] getFirst() {
        return first;
    }

    public void setFirst(LitObject[] first) {
        this.first = first;
    }

    public LitObject[] getLast() {
        return last;
    }

    public void setLast(LitObject[] last) {
        this.last = last;
    }

    public LitObject[] getCurrent() {
        return current;
    }

    public void setCurrent(LitObject[] current) {
        this.current = current;
    }

    private LitObject[] current;
}