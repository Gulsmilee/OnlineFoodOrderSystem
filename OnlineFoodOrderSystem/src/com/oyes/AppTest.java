package com.oyes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Test Sınıfı.
 * İş mantığının (Business Logic) doğru çalışıp çalışmadığını kontrol eder.
 */
class AppTest {

    private User testUser;
    private Order testOrder;
    private Restaurant testRestaurant;

    // Her testten önce çalışıp ortamı sıfırlar (Temiz başlangıç)
    @BeforeEach
    void setUp() {
        // Test kullanıcısı oluşturuyoruz
        testUser = new Customer("999", "Test User", "test@mail.com", "pass", 100.0, "Adres", "Tel");
        testRestaurant = new Restaurant("Test Restoran");
        testOrder = new Order((Customer) testUser);
    }

    // TEST 1: Sepet Toplamı Doğru Hesaplanıyor mu?
    @Test
    void testCalculateTotal() {
        // Test için geçici ürünler oluştur
        MenuItem item1 = new MenuItem("Yemek 1", "Desc", 50.0);
        MenuItem item2 = new MenuItem("Yemek 2", "Desc", 25.5);
        
        testOrder.addItem(item1);
        testOrder.addItem(item2);
        
        // Beklenen: 75.5 TL
        // calculateTotal() metodu double döndürdüğü için ondalıklı kontrol ediyoruz.
        assertEquals(75.5, testOrder.calculateTotal(), "Sepet toplamı yanlış hesaplandı!");
    }

    // TEST 2: Bakiye Yetersizse Düşüş Yapılmamalı (Güvenlik Testi)
    @Test
    void testInsufficientBalance() {
        // Kullanıcının 100 TL'si var (setUp metodunda verdik)
        boolean result = testUser.deductBalance(200.0); // 200 TL çekmeye çalış
        
        // İşlem reddedilmeli (false dönmeli)
        assertFalse(result, "Yetersiz bakiye olmasına rağmen işlem onaylandı!");
        // Para azalmamalı, hala 100 TL kalmalı
        assertEquals(100.0, testUser.getBalance(), "Bakiye değişmemeliydi!");
    }

    // TEST 3: Bakiye Yeterliyse Düşüş Yapılmalı
    @Test
    void testSufficientBalance() {
        // Kullanıcının 100 TL'si var
        boolean result = testUser.deductBalance(40.0); // 40 TL çek
        
        // İşlem onaylanmalı
        assertTrue(result, "Yeterli bakiye varken işlem reddedildi!");
        // 100 - 40 = 60 TL kalmalı
        assertEquals(60.0, testUser.getBalance(), "Kalan bakiye yanlış!");
    }

    // TEST 4: Luhn Algoritması (Kredi Kartı) Testi
    // --- DÜZELTİLEN KISIM BURASI ---
    @Test
    void testLuhnAlgorithm() {
        // NOT: CreditCardPayment sınıfı artık 3 parametre (No, CVV, Tarih) istiyor.
        // Tarih formatı "AA/YY" şeklinde ve 5 karakter olmalı.
        
        // 1. Matematiksel olarak GEÇERLİ bir kart numarası (Sonu 2 ile biten örnek)
        CreditCardPayment validCard = new CreditCardPayment("4000000000000002", "123", "12/28");
        
        // Bu kartın kabul edilmesi lazım (Tarih ve CVV de doğru formatta)
        assertTrue(validCard.pay(10.0), "Geçerli kart reddedildi!");

        // 2. Matematiksel olarak GEÇERSİZ bir kart numarası (Sonu 1 ile biten örnek)
        CreditCardPayment invalidCard = new CreditCardPayment("4000000000000001", "123", "12/28");
        
        // Bu kartın reddedilmesi lazım (Luhn algoritmasına uymuyor)
        assertFalse(invalidCard.pay(10.0), "Geçersiz kart kabul edildi!");
    }
    
    // TEST 5: Ürün Çıkarma ve Tutar Güncelleme Testi
    @Test
    void testRemoveItemAndTotal() {
        // 1. İki tane ürün oluşturalım
        MenuItem burger = new MenuItem("Burger", "Leziz", 150.0);
        MenuItem kola = new MenuItem("Kola", "Soğuk", 30.0);
        
        // 2. Sepete ekleyelim (Toplam: 180 TL olmalı)
        testOrder.addItem(burger);
        testOrder.addItem(kola);
        
        // Ara kontrol: Eklendi mi?
        assertEquals(180.0, testOrder.calculateTotal(), "Ekleme sonrası tutar yanlış!");
        
        // 3. Burgeri sepetten SİLELİM
        testOrder.removeItem(burger);
        
        // 4. Beklenen Sonuç: Sadece Kola kalmalı (30.0 TL)
        assertEquals(30.0, testOrder.calculateTotal(), "Ürün silme sonrası tutar güncellenmedi!");
    }
}