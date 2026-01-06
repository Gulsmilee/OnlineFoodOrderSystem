package com.oyes;

public class CashPayment implements PaymentMethod {
    @Override // üstümden gelen metodu burada kendime göre yeniden yazıyorum
    public boolean pay(double amount) {
        System.out.println(amount + " TL Nakit ödendi.");
        return true;
    }
}