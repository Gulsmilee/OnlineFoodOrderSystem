package com.oyes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Dosya İşlemleri Yardımcısı.
 * CSV dosyalarını okuma ve yazma işlemlerini yönetir.
 */
public class FileHelper {

    // --- MEVCUT METODLAR (Kullanıcı ve Menü Yükleme) ---
    
    public static List<User> loadUsers(String fileName) {
        List<User> userList = new ArrayList<>();
        // try-with-resources yapısı: Dosyayı işlem bitince otomatik kapatır
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) { //satir satir okur
                if (isFirstLine) { isFirstLine = false; continue; } // Başlık satırını atla
                
                String[] data = line.split(","); //virgul ayırma
                if(data.length < 5) continue; // Hatalı satır varsa atla

                // Verileri parçala ve nesneye çevir
                String id = data[0].trim();
                String name = data[1].trim();
                String email = data[2].trim();
                String password = data[3].trim();
                double balance = Double.parseDouble(data[4].trim());
                
                User u = new Customer(id, name, email, password, balance, "Adres Yok", "Tel Yok");
                userList.add(u);
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
        return userList;
    }
//yeni uye kaydı
    public static void saveUser(String fileName, User user) {
    	// new FileWriter(fileName, TRUE) -> TRUE parametresi "EKLEME MODU" demektir.
        // Yani eski verileri silme, dosyanın en altına yenisini ekle.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            String line = String.format("\n%s,%s,%s,%s,%s", 
                    user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getBalance());
            bw.write(line);
        } catch (IOException e) {
            System.out.println("Kayıt hatası: " + e.getMessage());
        }
    }
    
    // Şifre değiştiğinde tüm dosyayı güncelleyen metod
    public static void updateAllUsers(String fileName, List<User> userList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) { 
            // 'false' = Dosyanın içini temizle ve baştan yaz
            bw.write("id,name,email,password,balance"); // Başlığı tekrar yaz
            for (User user : userList) {
                bw.newLine();
                String line = String.format("%s,%s,%s,%s,%s", 
                        user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getBalance());
                bw.write(line);
            }
        } catch (IOException e) {
            System.out.println("Dosya güncelleme hatası: " + e.getMessage());
        }
    }

    public static List<MenuItem> loadMenu(String fileName) {
        List<MenuItem> menuList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] data = line.split(",");
                if(data.length < 3) continue;
                menuList.add(new MenuItem(data[0].trim(), data[1].trim(), Double.parseDouble(data[2].trim())));
            }
        } catch (IOException e) {
            System.out.println("Menü okuma hatası: " + e.getMessage());
        }
        return menuList;
    }
    
    // ---  Yorumları Kaydetme ---
    public static void saveReview(String reviewLine) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reviews.csv", true))) {
            bw.write(reviewLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Yorum kaydedilemedi: " + e.getMessage());
        }
    }

    // --- YENİ: Siparişi Geçmişe Kaydetme ---
    // Sipariş tamamlandığında orders.csv dosyasına ekler.
    public static void saveOrderToHistory(String userEmail, String orderDetails, double total) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("orders.csv", true))) {
            // Format: email;yemek_listesi;toplam_tutar
            // Noktalı virgül (;) kullanıyoruz çünkü yemek isimlerinde virgül olabilir.
            String line = userEmail + ";" + orderDetails + ";" + total;
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Sipariş geçmişe kaydedilemedi: " + e.getMessage());
        }
    }

    // --- YENİ: Geçmiş Siparişleri Listeleme ---
    // Dosyayı okur ve sadece giriş yapan kullanıcıya ait olan satırları ekrana basar.
    public static void showOrderHistory(String userEmail) {
        System.out.println("\n--- GEÇMİŞ SİPARİŞLERİNİZ ---");
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader("orders.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                //filtreleme sadece giriş yapan kişinin e postasına ait siparişleri gösterir
                // Eğer satırda 3 veri varsa ve e-posta eşleşiyorsa yazdır
                if (data.length >= 3 && data[0].equals(userEmail)) {
                    System.out.println("• " + data[1] + " (Tutar: " + data[2] + " TL)");
                    found = true;
                }
            }
        } catch (IOException e) {
            // Dosya henüz oluşmamış olabilir, hata vermeye gerek yok
        }
        
        if (!found) {
            System.out.println("Henüz geçmiş siparişiniz bulunmuyor.");
        }
        System.out.println("-----------------------------");
    }
}