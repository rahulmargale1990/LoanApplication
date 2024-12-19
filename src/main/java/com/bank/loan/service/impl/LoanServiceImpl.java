package com.bank.loan.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bank.loan.dao.CustomerRepository;
import com.bank.loan.dao.LoanRepository;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;
import com.bank.loan.service.iLoanService;

@Service
@Primary
public class LoanServiceImpl implements iLoanService {

	@Autowired
	private LoanRepository loanDao;

	@Autowired
	private CustomerRepository customerDao;

	private Logger logger = Logger.getLogger(getClass());

	public Loan applyLoan(Loan loan) {
		int customerId = loan.getCustomer().getId();
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Cusotmer Not Found: " + customerId));
		customer.addLoan(loan);
		return loanDao.save(loan);
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

}
