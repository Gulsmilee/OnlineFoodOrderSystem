package com.oyes;

public class CreditCardPayment implements PaymentMethod{
	
	private String cardNumber;
	private String cvv;
	
	public CreditCardPayment(String cardNumber, String cvv) {
		this.cardNumber=cardNumber;
		this.cvv=cvv;
		
	}
	
	@Override
	public boolean pay(double amount) {
		System.out.println("Banka ile iletişim kuruluyor...");
		System.out.println(amount + "TL," + cardNumber + "nolu karttan çekilmiştir.");
		return true;
		
	}

}
