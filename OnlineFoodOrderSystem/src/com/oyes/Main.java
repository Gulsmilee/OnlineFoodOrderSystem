package com.oyes;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- SİSTEM TESTİ BAŞLIYOR ---\n");

        System.out.println("1. User Testi:");
        // Artık "1234" diye şifre de giriyoruz
        User u1 = new User("001", "Ahmet Hoca", "ahmet@okul.edu", "1234");
        u1.login(); 
        System.out.println(u1.toString()); 
        
        System.out.println("\n--------------------------\n");

        System.out.println("2. Customer (Müşteri) Testi:");
        // Müşteri için şifre: "pass987"
        Customer c1 = new Customer("002", "Gülbahar", "gul@mail.com", "pass987", "Kadikoy, Istanbul", "555-1234");
        
        c1.login();
        c1.viewMenu();
        System.out.println("Müşteri Adresi: " + c1.getAddress());

        System.out.println("\n--- TEST BAŞARIYLA TAMAMLANDI ---");
    }
}