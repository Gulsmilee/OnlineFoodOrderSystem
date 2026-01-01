package com.oyes;

public class CashPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        System.out.println(amount + " TL Nakit ödendi.");
        return true;
    }
}