package com.oyes;

/**
 * Sipariş verilebilir ürünler için arayüz.
 * Bu arayüzü kullanan her sınıf, fiyat ve isim bilgisini vermek zorundadır.
 */
public interface Orderable {
    double getPrice();
    String getName();
}