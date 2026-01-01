package com.oyes;

/**
 * Kredi Kartı ile ödeme sınıfı.
 * Luhn algoritması ile kart numarasının geçerliliğini kontrol eder.
 */
public class CreditCardPayment implements PaymentMethod {
    
    private String cardNumber;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        // Önce kart numarasını doğrula
        if (isValidLuhn(cardNumber)) {
            System.out.println("Kart doğrulandı. " + amount + " TL çekiliyor...");
            return true;
        } else {
            System.out.println("HATA: Geçersiz Kart Numarası!");
            return false;
        }
    }

    /**
     * LUHN ALGORİTMASI
     * Kredi kartı numarasının matematiksel olarak geçerli olup olmadığını kontrol eder.
     */
    private boolean isValidLuhn(String cardNo) {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;
        
        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNo.charAt(i) - '0'; // Karakteri sayıya çevir
    
            if (isSecond == true)
                d = d * 2;
    
            // Sayı iki basamaklıysa basamakları topla (örn: 12 -> 1+2=3)
            nSum += d / 10;
            nSum += d % 10;
    
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}