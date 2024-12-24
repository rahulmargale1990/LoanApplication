package com.bank.loan.model;

import java.util.Date;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer paymentId;	
	private Date paymentDate;
	private Double amountPaid;
	
	private String paymentType; // monthly, quarterly, partial, full
	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;
	public Integer getPaymentId() {
		return paymentId;
	}
	public Loan LoanInfo() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public Double getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	
}
