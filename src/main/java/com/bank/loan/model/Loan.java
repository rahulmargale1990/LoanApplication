package com.bank.loan.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan")
public class Loan implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int loanId;
	private double loanAmt;
	private String loanType;
	private int duration;
	private double monthlyEMI;

	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Customer customer;

	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public Loan() {

	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public double getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getMonthlyEMI() {
		return monthlyEMI;
	}

	public void setMonthlyEMI(double monthlyEMI) {
		this.monthlyEMI = monthlyEMI;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void addTransaction(Transaction transation) {
		transation.setLoan(this);
		this.getTransactions().add(transation);
	}
}
