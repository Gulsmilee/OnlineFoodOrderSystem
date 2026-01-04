package com.oyes;

import java.util.List;
import java.util.Scanner;

/**
 * Ana Uygulama Sınıfı.
 * Güncellemeler:
 * 1. Oturum kapatınca tekrar giriş ekranına döner (Sonsuz Döngü).
 * 2. Sipariş sonrası puanlama (Değerlendirme) sistemi eklendi.
 */
public class Main {

    static List<User> users;
    static Restaurant restaurant;
    static Scanner scanner = new Scanner(System.in);
    static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("=== OYES SİSTEMİ BAŞLATILIYOR ===");
        
        // Verileri Yükle
        users = FileHelper.loadUsers("users.csv");
        List<MenuItem> menuItems = FileHelper.loadMenu("menu.csv");
        
        restaurant = new Restaurant("Lezzet Dünyası");
        for (MenuItem item : menuItems) {
            restaurant.addMenuItem(item);
        }

        // --- ANA SİSTEM DÖNGÜSÜ (Programın Kapanmaması İçin) ---
        boolean systemRunning = true;
        
        while (systemRunning) {
            loggedInUser = null; // Her başa döndüğünde kullanıcıyı sıfırla (Logout)
            boolean loginSuccess = false;

            // --- 1. GİRİŞ EKRANI DÖNGÜSÜ ---
            while (!loginSuccess && systemRunning) {
                System.out.println("\n========================================");
                System.out.println("      Lezzet Dünyasına HOŞGELDİNİZ      ");
                System.out.println("========================================");
                System.out.println("1. Giriş Yap");
                System.out.println("2. Kayıt Ol (Yeni Kullanıcı)");
                System.out.println("3. Şifremi Unuttum");
                System.out.println("0. SİSTEMİ KAPAT"); // Programı buradan kapatacağız
                System.out.print("Seçiminiz: ");
                
                int choice = -1;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Lütfen sayı giriniz!");
                    continue;
                }

                if (choice == 1) {
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
                    forgotPassword();
                }
                else if (choice == 0) {
                    System.out.println("Sistem kapatılıyor... Güle güle!");
                    systemRunning = false; // Ana döngüyü kırar ve program biter
                }
                else {
                    System.out.println("Geçersiz seçim!");
                }
            }

            // Eğer sistem kapatıldıysa aşağıdaki menüye hiç girme
            if (!systemRunning) break;

            // --- 2. KULLANICI MENÜSÜ (SİMÜLASYON) ---
            boolean userSessionActive = true;
            while (userSessionActive) {
                System.out.println("\n--- ANA MENÜ (" + loggedInUser.getName() + ") ---");
                System.out.println("1. Menüyü Gör");
                System.out.println("2. Bakiye Sorgula / Yükle");
                System.out.println("3. Sipariş Ver");
                System.out.println("0. Oturumu Kapat (Giriş Ekranına Dön)");
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
                        showMenu();
                        break;
                    case 2:
                        balanceOperations();
                        break;
                    case 3:
                        placeOrder();
                        break;
                    case 0:
                        userSessionActive = false; // Bu döngüden çıkar, en üstteki döngüye döner
                        System.out.println("Oturum kapatılıyor...");
                        break;
                    default:
                        System.out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
                }
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

    public static void register() {
        System.out.println("\n--- YENİ KULLANICI KAYDI ---");
        System.out.print("Adınız Soyadınız: ");
        String name = scanner.nextLine();
        System.out.print("E-posta Adresiniz: ");
        String email = scanner.nextLine();
        
        for(User u : users) {
            if(u.getEmail().equals(email)) {
                System.out.println("Bu e-posta zaten kayıtlı!");
                return;
            }
        }
        System.out.print("Şifreniz: ");
        String pass = scanner.nextLine();
        String newId = String.valueOf(users.size() + 1);
        User newUser = new Customer(newId, name, email, pass, 0.0, "Adres Yok", "Tel Yok");
        users.add(newUser);
        FileHelper.saveUser("users.csv", newUser);
        System.out.println(">>> Kayıt Başarılı! Şimdi giriş yapabilirsiniz.");
    }
    
    public static void forgotPassword() {
        System.out.println("\n--- ŞİFRE SIFIRLAMA ---");
        System.out.print("E-posta adresiniz: ");
        String email = scanner.nextLine();
        User foundUser = null;
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                foundUser = u;
                break;
            }
        }
        if (foundUser != null) {
            System.out.print("Yeni Şifrenizi Giriniz: ");
            String newPass = scanner.nextLine();
            foundUser.setPassword(newPass);
            FileHelper.updateAllUsers("users.csv", users);
            System.out.println(">>> Şifre güncellendi!");
        } else {
            System.out.println("!!! Kullanıcı bulunamadı.");
        }
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
                System.out.println("Hatalı miktar.");
            }
        }
    }

    // --- SİPARİŞ ve DEĞERLENDİRME ---
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
            System.out.println("Sepetiniz boş.");
            return;
        }

        System.out.println("\n>>> Toplam Tutar: " + total + " TL");
        boolean paymentSuccess = false;
        
        while (!paymentSuccess) {
            System.out.println("1- Nakit | 2- Kredi Kartı | 3- Cüzdan | 0- İptal");
            System.out.print("Ödeme Yöntemi Seçiniz: ");
            int pType = -1;
            try {
                pType = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
            
            if (pType == 0) return;
            
            if (pType == 1) {
                order.setPaymentMethod(new CashPayment());
                order.completeOrder();
                paymentSuccess = true;
            } else if (pType == 2) {
                System.out.print("Kart No: ");
                String cNo = scanner.nextLine().replace(" ", "");
                order.setPaymentMethod(new CreditCardPayment(cNo, "123"));
                order.completeOrder();
                paymentSuccess = true;
            } else if (pType == 3) {
                if (loggedInUser.deductBalance(total)) {
                    System.out.println("Cüzdandan ödendi. Kalan: " + loggedInUser.getBalance());
                    order.printReceipt();
                    paymentSuccess = true;
                } else {
                    System.out.println("!!! Yetersiz Bakiye.");
                }
            } else {
                System.out.println("Geçersiz seçim.");
            }
        }

        // ÖDEME BAŞARILI OLDUYSA DEĞERLENDİRME AL
        if (paymentSuccess) {
            evaluateService();
        }
    }
    
   
 // GÜNCELLENMİŞ: Değerlendirme Metodu (Gerçek Kayıt Yapar)
    public static void evaluateService() {
        System.out.println("\n----------------------------------------");
        System.out.println("  Siparişiniz tamamlandı! Bizi puanlamayı unutmayınn :*)  ");
        System.out.println("----------------------------------------");
        System.out.print("Puanınız (1-5 arası): ");
        try {
            int score = Integer.parseInt(scanner.nextLine());
            System.out.print("Yorumunuz (İsteğe bağlı): ");
            String comment = scanner.nextLine();
            
            // CSV Formatında hazırlayalım: "KullanıcıAdı, Puan, Yorum"
            String reviewData = loggedInUser.getName() + "," + score + "," + comment;
            
            // Dosyaya kaydet
            FileHelper.saveReview(reviewData);
            
            System.out.println("\n>> Teşekkürler! Puanınız ve yorumunuz için. Tekrar bekleriz ^^ >3");
        } catch (NumberFormatException e) {
            System.out.println(">> Puanlama geçildi.");
        }
    }
}