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

    //polimorfizm yaptık bunu arayüz (interface) şeklinde tuttum tutulması gerekiyor
    private PaymentMethod paymentMethod;
    
    
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
    
    //ödeme yöntemini belirme kısmı burada oluyor(kart mı nakit mi)
    public void setPaymentMeethod(PaymentMethod paymentMethod) {
    	this.paymentMethod=paymentMethod;
    	
    }
    
    //sipariş tamamlama işlemi
    public void completeOrder() {
    	double total = calculateTotal();
    	
    	if (paymentMethod !=null) {
    		boolean success = paymentMethod.pay(total);//polimorfizm çalıştıgı yer
    		this.status="Odendi ve Hazirlaniyor";
    		System.out.println("Sipariş başarıyla tamamlandı! Durum:"+this.status);
    	}
    } else {
    	System.out.println("HATA:Lütfen bir ödeme yöntemi seçiniz!!");
    }



    // Getter ve Setterlar bölümü
    public String getId() {
    	return id;
    	}
    public Customer getCustomer() {
    	return customer;
    	}
    public Restaurant getRestaurant() {
    	return restaurant;
    	}
    public List<MenuItem> getItems() {
    	return items;
    	}
    public String getStatus() {
    	return status;
    	}
    //elle ödeme yöntemini denemek için kullanacağım set fonksiyonu
  //  public void setStatus(String status) {
  //  this.status = status;
  //  }
}