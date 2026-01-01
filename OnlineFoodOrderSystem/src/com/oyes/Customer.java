package com.oyes;

/**
 * Müşteri sınıfı.
 * User sınıfından kalıtım (Inheritance) alır.
 * Ekstra olarak adres ve telefon bilgisi tutar.
 */
public class Customer extends User {
    
    private String address;
    private String phoneNumber;

    public Customer() {
        super();
    }

    /**
     * Müşteri oluşturucu metodu.
     * User sınıfının constructor'ını (super) çağırır.
     */
    public Customer(String id, String name, String email, String password, double balance, String address, String phoneNumber) {
        super(id, name, email, password, balance); 
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return super.getName() + " (Bakiye: " + super.getBalance() + " TL) - " + address;
    }
}