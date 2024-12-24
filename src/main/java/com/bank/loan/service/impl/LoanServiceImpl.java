package com.bank.loan.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.loan.dao.CustomerRepository;
import com.bank.loan.dao.LoanRepository;
import com.bank.loan.exception.CustomerEligibilityException;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;
import com.bank.loan.model.Payment;
import com.bank.loan.service.iLoanService;

@Service
@Primary
public class LoanServiceImpl implements iLoanService {

	@Autowired
	private LoanRepository loanDao;

	@Autowired
	private CustomerRepository customerDao;
	
	@Autowired
	private EligibilityService checkEligibility;

	private Logger logger = Logger.getLogger(getClass());

	public Loan applyLoan(int customerId,Loan loan) {		 
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Cusotmer Not Found: " + customerId));
		long b=loanDao.findAll().stream()
			.filter(loans -> loans.getLoanType().equalsIgnoreCase(loan.getLoanType())&& loans.Customerobj().getId()==customerId && loans.getFlag().equalsIgnoreCase("Active")).count();
			System.out.println(b);
		boolean isEligible = checkEligibility.checkEligibility(customer,loan);
		if(!isEligible) {
			throw new CustomerEligibilityException("Customer is not eligible for Loan: " + customerId); 
		}else if(b>0){
			throw new CustomerEligibilityException("Customer already having same type of Loan : " + loan.getLoanType()); 
		}else {
		String loantype=loan.getLoanType();
		double loanAmt=loan.getLoanAmt();
		double monthEMIAmt=0;
		double rateInt=18;
		if(loantype.equalsIgnoreCase("HomeLoan")) {
			loan.setDuration(20);
			rateInt=8;
		}else if(loantype.equalsIgnoreCase("VehicleLoan")) {
			loan.setDuration(5);
			rateInt=10;
		}else {
			loan.setDuration(3);
			rateInt=14;
		}
		double p,r,t;
		p=loanAmt;
		r=rateInt/ (12 * 100); // one month interest
		t = loan.getDuration() * 12; // one month period
		monthEMIAmt=(p * r * (double)Math.pow(1 + r, t)) 
                / (double)(Math.pow(1 + r, t) - 1);//loanAmt/(loan.getDuration()*12);
		loan.setMonthlyEMI(Math.ceil(monthEMIAmt));
		customer.addLoan(loan);
		return loanDao.save(loan);
		}
		
	}

	@Override
	public List<Loan> getLoansByCustomerId(int customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
		return customer.getLoans();
	}

	@Override
	public void foreCloseLoan(int loanId) {
		Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
		loanDao.delete(loan);
	}

	@Override
	public double calculateEligibleLoanAmount(int customerId, String loanType) {
		// TODO Auto-generated method stub
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Cusotmer Not Found: " + customerId));
		double maximumLoanEligibileAmt= checkEligibility.calculateEligibleLoanAmount(customer, loanType);
		customer.setMaximumEligibleLoanAmount(maximumLoanEligibileAmt);
		customerDao.save(customer);
		return maximumLoanEligibileAmt;
	}

}
