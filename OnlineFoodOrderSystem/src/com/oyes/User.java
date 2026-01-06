package com.oyes;

/**
 * Kullanıcı sınıfı (Base Class).
 * hem verileri sakladığımız hemde para işlemlerini yönettiğimiz class
 * Sistemdeki tüm kullanıcıların temel özelliklerini taşır.
 * Bakiye (Balance) yönetimi buraya eklenmiştir.
 */
public class User {
    
    private String id;
    private String name;
    private String email;
    private String password;
    private double balance; // Cüzdan Bakiyesi

    // Boş Constructor (Nesne oluşturma kolaylığı için)
    public User() {
    }

    /**
     * Dolu Constructor
     *  balance Başlangıç bakiyesi
     */
    public User(String id, String name, String email, String password, double balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    // --- Bakiye Yönetim Metodları ---

    /**
     * Hesaba para yükler.
     *  amount Yüklenecek miktar
     */
    public void addBalance(double amount) {
        this.balance += amount;
        System.out.println(">> Hesaba " + amount + " TL yüklendi. Yeni Bakiye: " + this.balance + " TL");
    }
    
    /**
     * Hesaptan para düşer.
     *  amount Düşülecek miktar
     * @return Yeterli bakiye varsa true, yoksa false döner.
     */
    public boolean deductBalance(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        } else {
            System.out.println(">> HATA: Yetersiz Bakiye! Mevcut paranız: " + this.balance + " TL");
            return false;
        }
    }

    // --- Getter ve Setter Metodları ---
    // get private değerin değişkenini  okur set değeri değiştirmemizi sağlar
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    
    @Override
    public String toString() {
        return "Kullanıcı: " + name + " | Bakiye: " + balance + " TL";
    }
}