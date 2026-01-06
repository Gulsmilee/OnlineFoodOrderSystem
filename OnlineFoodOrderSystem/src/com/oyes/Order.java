package com.oyes;

import java.util.ArrayList;
import java.util.List;

/**
 * Order (Sipariş) Sınıfı.
 * Bir müşterinin oluşturduğu sepeti ve ödeme işlemlerini yönetir.
 */
public class Order {
    private Customer customer;       // Siparişi veren müşteri
    private List<MenuItem> items;    // Sepetteki ürünlerin listesi
    private PaymentMethod paymentMethod; // Seçilen ödeme yöntemi (Polimorfizm)

    // Kurucu Metot: Sipariş oluşturulurken müşteri zorunludur
    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>(); // Sepet boş olarak başlar
    }

    // Ürün Ekleme: Menüden seçilen ürünü listeye ekler
    public void addItem(MenuItem item) {
        items.add(item);
        System.out.println(item.getName() + " sepete eklendi.");
    }

    // ---  Ürün Çıkarma Metodu ---
    // Listeden belirli bir ürünü siler.
    // "remove" metodu, listede o ürünü bulursa siler ve true döner.
    public void removeItem(MenuItem item) {
        boolean removed = items.remove(item);
        if(removed) {
            System.out.println(item.getName() + " sepetten ÇIKARILDI.");
        } else {
            System.out.println("Bu ürün zaten sepetinizde yok.");
        }
    }
    
    // ---  Sipariş Detaylarını Metin Olarak Verir ---
    // Bu metod, siparişi "orders.csv" dosyasına kaydederken kullanılır.
    // Örnek Çıktı: "Lahmacun + Ayran + Sufle"
    public String getOrderDetails() {
        StringBuilder sb = new StringBuilder(); // String birleştirme için performanslı yapı
        for(MenuItem item : items) {
            sb.append(item.getName()).append(" + ");
        }
        // Eğer sepet boş değilse, sondaki fazladan " + " işaretini sileriz
        if (sb.length() > 0) sb.setLength(sb.length() - 3);
        
        return sb.toString();
    }

    // Sepetteki tüm ürünlerin fiyatlarını toplar
    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // Ödeme yöntemini ayarlar (Nakit, Kredi Kartı vb.)
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Siparişi tamamlar ve ödemeyi gerçekleştirir
    public boolean completeOrder() {
        double total = calculateTotal();
        
        // Ödeme yöntemi seçilmemişse hata ver
        if (this.paymentMethod == null) {
            System.out.println("Hata: Ödeme yöntemi seçilmedi!");
            return false;
        }

        // paymentMethod.pay() metodunun sonucunu al (True/False)(interface üzerinden ödeme yapilir)
        boolean isSuccess = this.paymentMethod.pay(total);

        if (isSuccess) {
            System.out.println("Sipariş işlemleri tamamlandı.");
            return true; // Main sınıfına "BAŞARILI" bilgisini gönder
        } else {
            
            return false; // Main sınıfına "BAŞARISIZ" bilgisini gönder
        }
    }
    // Ekrana fiş yazdırır (Sadece bilgi amaçlı)
    public void printReceipt() {
    	System.out.println("--------------------");
        System.out.println("\n--- SİPARİŞ FİŞİ ---");
        System.out.println("Müşteri: " + customer.getName());
        for (MenuItem item : items) {
            System.out.println("- " + item.getName() + ": " + item.getPrice() + " TL");
        }
        System.out.println("TOPLAM: " + calculateTotal() + " TL");
        System.out.println("--------------------");
        System.out.println("--------------------");
    }
}