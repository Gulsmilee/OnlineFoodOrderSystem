package com.oyes;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== OYES (Online Yemek Siparis Sistemi) BAŞLATILIYOR ===\n");

        // 1. Müşteri Oluşturma (Giriş Yapma)
        Customer c1 = new Customer("C-101", "Gülbahar", "gul@mail.com", "pass123", "Kadikoy, Istanbul", "555-9988");
        c1.login();
        
    }
}