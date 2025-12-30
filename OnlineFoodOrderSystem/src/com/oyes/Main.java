package com.oyes;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== OYES (Online Yemek Siparis Sistemi) BAŞLATILIYOR ===\n");

        // 1. Müşteri Oluşturma (Giriş Yapma)
        Customer c1 = new Customer("C-101", "Gülbahar", "gul@mail.com", "pass123", "Kadikoy, Istanbul", "555-9988");
        c1.login();
        
        System.out.println("\n------------------------------------------------\n");
        //2. Restaurant ve Menü oluşturma
        Restaurant myRestaurant = new Restaurant("Lezzet Durağı",4.9,"Besiktas,Istanbul");
        
        MenuItem m1 = new MenuItem("Adana kebap","Acılı,kozlenmiş biberli",250.0);
        MenuItem m2 = new MenuItem("Ayran", "Bol köpüklü",30.0);
        MenuItem m3 = new MenuItem("Sufle","Bol çikolatalı",150.0);
        
        myRestaurant.addMenuItem(m1);
        myRestaurant.addMenuItem(m2);
        myRestaurant.addMenuItem(m3);
        
        System.out.println("Restauran menüsü hazır :))");
    }
    
}