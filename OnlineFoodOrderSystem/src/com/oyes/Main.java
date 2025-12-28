package com.oyes;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- SİSTEM TESTİ BAŞLIYOR ---\n");

        // 1. ADIM: Basit bir User (Kullanıcı) oluşturup test edelim
        System.out.println("1. User Testi:");
        User u1 = new User("001", "Ahmet Hoca", "ahmet@gmail.com");
        u1.login(); // User sınıfındaki metodu çağırdık
        System.out.println(u1.toString()); 
        
        System.out.println("\n--------------------------\n");

        // 2. ADIM: Bir Customer (Müşteri) oluşturup kalıtımı test edelim
        System.out.println("2. Customer (Müşteri) Testi:");
        // Dikkat: Customer hem User özelliklerini (id, isim) hem kendi özelliklerini (adres) alır
        Customer c1 = new Customer("002", "Gülbahar", "gul@mail.com", "Kadikoy, Istanbul", "555-1234");
        
        c1.login();      // User'dan miras aldığı metot (ÇALIŞMALI)
        c1.viewMenu();   // Customer'ın kendi metodu (ÇALIŞMALI)
        System.out.println("Müşteri Adresi: " + c1.getAddress());

        System.out.println("\n--- TEST BAŞARIYLA TAMAMLANDI ---");
    }
}