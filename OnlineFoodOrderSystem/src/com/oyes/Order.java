package com.oyes;

import java.util.ArrayList;
import java.util.List;

/**
 * Sipariş Yönetim Sınıfı.
 * Sepeti yönetir, toplam tutarı hesaplar ve ödemeyi işler.
 */
public class Order {
    
    private Customer customer;
    private List<MenuItem> items;
    private PaymentMethod paymentMethod;

    public Order(Customer customer) {
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(MenuItem item) {
        items.add(item);
        System.out.println(item.getName() + " sepete eklendi.");
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Siparişi tamamlar.
     * Eğer kullanıcıda bakiye varsa düşer veya karttan çeker.
     */
    public void completeOrder() {
        double total = calculateTotal();
        
        if (paymentMethod != null) {
            // Ödemeyi yapmayı dene
            boolean success = paymentMethod.pay(total);
            
            if (success) {
                // Eğer nakit değil de bakiye kullanılıyorsa, burada düşülebilir.
                System.out.println(">> Sipariş Başarıyla Tamamlandı!");
                printReceipt();
            }
        } else {
            System.out.println("Ödeme yöntemi seçilmedi!");
        }
    }

    public void printReceipt() {
        System.out.println("\n--- SİPARİŞ FİŞİ ---");
        System.out.println("Müşteri: " + customer.getName());
        for (MenuItem item : items) {
            System.out.println("* " + item.getName() + "\t" + item.getPrice() + " TL");
        }
        System.out.println("TOPLAM: " + calculateTotal() + " TL");
        System.out.println("--------------------\n");
    }
}