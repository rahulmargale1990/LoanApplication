package com.bank.loan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.loan.model.Payment;
import com.bank.loan.service.PaymentService;



@RestController
@RequestMapping("/payments")
public class PaymentController {

	
	
	@Autowired
	PaymentService paymentService;
	
	@PostMapping("/makepayment/{loanId}")
    public ResponseEntity<Payment> makePayment(@PathVariable int loanId, @RequestBody Payment payment) {
		return new ResponseEntity<Payment>(paymentService.addPayment(loanId,payment), HttpStatus.OK);        
    }

    @GetMapping("/{loanId}")
    public List<Payment> getPaymentsByCustomer(@PathVariable Integer loanId) {
        return paymentService.getPaymentByLoanId(loanId);
    }

    @PostMapping("/close-loan/{loanId}")
    public ResponseEntity<String> closeLoan(@PathVariable Integer loanId) {
        boolean isClosed = paymentService.closeLoan(loanId);
        if (isClosed) {
            return ResponseEntity.ok("Loan successfully closed.");
        } else {
            return ResponseEntity.badRequest().body("Unable to close loan. Outstanding balance remains.");
        }
    } 
}
