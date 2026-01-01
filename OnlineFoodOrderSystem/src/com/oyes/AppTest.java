package com.oyes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Test Sınıfı.
 * İş mantığının (Business Logic) doğru çalışıp çalışmadığını kontrol eder.
 * 
 */
class AppTest {

    private User testUser;
    private Order testOrder;
    private Restaurant testRestaurant;

    // Her testten önce çalışıp ortamı sıfırlar
    @BeforeEach
    void setUp() {
        testUser = new Customer("999", "Test User", "test@mail.com", "pass", 100.0, "Adres", "Tel");
        testRestaurant = new Restaurant("Test Restoran");
        testOrder = new Order((Customer) testUser);
    }

    // TEST 1: Sepet Toplamı Doğru Hesaplanıyor mu?
    @Test
    void testCalculateTotal() {
        MenuItem item1 = new MenuItem("Yemek 1", "Desc", 50.0);
        MenuItem item2 = new MenuItem("Yemek 2", "Desc", 25.5);
        
        testOrder.addItem(item1);
        testOrder.addItem(item2);
        
        // Beklenen: 75.5, Hesaplanan: order.calculateTotal()
        assertEquals(75.5, testOrder.calculateTotal(), "Sepet toplamı yanlış hesaplandı!");
    }

    // TEST 2: Bakiye Yetersizse Düşüş Yapılmamalı
    @Test
    void testInsufficientBalance() {
        // Kullanıcının 100 TL'si var
        boolean result = testUser.deductBalance(200.0); // 200 TL çekmeye çalış
        
        assertFalse(result, "Yetersiz bakiye olmasına rağmen işlem onaylandı!");
        assertEquals(100.0, testUser.getBalance(), "Bakiye değişmemeliydi!");
    }

    // TEST 3: Bakiye Yeterliyse Düşüş Yapılmalı
    @Test
    void testSufficientBalance() {
        // Kullanıcının 100 TL'si var
        boolean result = testUser.deductBalance(40.0); // 40 TL çek
        
        assertTrue(result, "Yeterli bakiye varken işlem reddedildi!");
        assertEquals(60.0, testUser.getBalance(), "Kalan bakiye yanlış!");
    }

    // TEST 4: Luhn Algoritması (Kredi Kartı) Testi
    // Not: Bu testi yapabilmek için CreditCardPayment sınıfındaki 'isValidLuhn' metodunu
    // 'private' yerine 'public' yapman veya dolaylı test etmen gerekir.
    // Biz burada dolaylı yoldan, geçerli ve geçersiz kart deneyerek test ediyoruz.
 // // TEST 4: Luhn Algoritması (Kredi Kartı) Testi
    @Test
    void testLuhnAlgorithm() {
        // 1. Matematiksel olarak GERÇEKTEN GEÇERLİ bir kart (Sonu 2)
        // Hesap: 4 -> 8, Son digit 2 -> 2. Toplam=10. 10%10==0.
        CreditCardPayment validCard = new CreditCardPayment("4000000000000002", "123");
        
        // Bu kartın kabul edilmesi lazım (assertTrue)
        assertTrue(validCard.pay(10.0), "Geçerli kart reddedildi!");

        // 2. Matematiksel olarak GERÇEKTEN GEÇERSİZ bir kart (Sonu 1)
        // Hesap: 4 -> 8, Son digit 1 -> 1. Toplam=9. 9%10!=0.
        CreditCardPayment invalidCard = new CreditCardPayment("4000000000000001", "123");
        
        // Bu kartın reddedilmesi lazım (assertFalse)
        assertFalse(invalidCard.pay(10.0), "Geçersiz kart kabul edildi!");
    }
}