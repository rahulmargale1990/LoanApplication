package com.bank.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.loan.model.Loan;
import com.bank.loan.service.iLoanService;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {

	@Autowired(required = true)
	private iLoanService loanService;

	@PostMapping("/{id}")
	public ResponseEntity<Loan> applyLoan(@PathVariable int id, @RequestBody Loan loan) {
		return new ResponseEntity<Loan>(loanService.applyLoan(id,loan), HttpStatus.OK);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable int id) {
		return new ResponseEntity<List<Loan>>(loanService.getLoansByCustomerId(id), HttpStatus.OK);
	}

	@DeleteMapping("/foreclose/{loanId}")
	public ResponseEntity<String> forecloseLoan(@PathVariable int loanId) {
		loanService.foreCloseLoan(loanId);
		return new ResponseEntity<String>("Loan Foreclosed with Loan Id: " + loanId, HttpStatus.OK);
	}
	@GetMapping("/loaneligibility")
    public ResponseEntity<String> getLoanEligibility(@RequestParam int customerId,@RequestParam String loanType) {
		double eligibleAmount =loanService.calculateEligibleLoanAmount(customerId,loanType);
		return new ResponseEntity<String>("You are eligibile for Loan amount : " + eligibleAmount, HttpStatus.OK);
	}

}
