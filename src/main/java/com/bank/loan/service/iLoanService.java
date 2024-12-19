package com.bank.loan.service;

import java.util.List;

import com.bank.loan.model.Loan;

public interface iLoanService {

	public Loan applyLoan(Loan l);

	public List<Loan> getLoansByCustomerId(int custId);

	public void foreCloseLoan(int loanId);

}
