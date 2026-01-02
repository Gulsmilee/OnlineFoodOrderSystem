package com.oyes;

import java.util.List;
import java.util.Scanner;

/**
 * Ana Uygulama Sınıfı (Main).
 * Projenin beyni burasıdır. Kullanıcıyı karşılar, giriş yaptırır ve menüleri yönetir.
 */
public class Main {

    // Global Değişkenler (Tüm metodlardan erişilebilir)
    static List<User> users;
    static Restaurant restaurant;
    static Scanner scanner = new Scanner(System.in);
    static User loggedInUser = null; // O an sisteme giren kullanıcıyı tutar

    public static void main(String[] args) {
        System.out.println("=== OYES SİSTEMİ BAŞLATILIYOR ===");
        
        // 1. Verileri Dosyalardan Yükle (CSV Okuma)
        users = FileHelper.loadUsers("users.csv");
        List<MenuItem> menuItems = FileHelper.loadMenu("menu.csv");
        
        // Restoranı oluştur ve menüyü içine ekle
        restaurant = new Restaurant("Lezzet Dünyası");
        for (MenuItem item : menuItems) {
            restaurant.addMenuItem(item);
        }

        // --- GİRİŞ / KAYIT DÖNGÜSÜ ---
     // --- GİRİŞ / KAYIT DÖNGÜSÜ ---
        boolean loginSuccess = false;
        while (!loginSuccess) {
            System.out.println("\n--- Lezzet Dünyasına HOŞGELDİNİZ ---");
            System.out.println("1. Giriş Yap");
            System.out.println("2. Kayıt Ol (Yeni Kullanıcı)");
            System.out.println("3. Şifremi Unuttum"); // YENİ SEÇENEK
            System.out.print("Seçiminiz: ");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen sadece sayı giriniz!");
                continue;
            }

            if (choice == 1) {
                // Giriş İşlemi (Aynı kalacak)
                System.out.print("E-posta: ");
                String email = scanner.nextLine();
                System.out.print("Şifre: ");
                String pass = scanner.nextLine();
                
                loggedInUser = login(email, pass);
                if (loggedInUser != null) {
                    loginSuccess = true;
                    System.out.println("\n>>> Başarıyla giriş yapıldı. Hoşgeldin, " + loggedInUser.getName());
                } else {
                    System.out.println("!!! Hatalı E-posta veya Şifre.");
                }
            } 
            else if (choice == 2) {
                register();
            } 
            else if (choice == 3) {
                // YENİ: Şifre sıfırlama metodunu çağır
                forgotPassword();
            }
            else {
                System.out.println("Geçersiz seçim!");
            }
        }

