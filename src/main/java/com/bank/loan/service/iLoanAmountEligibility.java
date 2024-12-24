package com.bank.loan.service;

import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;

public interface iLoanAmountEligibility {

	public String loanAmountEligibility(Loan loan, Customer cust);
}
