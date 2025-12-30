package com.oyes;

public interface PaymentMethod {
	//tüm ödeme yöntemlerinde olması gerekiyor.
	boolean pay(double amount);

}
