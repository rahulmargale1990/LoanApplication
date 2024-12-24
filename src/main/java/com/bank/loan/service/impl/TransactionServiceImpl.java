package com.bank.loan.service.impl;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.TransactionalException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bank.loan.dao.CustomerRepository;
import com.bank.loan.dao.LoanRepository;
import com.bank.loan.dao.TransactionRepository;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.exception.LoanNotFoundException;
import com.bank.loan.exception.TransactionFailedException;
import com.bank.loan.exception.TransactionsNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;
import com.bank.loan.model.Transaction;
import com.bank.loan.service.iTransactionService;

@Service
@Primary
public class TransactionServiceImpl implements iTransactionService {

	@Autowired
	private CustomerRepository customerDao;

	@Autowired
	private LoanRepository loanDao;

	@Autowired
	private TransactionRepository transactionDao;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Transaction addTransaction(Transaction transaction) {
		int loanId = transaction.getLoan().getLoanId();
		Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
		//loan.addTransaction(transaction);
		try {
			return transactionDao.save(transaction);
		} catch (Exception e) {
			throw new TransactionFailedException("Transaction Failed for LoanId: " + loanId);
		}
	}

	@Override
	public List<Transaction> getTransactionsByCustId(int customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
		try {
			List<Transaction> transactions = transactionDao.findTransactionsByCustomerId(customerId);
			return transactions;
		} catch (Exception e) {
			throw new TransactionsNotFoundException("Transactions not Found for Customer Id: " + customerId);
		}
	}

}
