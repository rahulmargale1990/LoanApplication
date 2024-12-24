package com.bank.loan.service;

import java.util.*;

import com.bank.loan.model.Payment;

public interface PaymentService {

	public List<Payment> getPaymentByLoanId(int loanId);

	public Boolean closeLoan(int loanId);
	public Payment addPayment(int loanId,Payment payment);
	

//	public Payment savePayment(Payment payment);
	
//	public Customer findCustomerById(Integer customerId);
}
