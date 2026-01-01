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

    // YENİ: Kullanıcıyı dosyaya kaydeder (Append Mode)
    public static void saveUser(String fileName, User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            // Yeni satıra geçmeden önce kontrol edilebilir ama şimdilik direkt ekleyelim
            // Format: id,name,email,password,balance
            String line = String.format("\n%s,%s,%s,%s,%s", 
                    user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getBalance());
            bw.write(line);
        } catch (IOException e) {
            System.out.println("Kayıt hatası: " + e.getMessage());
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
}