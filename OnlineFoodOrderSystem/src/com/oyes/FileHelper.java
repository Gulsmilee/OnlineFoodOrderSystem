package com.oyes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    // users.csv dosyasını okur
    public static List<User> loadUsers(String fileName) {
        List<User> userList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                
                String[] data = line.split(","); 
                // Basit hata önleme: Eksik veri varsa atla
                if(data.length < 5) continue;

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

    // Kullanıcıyı dosyaya kaydeder (Append Mode - Sona Ekleme)
    // Kayıt Ol (Register) işlemi için kullanılır
    public static void saveUser(String fileName, User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            // Yeni satıra geç ve veriyi yaz
            String line = String.format("\n%s,%s,%s,%s,%s", 
                    user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getBalance());
            bw.write(line);
        } catch (IOException e) {
            System.out.println("Kayıt hatası: " + e.getMessage());
        }
    }

    // --- YENİ EKLENEN METOD BURASI ---
    // Şifre değiştiğinde tüm dosyayı baştan aşağı günceller (Overwrite Mode)
    public static void updateAllUsers(String fileName, List<User> userList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false))) { 
            // 'false' parametresi dosyanın içini silip baştan yazmasını sağlar
            
            // 1. Başlık satırını tekrar yazalım
            bw.write("id,name,email,password,balance");
            
            // 2. Listedeki tüm kullanıcıları (yeni şifreli halleriyle) yazalım
            for (User user : userList) {
                bw.newLine(); // Alt satıra geç
                String line = String.format("%s,%s,%s,%s,%s", 
                        user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getBalance());
                bw.write(line);
            }
        } catch (IOException e) {
            System.out.println("Dosya güncelleme hatası: " + e.getMessage());
        }
    }

    // menu.csv dosyasını okur
    public static List<MenuItem> loadMenu(String fileName) {
        List<MenuItem> menuList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }
                String[] data = line.split(",");
                if(data.length < 3) continue;

                String name = data[0].trim();
                String description = data[1].trim();
                double price = Double.parseDouble(data[2].trim());
                menuList.add(new MenuItem(name, description, price));
            }
        } catch (IOException e) {
            System.out.println("Menü okuma hatası: " + e.getMessage());
        }
        return menuList;
    }
    
 // --- YENİ: Yorumları dosyaya kaydetme ---
    public static void saveReview(String reviewLine) {
        // reviews.csv dosyasına ekleme yapar (true = append mode)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reviews.csv", true))) {
            bw.write(reviewLine);
            bw.newLine(); // Alt satıra geç
        } catch (IOException e) {
            System.out.println("Yorum kaydedilemedi: " + e.getMessage());
        }
    }
    
}