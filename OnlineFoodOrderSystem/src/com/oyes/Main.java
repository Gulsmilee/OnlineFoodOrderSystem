package com.oyes;

import java.util.List;
import java.util.Scanner;

/**
 * Ana Uygulama Sınıfı (Main).
 * Programın giriş noktasıdır. Menüleri ve kullanıcı etkileşimini yönetir.
 */
public class Main {

    static List<User> users;
    static Restaurant restaurant;
    static Scanner scanner = new Scanner(System.in);
    static User loggedInUser = null;

    public static void main(String[] args) {
        System.out.println("=== OYES SİSTEMİ BAŞLATILIYOR ===");
        
        // CSV Dosyalarından Verileri Yükle
        users = FileHelper.loadUsers("users.csv");
        List<MenuItem> menuItems = FileHelper.loadMenu("menu.csv");
        
        restaurant = new Restaurant("Lezzet Dünyası");
        for (MenuItem item : menuItems) {
            restaurant.addMenuItem(item);
        }

        boolean systemRunning = true; // Programı komple kapatmak için kontrol değişkeni
        
        while (systemRunning) {
            loggedInUser = null; // Her döngü başında oturumu sıfırla (Logout mantığı)
            boolean loginSuccess = false;

            // --- 1. GİRİŞ EKRANI DÖNGÜSÜ ---
            while (!loginSuccess && systemRunning) {
            	System.out.println("\n========================================");
                System.out.println("      Lezzet Dünyasına HOŞGELDİNİZ      ");
                System.out.println("========================================");
                System.out.println("1. Giriş Yap");
                System.out.println("2. Kayıt Ol");
                System.out.println("3. Şifremi Unuttum");
                System.out.println("0. SİSTEMİ KAPAT");
                System.out.print("Seçiminiz: ");
                
                int choice = -1;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) { continue; }

                if (choice == 1) {
                    // Giriş Yapma
                    System.out.print("E-posta: ");
                    String email = scanner.nextLine();
                    System.out.print("Şifre: ");
                    String pass = scanner.nextLine();
                    loggedInUser = login(email, pass);
                    if (loggedInUser != null) loginSuccess = true;
                    else System.out.println("Hatalı giriş.");
                    
                } else if (choice == 2) {
                    register(); // Kayıt Ol
                } else if (choice == 3) {
                    forgotPassword(); // Şifre Sıfırla
                } else if (choice == 0) {
                    systemRunning = false; // Programı sonlandır
                }
            }

            if (!systemRunning) break; // Eğer sistem kapatıldıysa alttaki kodlara girme

            // --- 2. KULLANICI MENÜSÜ DÖNGÜSÜ ---
            boolean userSessionActive = true;
            while (userSessionActive) {
                System.out.println("\n--- ANA MENÜ (" + loggedInUser.getName() + ") ---");
                System.out.println("1. Menüyü Gör");
                System.out.println("2. Bakiye İşlemleri");
                System.out.println("3. Sipariş Ver");
                System.out.println("4. Geçmiş Siparişlerim"); // YENİ SEÇENEK
                System.out.println("0. Oturumu Kapat");
                System.out.print("Seçiminiz: ");
                
                int mainChoice = -1;
                try {
                    mainChoice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) { mainChoice = -1; }

                switch (mainChoice) {
                    case 1: showMenu(); break;
                    case 2: balanceOperations(); break;
                    case 3: placeOrder(); break; // Sipariş süreci
                    case 4: 
                        // Geçmiş siparişleri dosyadan okuyup listeler
                        FileHelper.showOrderHistory(loggedInUser.getEmail()); 
                        break;
                    case 0: userSessionActive = false; break; // Oturumu kapatır, giriş ekranına döner
                    default: System.out.println("Geçersiz seçim.");
                }
            }
        }
    }

    // --- YARDIMCI METODLAR ---
    
    public static User login(String email, String password) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) return u;
        }
        return null;
    }

    public static void register() {
        System.out.print("Ad-Soyad: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        // Mail kontrolü...
        for(User u : users) { if(u.getEmail().equals(email)) { System.out.println("Zaten kayıtlı!"); return; }}
        
        System.out.print("Şifre: "); String pass = scanner.nextLine();
        User newUser = new Customer(String.valueOf(users.size()+1), name, email, pass, 0.0, "", "");
        users.add(newUser);
        FileHelper.saveUser("users.csv", newUser);
        System.out.println("Kayıt Başarılı.");
    }
    
    public static void forgotPassword() {
        System.out.print("Email: "); String email = scanner.nextLine();
        for(User u : users) {
            if(u.getEmail().equals(email)) {
                System.out.print("Yeni Şifre: ");
                u.setPassword(scanner.nextLine());
                FileHelper.updateAllUsers("users.csv", users);
                System.out.println("Şifre güncellendi.");
                return;
            }
        }
        System.out.println("Kullanıcı bulunamadı.");
    }

    public static void showMenu() {
        System.out.println("\n--- YEMEK LİSTESİ ---");
        List<MenuItem> menu = restaurant.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    public static void balanceOperations() {
        System.out.println("Bakiye: " + loggedInUser.getBalance() + " TL");
        System.out.print("Yüklemek ister misin? (E/H): ");
        if (scanner.nextLine().equalsIgnoreCase("E")) {
            System.out.print("Miktar: ");
            try { loggedInUser.addBalance(Double.parseDouble(scanner.nextLine())); }
            catch(Exception e) { System.out.println("Hata."); }
        }
    }

    // --- EN ÖNEMLİ METOD: SİPARİŞ VE SİLME İŞLEMLERİ ---
    public static void placeOrder() {
        Order order = new Order((Customer) loggedInUser);
        boolean ordering = true;
        
        System.out.println("\n--- SİPARİŞ OLUŞTURMA ---");
        System.out.println("BİLGİ: Ürün eklemek için numarasını (Örn: 1),");
        System.out.println("       Silmek için başına eksi koyarak (Örn: -1) girin.");
        
        while (ordering) {
            showMenu();
            // Kullanıcıya o an sepetinde ne kadar tutar olduğunu göster
            System.out.println(">> Şu anki Sepet Tutarı: " + order.calculateTotal() + " TL");
            System.out.print("Seçim (Bitirmek için 0): ");
            
            int choice = -999;
            try { choice = Integer.parseInt(scanner.nextLine()); } 
            catch (Exception e) { continue; }
            
            if (choice == 0) {
                ordering = false; // Döngüden çık, ödemeye geç
            } 
            else if (choice > 0 && choice <= restaurant.getMenu().size()) {
                // POZİTİF Sayı Girildi: ÜRÜN EKLEME
                // Listeler 0'dan başlar, o yüzden choice-1 yapıyoruz
                order.addItem(restaurant.getMenu().get(choice - 1));
            } 
            else if (choice < 0 && Math.abs(choice) <= restaurant.getMenu().size()) {
                // NEGATİF Sayı Girildi: ÜRÜN SİLME
                // Math.abs(-2) -> 2 yapar. Sonra 1 çıkarıp indexi buluruz.
                int indexToDelete = Math.abs(choice) - 1;
                order.removeItem(restaurant.getMenu().get(indexToDelete));
            } 
            else {
                System.out.println("Geçersiz numara!");
            }
        }
        
        // Sepet toplamını al
        double total = order.calculateTotal();
        if (total == 0) {
            System.out.println("Sepet boş, işlem iptal.");
            return;
        }

        System.out.println("\n>>> ÖDENECEK TUTAR: " + total + " TL");
        
        boolean paymentSuccess = false;
        while (!paymentSuccess) {
            System.out.println("1-Nakit | 2-Kart | 3-Cüzdan | 0-İptal");
            System.out.print("Ödeme Yöntemi: ");
            int pType = -1;
            try { pType = Integer.parseInt(scanner.nextLine()); } catch(Exception e) {}
            
            if (pType == 0) return; // İptal
            
            if (pType == 1) { // Nakit
                order.setPaymentMethod(new CashPayment());
                order.completeOrder();
                paymentSuccess = true;
            } else if (pType == 2) { // Kart
                System.out.print("Kart No: ");
                String cNo = scanner.nextLine().replace(" ", "");
                order.setPaymentMethod(new CreditCardPayment(cNo, "123"));
                order.completeOrder();
                paymentSuccess = true;
            } else if (pType == 3) { // Cüzdan
                if (loggedInUser.deductBalance(total)) {
                    System.out.println("Cüzdandan ödendi. Kalan Bakiye: " + loggedInUser.getBalance());
                    order.printReceipt();
                    paymentSuccess = true;
                } else {
                    System.out.println("!!! Yetersiz Bakiye.");
                }
            }
        }

        // ÖDEME BAŞARILIYSA İŞLEMLER
        if (paymentSuccess) {
            // 1. Siparişi "orders.csv" dosyasına kaydet (Geçmiş için)
            FileHelper.saveOrderToHistory(loggedInUser.getEmail(), order.getOrderDetails(), total);
            
            // 2. Kullanıcıdan puan ve yorum iste
            evaluateService();
        }
    }

    // Puanlama Metodu
    public static void evaluateService() {
        System.out.print("\nPuanla (1-5): ");
        try {
            int score = Integer.parseInt(scanner.nextLine());
            System.out.print("Yorumunuz: ");
            String comment = scanner.nextLine();
            // reviews.csv dosyasına kaydet
            FileHelper.saveReview(loggedInUser.getName() + "," + score + "," + comment);
            System.out.println("Teşekkürler! Yorumunuz kaydedildi.Tekrar bekleriz ^^ >3");
        } catch(Exception e) { 
            System.out.println("Puanlama geçildi."); 
        }
    }
}