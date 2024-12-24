package com.bank.loan.service.impl;

import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;
import com.bank.loan.service.iLoanAmountEligibility;

public class LoanAmountEligibility implements iLoanAmountEligibility{

	@Override
	public String loanAmountEligibility(Loan loan, Customer cust) {
		// TODO Auto-generated method stub
		String loanAmount="";
		double custSalary=cust.getSalary();
		double applyLoanAmount=loan.getLoanAmt();
		
		if(custSalary<50000 && applyLoanAmount<400000) {
			
		}
		
		return loanAmount;
	}

}
