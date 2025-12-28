package com.oyes;

/**
 * Kullanıcıların ortak özelliklerini tutan temel sınıf.
 * Kapsülleme (Encapsulation) prensibine uygun olarak tasarlanmıştır.
 */
public class User {
    
    // Encapsulation: Değişkenler private (gizli) tutulur
    private String id;
    private String name;
    private String email;
    
    // Parametresiz Constructor (Gerektiğinde hata almamak için)
    public User() {
    }

    // Parametreli Constructor
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter ve Setter Metodları
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Sisteme giriş simülasyonu
    public boolean login() {
        System.out.println(this.name + " sisteme giris yapti.");
        return true;
    }
    
    // Kullanıcı bilgilerini yazdırmak için toString override ediyoruz
    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
    }
}