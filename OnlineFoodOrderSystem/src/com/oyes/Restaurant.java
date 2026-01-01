package com.oyes;

import java.util.ArrayList;
import java.util.List;

/**
 * Restoran sınıfı.
 * İçinde bir menü listesi barındırır (Composition).
 */
public class Restaurant {
    
    private String name;
    private List<MenuItem> menu;

    public Restaurant(String name) {
        this.name = name;
        this.menu = new ArrayList<>();
    }

    /**
     * Menüye yeni yemek ekler.
     */
    public void addMenuItem(MenuItem item) {
        this.menu.add(item);
    }

    /**
     * Tüm menüyü döndürür.
     */
    public List<MenuItem> getMenu() {
        return menu;
    }

    public String getName() { return name; }
}