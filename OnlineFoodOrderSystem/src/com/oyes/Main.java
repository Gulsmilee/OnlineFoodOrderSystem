package com.oyes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ana Uygulama Sınıfı (Main).
 * Programın beyni burasıdır. Kullanıcı girişini, menüleri ve sipariş döngüsünü yönetir.
 */
public class Main {

    // --- GLOBAL DEĞİŞKENLER ---
    // Programın her yerinden erişilebilmesi için static tanımladım.
    static List<User> users;          // Sistemdeki tüm kullanıcıların listesi
    static Restaurant restaurant;     // Restoran nesnesi (Menüyü tutar)
    static Scanner scanner = new Scanner(System.in); // Konsoldan veri almak için araç
    static User loggedInUser = null;  // O an sisteme giriş yapmış kullanıcıyı tutar

    public static void main(String[] args) {
        System.out.println("=== OYES SİSTEMİ BAŞLATILIYOR ===");
        
        // 1. ADIM: Verileri Yükleme
        // FileHelper sınıfı yardımıyla CSV dosyalarından kullanıcıları çekiyoruz.
        users = FileHelper.loadUsers("users.csv");
        
        // Eğer dosya boşsa veya hata varsa program çökmesin diye boş liste oluşturuyoruz.
        if (users == null) users = new ArrayList<>(); 

        // Menüyü dosyadan yüklüyoruz.
        List<MenuItem> menuItems = FileHelper.loadMenu("menu.csv");
        
        // Restoranı oluşturup menüyü içine yüklüyoruz.
        restaurant = new Restaurant("Lezzet Dünyası");
        if (menuItems != null) {
            for (MenuItem item : menuItems) {
                restaurant.addMenuItem(item);
            }
        }

        boolean systemRunning = true; // Programı kapatana kadar döngünün dönmesini sağlayan anahtar.
        
        // --- ANA PROGRAM DÖNGÜSÜ ---
        while (systemRunning) {
            loggedInUser = null; // Her döngü başında (logout durumunda) kullanıcıyı sıfırla.
            boolean loginSuccess = false;

            // --- A. GİRİŞ EKRANI DÖNGÜSÜ ---
            // Kullanıcı giriş yapana veya sistemi kapatana kadar burası döner.
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
                    // Kullanıcıdan seçim alıyoruz. Harf girerse hata vermesin diye try-catch var.
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) { 
                    System.out.println("Lütfen geçerli bir sayı giriniz!"); 
                    continue; // Hata varsa döngü başına dön
                }

                if (choice == 1) {
                    // --- Giriş Yapma İşlemi ---
                    System.out.print("E-posta: ");
                    String email = scanner.nextLine();
                    System.out.print("Şifre: ");
                    String pass = scanner.nextLine();
                    
                    loggedInUser = login(email, pass); // login metoduna bilgileri gönder
                    if (loggedInUser != null) {
                        System.out.println("Giriş Başarılı! Hoşgeldin " + loggedInUser.getName());
                        loginSuccess = true; // Döngüyü kırıp ana menüye geçmek için
                    } else {
                        System.out.println("Hatalı e-posta veya şifre.");
                    }
                    
                } else if (choice == 2) {
                    register(); // Kayıt olma metodunu çağır
                } else if (choice == 3) {
                    forgotPassword(); // Şifre yenileme metodunu çağır
                } else if (choice == 0) {
                    System.out.println("Sistem kapatılıyor... İyi günler!");
                    systemRunning = false; // Ana döngüyü de kırar ve program tamamen biter.
                }
            }

            if (!systemRunning) break; // Sistem kapatıldıysa alt kısımdaki kullanıcı menüsüne hiç girme.

