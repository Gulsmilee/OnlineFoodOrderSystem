package com.oyes;

public class CashPayment implements PaymentMethod{
	
	@Override
	public boolean pay(double amount) {
		System.out.println(amount + " TL nakit olarak odendi. Para üstü veriliyor...");
		return true;
		
	}

}
