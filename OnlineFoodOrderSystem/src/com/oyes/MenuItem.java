package com.oyes;

/**
 * Menüdeki yemekleri temsil eder.
 * Orderable arayüzünü (interface) uygular.
 */
public class MenuItem implements Orderable {
    
    private String name;        // Yemek adı
    private String description; // Açıklama (Örn: "Acılı, bol soğanlı")
    private double price;       // Fiyat

    public MenuItem() {
    }

    public MenuItem(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // --- Orderable Interface'inden gelen zorunlu metodlar ---
    // Bu metodlar olmak ZORUNDA 
    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
    
    
    // Getter ve Setter Metodları
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (" + price + " TL) - " + description;
    }
}