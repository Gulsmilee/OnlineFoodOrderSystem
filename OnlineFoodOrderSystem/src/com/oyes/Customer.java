package com.oyes;

/**
 * Müşteri sınıfı. User sınıfından türer (Inheritance).
 * Ek olarak adres ve telefon bilgilerini tutar.
 */
public class Customer extends User {
    
    // Kapsülleme: Müşteriye özel gizli değişkenler
    private String address;
    private String phoneNumber;

    // Parametresiz Constructor
    public Customer() {
        super();
    }

    /**
     * Parametreli Constructor
     * super() anahtar kelimesi ile User sınıfının kurucusunu çağırır.
     */
    public Customer(String id, String name, String email, String address, String phoneNumber) {
        super(id, name, email); // User sınıfının özelliklerini set et
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getter ve Setter Metodları
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Menü görüntüleme (Şimdilik sadece mesaj)
    public void viewMenu() {
        System.out.println("Menü listeleniyor...");
    }

    // Sipariş verme işlemi (Taslak)
    public void placeOrder() {
        System.out.println(getName() + " sipariş verdi.");
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + getName() + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phoneNumber + '\'' +
                '}';
    }
}