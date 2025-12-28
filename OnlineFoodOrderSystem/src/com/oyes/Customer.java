package com.oyes;

public class Customer extends User {
    
    private String address;
    private String phoneNumber;

    public Customer() {
        super();
    }

    // Constructor güncellendi: Şifreyi de alıp User'a gönderiyor
    public Customer(String id, String name, String email, String password, String address, String phoneNumber) {
        super(id, name, email, password); 
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void viewMenu() {
        System.out.println("Menü listeleniyor...");
    }

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