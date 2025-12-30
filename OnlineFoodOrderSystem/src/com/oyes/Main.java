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
        
        System.out.println("\n------------------------------------------------\n");
        
        //3. Senaryo 1: Kredi kartı ile sipariş verimi
        System.out.println(">>>Kredi kartı ile sipariş<<<");
        
        Order order1 = new Order("SIPARIS-001", c1, myRestaurant);
        order1.addItem(m1);
        order1.addItem(m2);
        order1.addItem(m3);
        
        System.out.println("Toplam TUTAR:" + order1.calculateTotal()+"TL");
        
        //Polimorfizm: PaymentMethod tipinde CreditCardPayment oluşturuyoruz
        PaymentMethod creditCard = new CreditCardPayment("1234-5678-9012-3456","401");
        order1.setPaymentMethod(creditCard);
        
        order1.completeOrder();//ödemeyi yap ve bitir
        
        System.out.println("\n------------------------------------------------\n");
        
        //4. SEnaryo2: Nakit ile sipariş
        System.out.println(">>>Senaryo2: Nakit ile Sipariş<<<");
        Order order2= new Order("SIPARIS-002",c1,myRestaurant);
        order2.addItem(m2);
        order2.addItem(m3);
        
        System.out.println("Toplam Tutar:"+ order2.calculateTotal()+"TL");
        
        //Polimorfizm:PaymentMethod tipinde CashPayment oluşturdum
        PaymentMethod cash = new CashPayment();
        order2.setPaymentMethod(cash);
        
        order2.completeOrder();//ödemeyi yap ve bitir
        
        
        
    }
    
}