package com.oyes;

/**
 * Menüdeki yemekleri temsil eder.
 * Orderable arayüzünü (interface) uygular.
 */
public class MenuItem implements Orderable {
    
    private String name;
    private String description;
    private double price;

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
//interface metodlari orderableden gelen arayüz
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    // toString: Menüde nasıl görüneceğini belirler
    @Override
    public String toString() {
        return String.format("%-20s %-10s %s", name, price + " TL", description);
    }
}