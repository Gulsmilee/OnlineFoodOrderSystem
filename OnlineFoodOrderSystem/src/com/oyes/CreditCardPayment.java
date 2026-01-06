package com.oyes;

/**
 * Kredi Kartı ile ödeme sınıfı.
 * Luhn algoritması ile kart numarasının geçerliliğini kontrol eder.
 */
public class CreditCardPayment implements PaymentMethod {
    
    private String cardNumber;
    private String cvv;
    private String expiryDate;

    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate=expiryDate;
    }

    @Override
    public boolean pay(double amount) {
        // Önce kart numarasını doğrula
        if(cvv == null || cvv.length() !=3) {
        	System.out.println("Hata:Geçersiz CVV kodu!");
        	return false;
        }
        if(expiryDate == null || expiryDate.length() !=5) {
        	System.out.println("Hata:Geçersiz son kullanma tarihi(Örn:12/25 olmalı)!");
        	return false;
        }
        if (isValidLuhn(cardNumber)) {
        	System.out.println("Kredi kartı onaylandı:" + cardNumber.substring(0,4)+ "****");
        	System.out.println("Ödeme başarıyla alındı:" +amount + "TL");
        	return true;
        }else {
        	System.out.println("Hata:Geçersiz kart numarası!!");
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
        
        for (int i = nDigits - 1; i >= 0; i--) { //ters dongu
            int d = cardNo.charAt(i) - '0'; // Karakteri sayıya çevir
    
            if (isSecond == true) // her ikinci sayiyi ciftle
                d = d * 2;
    
            // Sayı iki basamaklıysa basamakları topla (örn: 12 -> 1+2=3)
            nSum += d / 10; //onlar basamagi
            nSum += d % 10;//birler basamagi
    
            isSecond = !isSecond; //bayragi tersine çevirir(bir sonkaki tur icin
        }
        return (nSum % 10 == 0); //toplam 10 a bolunuyormu
    }
}