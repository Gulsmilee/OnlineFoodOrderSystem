package com.oyes;

public class Main {

    public static void main(String[] args) {

    	
    	System.out.println("--- SİSTEM TESTİ BAŞLIYOR ---\n");

        // 1. ADIM: User Testi
        System.out.println("1. User Testi:");
        User u1 = new User("001", "Ahmet Hoca", "ahmet@okul.edu", "1234");
        u1.login(); 
        System.out.println(u1.toString());
        
        System.out.println("\n--------------------------\n");

        // 2. ADIM: Customer (Müşteri) Testi
        System.out.println("2. Customer Testi:");
        Customer c1 = new Customer("002", "Gülbahar", "gul@mail.com", "pass987", "Kadikoy, Istanbul", "555-1234");
        c1.login();
        System.out.println("Müşteri: " + c1.getName() + " - Adres: " + c1.getAddress());

        System.out.println("\n--------------------------\n");

        // 3. ADIM: MenuItem (Yemek) Testi
        System.out.println("3. MenuItem Testi:");
        
        // Yeni bir yemek oluşturalım
        MenuItem m1 = new MenuItem("Lahmacun", "Bol acili, citir", 120.0);
        MenuItem m2 = new MenuItem("Ayran", "Yayik ayran", 30.0);
        
        // toString() metodunu test edelim
        System.out.println("Eklenen 1: " + m1.toString());
        System.out.println("Eklenen 2: " + m2.toString());
        
        // Interface metodlarını (getName, getPrice) test edelim
        System.out.println("Sorgu: " + m1.getName() + " fiyati ne kadar? -> " + m1.getPrice() + " TL");

        System.out.println("\n--- TEST BAŞARIYLA TAMAMLANDI ---");
    }
}	