            // --- B. KULLANICI MENÜSÜ DÖNGÜSÜ ---
            // Kullanıcı giriş yaptıktan sonra burası çalışır. "Çıkış Yap" diyene kadar döner.
            boolean userSessionActive = true;
            while (userSessionActive) {
                System.out.println("\n--- ANA MENÜ (" + loggedInUser.getName() + ") ---");
                System.out.println("1. Menüyü Gör");
                System.out.println("2. Bakiye İşlemleri");
                System.out.println("3. Sipariş Ver");
                System.out.println("4. Geçmiş Siparişlerim"); 
                System.out.println("0. Oturumu Kapat");
                System.out.print("Seçiminiz: ");
                
                int mainChoice = -1;
                try {
                    mainChoice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) { mainChoice = -1; }

                switch (mainChoice) {
                    case 1: 
                        showMenu(); // Sadece menüyü listeler
                        break;
                    case 2: 
                        balanceOperations(); // Bakiye yükleme/sorgulama
                        break;
                    case 3: 
                        placeOrder(); // Sipariş verme süreci (Sepet işlemleri)
                        break; 
                    case 4: 
                        // Dosyadan kullanıcının eski siparişlerini okur ve ekrana basar
                        FileHelper.showOrderHistory(loggedInUser.getEmail()); 
                        break;
                    case 0: 
                        userSessionActive = false; // Bu döngüden çıkar, en başa (Giriş Ekranına) döner
                        System.out.println("Oturum kapatıldı.");
                        break;
                    default: 
                        System.out.println("Geçersiz seçim.");
                }
            }
        }
    }

    // --- YARDIMCI METODLAR ---
    
    /**
     * Kullanıcı girişi kontrolü yapar.
     *  email Girilen e-posta
     *  password Girilen şifre
     * @return Eşleşen User nesnesi veya null
     */
    public static User login(String email, String password) {
        if (users == null) return null;
        for (User u : users) {
            // E-posta ve şifre eşleşiyor mu kontrol et
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) return u;
        }
        return null; // Bulunamazsa null döner
    }

    /**
     * Yeni kullanıcı kaydı alır ve listeye/dosyaya ekler.
     */
    public static void register() {
        System.out.println("\n--- YENİ ÜYE KAYDI ---");
        System.out.print("Ad-Soyad: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        
        // Bu email daha önce kullanılmış mı kontrolü
        for(User u : users) { 
            if(u.getEmail().equals(email)) { 
                System.out.println("HATA: Bu e-posta ile zaten kayıtlı bir kullanıcı var!"); 
                return; 
            }
        }
        
        System.out.print("Şifre: "); String pass = scanner.nextLine();
        
        // Yeni bir Customer (Müşteri) nesnesi oluşturuyoruz. Varsayılan bakiye 0.0 TL.
        User newUser = new Customer(String.valueOf(users.size()+1), name, email, pass, 0.0, "", "");
        users.add(newUser);
        
        // Dosyaya da kaydediyoruz ki program kapanınca silinmesin.
        FileHelper.saveUser("users.csv", newUser);
        System.out.println("Kayıt Başarılı! Lütfen giriş yapınız.");
    }
    
    /**
     * Şifre sıfırlama işlemi.
     */
    public static void forgotPassword() {
        System.out.print("Kayıtlı Email: "); String email = scanner.nextLine();
        for(User u : users) {
            if(u.getEmail().equals(email)) {
                System.out.print("Yeni Şifreniz: ");
                u.setPassword(scanner.nextLine());
                
                // Sadece o kullanıcıyı değil, tüm listeyi güncelleyip dosyayı yeniden yazıyoruz.
                FileHelper.updateAllUsers("users.csv", users);
                System.out.println("Şifre başarıyla güncellendi.");
                return;
            }
        }
        System.out.println("Kullanıcı bulunamadı.");
    }

    /**
     * Restoran menüsünü ekrana yazdırır.
     */
    public static void showMenu() {
        System.out.println("\n--- YEMEK LİSTESİ ---");
        List<MenuItem> menu = restaurant.getMenu();
        if (menu.isEmpty()) {
            System.out.println("Menü şu an boş.");
            return;
        }
        for (int i = 0; i < menu.size(); i++) {
            // Kullanıcıya 1'den başlayan numara gösteriyoruz (i+1)
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }

    /**
     * Bakiye sorgulama ve yükleme işlemleri.
     */
    public static void balanceOperations() {
        System.out.println("\n--- CÜZDAN ---");
        System.out.println("Mevcut Bakiye: " + loggedInUser.getBalance() + " TL");
        System.out.print("Bakiye yüklemek ister misin? (E/H): ");
        String response = scanner.nextLine();
        
        if (response.equalsIgnoreCase("E")) {
            System.out.print("Yüklenecek Miktar: ");
            try { 
                double amount = Double.parseDouble(scanner.nextLine());
                loggedInUser.addBalance(amount); // Bakiyeyi nesne üzerinde güncelle
                FileHelper.updateAllUsers("users.csv", users); // Dosyayı güncelle
                System.out.println("Yeni Bakiye: " + loggedInUser.getBalance() + " TL");
            }
            catch(Exception e) { System.out.println("Hatalı miktar girdiniz."); }
        }
    }

    /**
     * --- EN ÖNEMLİ METOD: SİPARİŞ SÜRECİ ---
     * Ürün ekleme, çıkarma ve ödeme işlemlerini yönetir.
     */
    public static void placeOrder() {
        // Polymorphism: Giriş yapan User'ı Customer tipine çevirip (cast) Order oluşturuyoruz.
        if (!(loggedInUser instanceof Customer)) {
            System.out.println("Sadece müşteriler sipariş verebilir.");
            return;
        }

        Order order = new Order((Customer) loggedInUser);
        boolean ordering = true; // Sipariş oluşturma döngüsü kontrolü
        
        System.out.println("\n--- SİPARİŞ OLUŞTURMA ---");
        System.out.println("BİLGİ: Ürün eklemek için numarasını (Örn: 1),");
        System.out.println("       Silmek için başına eksi koyarak (Örn: -1) girin.");
        
        while (ordering) {
            showMenu(); // Her adımda menüyü göster
            System.out.println("--------------------------------");
            // Anlık sepet tutarını göster
            System.out.println(">> Şu anki Sepet Tutarı: " + order.calculateTotal() + " TL");
            System.out.print("Seçim (Ödeme Ekranı için 0): ");
            
            int choice = -999;
            try { choice = Integer.parseInt(scanner.nextLine()); } 
            catch (Exception e) { continue; }
            
            if (choice == 0) {
                ordering = false; // Döngüden çık, ödemeye geç
            } 
            else if (choice > 0 && choice <= restaurant.getMenu().size()) {
                // POZİTİF Sayı Girildi: SEPETE EKLE
                // Listeler 0 indexli olduğu için kullanıcının girdiği sayıdan 1 çıkarıyoruz.
                MenuItem selectedItem = restaurant.getMenu().get(choice - 1);
                order.addItem(selectedItem);
                // NOT: Burada "Eklendi" yazısı yazdırmıyoruz, Order sınıfı zaten yazıyor.
            } 
            else if (choice < 0 && Math.abs(choice) <= restaurant.getMenu().size()) {
                // NEGATİF Sayı Girildi: SEPETTEN ÇIKAR
                // Math.abs(-2) -> 2 yapar. Sonra 1 çıkarıp indexi buluruz.
                int indexToDelete = Math.abs(choice) - 1;
                MenuItem itemToDelete = restaurant.getMenu().get(indexToDelete);
                order.removeItem(itemToDelete); 
                System.out.println(itemToDelete.getName() + " sepetten çıkarıldı.");
            } 
            else {
                System.out.println("Geçersiz numara!");
            }
        }
        
        // --- ÖDEME AŞAMASI ---
        double total = order.calculateTotal();
        if (total == 0) {
            System.out.println("Sepetiniz boş, ana menüye dönülüyor.");
            return;
        }

        System.out.println("\n>>> TOPLAM ÖDENECEK TUTAR: " + total + " TL");
        
        boolean paymentSuccess = false; // Ödeme başarılı mı kontrol değişkeni
        
        while (!paymentSuccess) {
            System.out.println("1-Nakit | 2-Kredi Kartı | 3-Online Cüzdan | 0-İptal");
            System.out.print("Ödeme Yöntemi Seçiniz: ");
            int pType = -1;
            try { pType = Integer.parseInt(scanner.nextLine()); } catch(Exception e) {}
            
            if (pType == 0) {
                System.out.println("Sipariş iptal edildi.");
                return; // Metottan komple çık
            }
            
            if (pType == 1) { // --- Nakit Ödeme ---
                order.setPaymentMethod(new CashPayment());
                order.completeOrder();
                paymentSuccess = true; // Nakitte hata olmaz, başarılı sayılır.
                
            } else if (pType == 2) { // --- Kredi Kartı Seçimi ---
                System.out.print("Kart Numarası (Boşluksuz): ");
                String cNo = scanner.nextLine().replace(" ", "");
                
                System.out.print("CVV (3 haneli): ");
                String cvv = scanner.nextLine();
                
                System.out.print("Son Kullanma Tarihi (AA/YY örn: 12/25): ");
                String date = scanner.nextLine();

                // CreditCardPayment sınıfına 3 parametre (No, CVV, Tarih) gönderiyoruz.
                order.setPaymentMethod(new CreditCardPayment(cNo, cvv, date)); 
                
                // completeOrder() metodu kart hatası varsa false döner
                if(order.completeOrder()) {
                    paymentSuccess = true;
                }
                
            } else if (pType == 3) { // --- Cüzdan Bakiyesi ile Ödeme ---
                if (loggedInUser.deductBalance(total)) {
                    System.out.println("Ödeme cüzdandan alındı. Kalan Bakiye: " + loggedInUser.getBalance());
                    // Bakiyeyi dosyada güncellemek önemli!
                    FileHelper.updateAllUsers("users.csv", users);
                    paymentSuccess = true;
                } else {
                    System.out.println("!!! Yetersiz Bakiye. Lütfen başka bir yöntem seçin.");
                }
            } else {
                System.out.println("Geçersiz ödeme yöntemi.");
            }
        }

        // --- SON İŞLEMLER (Fiş Yazdırma ve Kayıt) ---
        // Ödeme başarılı olduysa bu bloğa girer.
        if (paymentSuccess) {
            System.out.println("\n--------------------------------");
            order.printReceipt(); // <--- DÜZELTME: Artık her ödeme türünde fiş basılacak.
            System.out.println("--------------------------------");
            
            // 1. Siparişi "orders.csv" dosyasına kaydet (Geçmiş siparişler özelliği için)
            FileHelper.saveOrderToHistory(loggedInUser.getEmail(), order.getOrderDetails(), total);
            
            // 2. Kullanıcıdan hizmeti puanlamasını iste
            evaluateService();
        }
    }

    /**
     * Sipariş sonrası puan ve yorum alma.
     */
    public static void evaluateService() {
        System.out.println("\n--- DEĞERLENDİRME ---");
        System.out.print("Hizmetimizi 1-5 arası puanlayın: ");
        try {
            int score = Integer.parseInt(scanner.nextLine());
            if(score < 1 || score > 5) score = 5; // Hatalı girerse varsayılan 5 olsun
            
            System.out.print("Yorumunuz (İsteğe bağlı): ");
            String comment = scanner.nextLine();
            
            // Yorumu reviews.csv dosyasına kaydet
            String reviewData = loggedInUser.getName() + "," + score + "," + comment;
            FileHelper.saveReview(reviewData);
            
            System.out.println("Geri bildiriminiz için teşekkürler! Tekrar bekleriz ^^ <3 ");
        } catch(Exception e) { 
            System.out.println("Puanlama pas geçildi."); 
        }
    }
}