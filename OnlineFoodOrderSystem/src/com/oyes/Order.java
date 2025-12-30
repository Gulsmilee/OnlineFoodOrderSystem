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
    private String status; 
    
    // Polimorfizm: Ödeme yöntemi arayüz tipinde tutulur
    private PaymentMethod paymentMethod;

    public Order(String id, Customer customer, Restaurant restaurant) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.status = "Siparis Olusturuldu";
    }

    // Siparişe yemek ekleme
    public void addItem(MenuItem item) {
        items.add(item);
        System.out.println(item.getName() + " sepete eklendi.");
    }

    // Toplam tutarı hesaplama
    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Ödeme yöntemini belirleme (Kart mı Nakit mi?)
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Siparişi tamamlama işlemi
    public void completeOrder() {
        double total = calculateTotal();
        
      
        if (paymentMethod != null) {
            boolean success = paymentMethod.pay(total); // Polimorfizm burada çalışır
            
            // Eğer ödeme başarılıysa durumu güncelle
            if (success) {
                this.status = "Odendi ve Hazirlaniyor";
                System.out.println("Sipariş başarıyla tamamlandı! Durum: " + this.status);
            }
        } else {
            // Bu else artık if bloğunun hemen bitiminde ve metodun içinde
            System.out.println("HATA: Lütfen bir ödeme yöntemi seçiniz!!");
        }
    } // Metod burada bitiyor

    // Getter Metodları
    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<MenuItem> getItems() { return items; }
    public String getStatus() { return status; }
    
    // Manuel durum değiştirme fonksiyonu
    // public void setStatus(String status) {
    //    this.status = status;
    // }
}