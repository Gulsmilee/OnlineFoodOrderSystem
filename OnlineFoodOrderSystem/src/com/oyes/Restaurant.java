package com.oyes;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    
    private String name;
    private double rating;
    private String address;
    
    // Composition: Restoran, içinde MenuItem listesi barındırır.
    private List<MenuItem> menu;

    public Restaurant() {
        // Listeyi başlatmazsak "Null" hatası alırız, o yüzden boş liste oluşturuyoruz.
        this.menu = new ArrayList<>();
    }

    public Restaurant(String name, double rating, String address) {
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.menu = new ArrayList<>(); // Listeyi burada da başlatıyoruz
    }

    // --- Menü Yönetim Metodları ---

    // Menüye yeni yemek ekleme
    public void addMenuItem(MenuItem item) {
        this.menu.add(item);
        // Eklendiğine dair bilgi verelim (Opsiyonel)
        // System.out.println(item.getName() + " menüye eklendi.");
    }

    // Menüden yemek silme
    public void removeMenuItem(MenuItem item) {
        this.menu.remove(item);
    }

    // Menüyü getirme
    public List<MenuItem> getMenu() {
        return menu;
    }
    
    // --- Getter ve Setterlar ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}