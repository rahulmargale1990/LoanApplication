package com.bank.loan.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bank.loan.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer>{

//	@Query("select c from Payment c where c.loan_id=?1")
//	double findPaymentTransactionLoan(int loanId);
	//@Query("select c from Payment c where c.loan_id=?1")
	//Payment findPaymentTransactionList(int loanId);
}