        // --- ANA MENÜ (SİMÜLASYON) ---
        // Giriş yapıldıktan sonra programın ana akışı burada döner
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
                String input = scanner.nextLine();
                mainChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                mainChoice = -1;
            }

            switch (mainChoice) {
                case 1:
                    showMenu(); // Menüyü yazdır
                    break;
                case 2:
                    balanceOperations(); // Para yükleme işlemleri
                    break;
                case 3:
                    placeOrder(); // Sipariş verme işlemleri (Ödeme burada)
                    break;
                case 0:
                    exit = true; // Döngüyü kırar ve programı kapatır
                    System.out.println("Çıkış yapılıyor... İyi günler!");
                    break;
                default:
                    System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
            }
        }
    }

    // --- YARDIMCI METODLAR ---

    // Kullanıcıyı e-posta ve şifreye göre listede arar
    public static User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // Yeni kullanıcı kaydı alır ve CSV'ye yazar
    public static void register() {
        System.out.println("\n--- YENİ KULLANICI KAYDI ---");
        System.out.print("Adınız Soyadınız: ");
        String name = scanner.nextLine();
        
        System.out.print("E-posta Adresiniz: ");
        String email = scanner.nextLine();
        
        // Aynı mail adresi var mı kontrolü
        for(User u : users) {
            if(u.getEmail().equals(email)) {
                System.out.println("Bu e-posta zaten kayıtlı!");
                return;
            }
        }

        System.out.print("Şifreniz: ");
        String pass = scanner.nextLine();

        // Otomatik ID ver ve bakiyeyi 0 yap
        String newId = String.valueOf(users.size() + 1);
        double startBalance = 0.0; 

        // Polymorphism: Herkesi Customer olarak oluşturuyoruz
        User newUser = new Customer(newId, name, email, pass, startBalance, "Adres Yok", "Tel Yok");
        
        users.add(newUser); // Listeye ekle
        FileHelper.saveUser("users.csv", newUser); // Dosyaya kaydet
        
        System.out.println(">>> Kayıt Başarılı! Şimdi giriş yapabilirsiniz.");
    }

    // Menüdeki yemekleri listeler
    public static void showMenu() {
        System.out.println("\n--- YEMEK LİSTESİ ---");
        List<MenuItem> menu = restaurant.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    // Bakiye gösterme ve yükleme işlemleri
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

 // Sipariş oluşturma, sepete ekleme ve ödeme yapma
    public static void placeOrder() {
        Order order = new Order((Customer) loggedInUser);
        boolean ordering = true;
        
        // --- 1. SEPETE ÜRÜN EKLEME DÖNGÜSÜ ---
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
                ordering = false; // Siparişi tamamlamaya geç
            } else if (itemIndex > 0 && itemIndex <= restaurant.getMenu().size()) {
                order.addItem(restaurant.getMenu().get(itemIndex - 1));
            } else {
                System.out.println("Geçersiz numara!");
            }
        }
        
        // Sepet toplamını hesapla
        double total = order.calculateTotal();
        if (total == 0) {
            System.out.println("Sepetiniz boş, ana menüye dönülüyor.");
            return;
        }

        System.out.println("\n>>> Toplam Tutar: " + total + " TL");

        // --- 2. ÖDEME DÖNGÜSÜ (YENİ KISIM) ---
        // Ödeme başarılı olana kadar veya iptal edilene kadar döner
        boolean paymentSuccess = false;
        
        while (!paymentSuccess) {
            System.out.println("\n--------------------------------");
            System.out.println("Ödeme Yöntemi Seçiniz:");
            System.out.println("1- Nakit");
            System.out.println("2- Kredi Kartı");
            System.out.println("3- Cüzdan Bakiyesi (Mevcut: " + loggedInUser.getBalance() + " TL)");
            System.out.println("0- İptal Et ve Ana Menüye Dön");
            System.out.println("--------------------------------");
            System.out.print("Seçiminiz: ");
            
            int pType = -1;
            try {
                pType = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Hatalı seçim, tekrar deneyin.");
                continue;
            }
            
            if (pType == 0) {
                System.out.println("Sipariş iptal edildi. Ana menüye dönülüyor.");
                return; // Metottan komple çıkar
            }
            
            else if (pType == 1) {
                // Nakit Ödeme
                order.setPaymentMethod(new CashPayment());
                order.completeOrder();
                paymentSuccess = true; // Döngüden çıkmak için
                
            } else if (pType == 2) {
                // Kredi Kartı Ödeme
                System.out.print("Kart No Giriniz (Örn: 1234 5678...): ");
                String cNo = scanner.nextLine();
                String cleanCardNo = cNo.replace(" ", ""); 
                
                // Kart numarasını burada basitçe kontrol edebiliriz veya Luhn içeride bakar
                order.setPaymentMethod(new CreditCardPayment(cleanCardNo, "123"));
                
                // NOT: completeOrder içinde Luhn kontrolü başarısız olursa status değişmez
                // Ama biz burada basitlik olsun diye "denendi" sayıp çıkıyoruz.
                
                order.completeOrder();
                paymentSuccess = true;
                
            } else if (pType == 3) {
                // Cüzdan (Bakiye) ile ödeme
                if (loggedInUser.deductBalance(total)) {
                    System.out.println(">> Cüzdandan ödendi. Kalan Bakiyeniz: " + loggedInUser.getBalance() + " TL");
                    order.printReceipt();
                    paymentSuccess = true; // Başarılı, döngüden çık
                } else {
                    // --- KRİTİK NOKTA ---
                    // paymentSuccess hala 'false'. Döngü başa döner!
                    System.out.println("\n!!! HATA: Yetersiz Bakiye! Lütfen başka bir yöntem seçin veya İptal (0) yapın.");
                }
            } else {
                System.out.println("Geçersiz seçim!");
            }
        }
    }
 // Şifremi Unuttum Metodu
    public static void forgotPassword() {
        System.out.println("\n--- ŞİFRE SIFIRLAMA ---");
        System.out.print("Sisteme kayıtlı E-posta adresinizi girin: ");
        String email = scanner.nextLine();
        
        User foundUser = null;
        // Kullanıcıyı e-posta ile bul
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                foundUser = u;
                break;
            }
        }
        
        if (foundUser != null) {
            System.out.println("Kullanıcı bulundu: " + foundUser.getName());
            System.out.print("Yeni Şifrenizi Giriniz: ");
            String newPass = scanner.nextLine();
            
            // 1. Hafızadaki bilgiyi güncelle
            foundUser.setPassword(newPass);
            
            // 2. CSV dosyasını güncelle (Kalıcı olması için)
            FileHelper.updateAllUsers("users.csv", users);
            
            System.out.println(">>> Şifreniz başarıyla güncellendi! Şimdi giriş yapabilirsiniz.");
        } else {
            System.out.println("!!! HATA: Bu e-posta adresiyle kayıtlı kullanıcı bulunamadı.");
        }
    }
}