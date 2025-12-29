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
        System.out.println("Yemek oluşturuldu: " + m1.getName());

        System.out.println("\n--------------------------\n");

        // 4. ADIM: Restaurant Testi (YENİ!)
        System.out.println("4. Restaurant Testi:");
        
        // Restoranı oluşturuyoruz
        Restaurant myRestaurant = new Restaurant("Lezzet Dunyasi", 4.8, "Besiktas, Istanbul");
        System.out.println("Restoran Acildi: " + myRestaurant.getName());
        
        // Yemekleri menüye ekliyoruz
        System.out.println(">> Yemekler menüye ekleniyor...");
        myRestaurant.addMenuItem(m1); // Kebap
        myRestaurant.addMenuItem(m2); // Ayran
        myRestaurant.addMenuItem(m3); // Künefe
        
        // Menüyü ekrana basalım
        System.out.println("\n--- GÜNCEL MENÜ ---");
        for (MenuItem item : myRestaurant.getMenu()) {
            System.out.println("- " + item.toString());
        }

        System.out.println("\n--- TEST BAŞARIYLA TAMAMLANDI ---");
    }
}