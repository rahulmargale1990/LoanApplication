package com.bank.loan.service.impl;

import java.util.List;

//import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.loan.dao.LoanRepository;
import com.bank.loan.dao.PaymentRepository;
import com.bank.loan.exception.CustomerEligibilityException;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.model.Loan;
import com.bank.loan.model.Payment;
import com.bank.loan.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	
	@Autowired
	private LoanRepository loanDAO;
	
	@Autowired 
	private PaymentRepository paymentRepository;
	
	
	@Override
	public Payment addPayment(int loanId,Payment payment) {
		Loan loan = loanDAO.findById(loanId)
				.orElseThrow(() -> new CustomerNotFoundException("Loan Account Not Found for ID: " + loanId));
		loan.addPayment(payment);
		Double totalPaid= paymentRepository.findAll().stream()
				.filter(pay -> pay.LoanInfo().getLoanId()==(loanId))
				.mapToDouble(Payment::getAmountPaid).sum();
		Double loanAmount=loan.getLoanAmt();
		if(totalPaid>=loanAmount) {
			closeLoan(loanId);
			throw new CustomerEligibilityException("Payment for this Loan Account has been completed and this Loan account get closed for LoanID:  "+loanId); 
		}
		return paymentRepository.save(payment);
	}

	@Override
	public List<Payment> getPaymentByLoanId(int loanId) {
		Loan loan = loanDAO.findById(loanId)
				.orElseThrow(() -> new CustomerNotFoundException("Loan Account Not Found for ID: " + loanId));
		return loan.getPayment();//(List<Payment>) paymentRepository.findPaymentTransactionList(loanId);
				
	}

	@Override
	public Boolean closeLoan(int  loanId) {
		Loan loan = loanDAO.findById(loanId)
				.orElseThrow(() -> new CustomerNotFoundException("Loan Account Not Found for ID: " + loanId));
		Double totalPaid= paymentRepository.findAll().stream()
				.filter(payment -> payment.LoanInfo().getLoanId()==(loanId))
				.mapToDouble(Payment::getAmountPaid).sum();
		Double loanAmount=loan.getLoanAmt();
		
//		Double totalPaid = paymentRepository.findAll().stream()
//				.filter(payment -> payment.getCustomer().getCustomerId().equals(customerId))
//				.mapToDouble(Payment::getAmountPaid).sum();
//		
//		Double loanAmount = 10000.0; // 
		
		if(totalPaid>=loanAmount) {
			loan.setFlag("Closed");
			loanDAO.save(loan);
			return true;
		}
		return false;
	}

//	@Override
//	public Payment savePayment(Payment payment) {
//		return paymentRepository.save(payment);
//	}
	
}
