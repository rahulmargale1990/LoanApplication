package com.bank.loan.service;

import java.util.List;

import com.bank.loan.model.Transaction;

public interface iTransactionService {

	public Transaction addTransaction(Transaction trans);

	public List<Transaction> getTransactionsByCustId(int custId);
}
