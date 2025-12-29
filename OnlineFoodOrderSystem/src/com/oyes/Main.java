package com.oyes;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- SİSTEM TESTİ BAŞLIYOR ---\n");

        // 1. ADIM: User Testi
        System.out.println("1. User Testi:");
        User u1 = new User("001", "Ahmet Hoca", "ahmet@okul.edu", "1234");
        u1.login(); 
        
        System.out.println("\n--------------------------\n");

        // 2. ADIM: Customer (Müşteri) Testi
        System.out.println("2. Customer Testi:");
        Customer c1 = new Customer("002", "Gülbahar", "gul@mail.com", "pass987", "Kadikoy, Istanbul", "555-1234");
        c1.login();

        System.out.println("\n--------------------------\n");

        // 3. ADIM: MenuItem (Yemek) Testi
        System.out.println("3. MenuItem Testi:");
        MenuItem m1 = new MenuItem("Adana Kebap", "Acili ve lezzetli", 220.0);
        MenuItem m2 = new MenuItem("Ayran", "Yayik ayran", 30.0);
        MenuItem m3 = new MenuItem("Kunefe", "Peynirli sicak tatli", 150.0);
        System.out.println("Yemekler hazirlandi.");

        System.out.println("\n--------------------------\n");

        // 4. ADIM: Restaurant Testi
        System.out.println("4. Restaurant Testi:");
        Restaurant myRestaurant = new Restaurant("Lezzet Dunyasi", 4.8, "Besiktas, Istanbul");
        myRestaurant.addMenuItem(m1);
        myRestaurant.addMenuItem(m2);
        myRestaurant.addMenuItem(m3);
        System.out.println("Restoran menusu hazir.");
        
        System.out.println("\n--------------------------\n");

        // 5. ADIM: Order (Sipariş) Testi (YENİ!)
        System.out.println("5. Sipariş Testi:");
        
        // Müşteri (c1), Restorandan (myRestaurant) sipariş veriyor
        Order order1 = new Order("SIP-001", c1, myRestaurant);
        
        System.out.println(">> Sepete ürün ekleniyor...");
        order1.addItem(m1); // 220 TL
        order1.addItem(m2); // 30 TL
        // Toplam 250 TL olmalı
        
        System.out.println("Sipariş Durumu: " + order1.getStatus());
        System.out.println("Müşteri: " + order1.getCustomer().getName());
        
        // HESAPLAMA KONTROLÜ
        double total = order1.calculateTotal();
        System.out.println("TOPLAM TUTAR: " + total + " TL");

        System.out.println("\n--- TEST BAŞARIYLA TAMAMLANDI ---");
    }
}