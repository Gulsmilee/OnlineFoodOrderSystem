package com.oyes;

public class MenuItem implements Orderable {
    
    private String name;
    private String description;
    private double price;

    public MenuItem() {
    }

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return name + " (" + price + " TL) - " + description;
    }
}