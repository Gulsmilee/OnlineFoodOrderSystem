package com.oyes;

import java.util.List;
import java.util.Scanner;

public class Main {

    static List<User> users;
    static Restaurant restaurant;
    static Scanner scanner = new Scanner(System.in);
    static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("=== OYES SİSTEMİ BAŞLATILIYOR ===");
        
        // Verileri yükle
        users = FileHelper.loadUsers("users.csv");
        List<MenuItem> menuItems = FileHelper.loadMenu("menu.csv");
        
        restaurant = new Restaurant("Lezzet Dünyası");
        for (MenuItem item : menuItems) {
            restaurant.addMenuItem(item);
        }

        // --- GİRİŞ / KAYIT DÖNGÜSÜ ---
        boolean loginSuccess = false;
        while (!loginSuccess) {
            System.out.println("\n--- HOŞGELDİNİZ ---");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol (Yeni Kullanıcı)");
            System.out.print("Seçiminiz: ");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sayı giriniz!");
                continue;
            }

            if (choice == 1) {
                // Giriş İşlemi
                System.out.print("E-posta: ");
                String email = scanner.nextLine();
                System.out.print("Şifre: ");
                String pass = scanner.nextLine();
                
                loggedInUser = login(email, pass);
                if (loggedInUser != null) {
                    loginSuccess = true;
                    System.out.println("\n>>> Başarıyla giriş yapıldı. Hoşgeldin, " + loggedInUser.getName());
                } else {
                    System.out.println("!!! Hatalı E-posta veya Şifre. Tekrar deneyin veya kayıt olun.");
                }
            } 
            else if (choice == 2) {
                // Kayıt İşlemi
                register();
            } 
            else {
                System.out.println("Geçersiz seçim!");
            }
        }

        // --- ANA MENÜ (SİMÜLASYON) ---
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ANA MENÜ ---");
            System.out.println("1. Menüyü Gör");
            System.out.println("2. Bakiye Sorgula / Yükle");
            System.out.println("3. Sipariş Ver");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");
            
            int mainChoice = -1;
            try {
                // nextInt yerine parse kullanarak buffer hatasını önlüyoruz
                String input = scanner.nextLine();
                mainChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                mainChoice = -1;
            }

            switch (mainChoice) {
                case 1:
                    showMenu();
                    break;
                case 2:
                    balanceOperations();
                    break;
                case 3:
                    placeOrder();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Çıkış yapılıyor... İyi günler!");
                    break;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    // --- METODLAR ---

    public static User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // YENİ: Kayıt Olma Metodu
    public static void register() {
        System.out.println("\n--- YENİ KULLANICI KAYDI ---");
        System.out.print("Adınız Soyadınız: ");
        String name = scanner.nextLine();
        
        System.out.print("E-posta Adresiniz: ");
        String email = scanner.nextLine();
        
        // E-posta kontrolü (Aynı mail var mı?)
        for(User u : users) {
            if(u.getEmail().equals(email)) {
                System.out.println("Bu e-posta zaten kayıtlı!");
                return;
            }
        }

        System.out.print("Şifreniz: ");
        String pass = scanner.nextLine();

        // Otomatik ID oluşturma (Listenin boyutu + 1)
        String newId = String.valueOf(users.size() + 1);
        double startBalance = 0.0; // Yeni üye 0 TL ile başlar

        // Yeni kullanıcı oluştur
        User newUser = new Customer(newId, name, email, pass, startBalance, "Adres Yok", "Tel Yok");
        
        // 1. Listeye ekle (Anlık kullanım için)
        users.add(newUser);
        
        // 2. Dosyaya kaydet (Kalıcılık için)
        FileHelper.saveUser("users.csv", newUser);
        
        System.out.println(">>> Kayıt Başarılı! Şimdi giriş yapabilirsiniz.");
    }

    public static void showMenu() {
        System.out.println("\n--- YEMEK LİSTESİ ---");
        List<MenuItem> menu = restaurant.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    public static void balanceOperations() {
        System.out.println("Mevcut Bakiyeniz: " + loggedInUser.getBalance() + " TL");
        System.out.print("Para yüklemek ister misiniz? (E/H): ");
        String resp = scanner.nextLine();
        if (resp.equalsIgnoreCase("E")) {
            System.out.print("Yüklenecek Miktar: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                loggedInUser.addBalance(amount);
            } catch (NumberFormatException e) {
                System.out.println("Hatalı miktar girdiniz.");
            }
        }
    }

    public static void placeOrder() {
        Order order = new Order((Customer) loggedInUser);
        boolean ordering = true;
        
        while (ordering) {
            showMenu();
            System.out.print("Sepete eklemek için numara girin (Bitirmek için 0): ");
            int itemIndex = -1;
            try {
                itemIndex = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sayı giriniz.");
                continue;
            }
            
            if (itemIndex == 0) {
                ordering = false;
            } else if (itemIndex > 0 && itemIndex <= restaurant.getMenu().size()) {
                order.addItem(restaurant.getMenu().get(itemIndex - 1));
            } else {
                System.out.println("Geçersiz numara!");
            }
        }
        
        double total = order.calculateTotal();
        if (total == 0) {
            System.out.println("Sepetiniz boş, ana menüye dönülüyor.");
            return;
        }

        System.out.println("Toplam Tutar: " + total + " TL");
        System.out.println("Ödeme Yöntemi: 1-Nakit, 2-Kredi Kartı, 3-Cüzdan Bakiyesi, 0-İptal");
        
        int pType = -1;
        try {
            pType = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Hatalı seçim.");
            return;
        }
        
        if (pType == 1) {
            order.setPaymentMethod(new CashPayment());
            order.completeOrder();
        } else if (pType == 2) {
            System.out.print("Kart No Giriniz (Örn: 1234 5678...): ");
            // DÜZELTME: nextLine() kullanarak tüm satırı alıyoruz
            String cNo = scanner.nextLine();
            
            // Kart numarasındaki boşlukları temizle (1234 5678 -> 12345678)
            String cleanCardNo = cNo.replace(" ", "");
            
            order.setPaymentMethod(new CreditCardPayment(cleanCardNo, "123"));
            order.completeOrder();
        } else if (pType == 3) {
            if (loggedInUser.deductBalance(total)) {
                System.out.println("Cüzdandan ödendi. Kalan: " + loggedInUser.getBalance());
                order.printReceipt();
            }
        } else {
            System.out.println("Sipariş iptal edildi.");
        }
    }
}