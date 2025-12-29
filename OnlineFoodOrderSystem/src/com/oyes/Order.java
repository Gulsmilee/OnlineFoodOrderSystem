package com.oyes;

import java.util.ArrayList;
import java.util.List;

/**
 * Sipariş sınıfı.
 * Müşteri, Restoran ve Yemekleri bir araya getirildi.
 */
public class Order {
    
    private String id;
    private Customer customer;
    private Restaurant restaurant;
    private List<MenuItem> items;
    private String status; // Örn: "Hazırlanıyor", "Teslim Edildi"

    public Order(String id, Customer customer, Restaurant restaurant) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.status = "Siparis Olusturuldu";
    }

    // Siparişe yemek ekleme bölümü
    public void addItem(MenuItem item) {
        items.add(item);
        System.out.println(item.getName() + " sepete eklendi.");
    }

    // Toplam tutarı hesaplama kısmı
    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Getter ve Setterlar bölümü
    public String getId() { return id; }
    
    public Customer getCustomer() { return customer; }
    
    public Restaurant getRestaurant() { return restaurant; }
    
    public List<MenuItem> getItems() { return items; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}