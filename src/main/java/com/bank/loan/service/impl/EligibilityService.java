package com.bank.loan.service.impl;

import org.springframework.stereotype.Service;

import com.bank.loan.model.Customer;
import com.bank.loan.model.Loan;


@Service
public class EligibilityService {

	public boolean checkEligibility(Customer customer,Loan loan) {
		double maxlimit=calculateEligibleLoanAmount(customer,loan.getLoanType());
		if(loan.getLoanAmt()<maxlimit && customer.getCreditScore() >= 600 &&(customer.getAge()>18 || customer.getAge()<60)) {
			return true;
		}
		else
			return false;
//		else if(customer.getSalary() < 15000)
//			return false;
//		
//		else if(customer.getAge()<18 || customer.getAge()>60)
//			return false;
//		
//		else if(customer.getResidenceType().equalsIgnoreCase("rural_rented") 
//				&& customer.getSalary() < 25000)
//			return false;
//		
//		else if(customer.getResidenceType().equalsIgnoreCase("urban_rented") 
//				&& customer.getSalary() < 30000)
//			return false;
//		
//		else if(customer.getResidenceType().equalsIgnoreCase("rural_owned") 
//				&& customer.getSalary() < 20000)
//			return false;
		
		
		
	}
	public double calculateEligibleLoanAmount(Customer customer,String loanType) {
        double baseEligibilityAmount = 0;

        // Factor: Salary
        
        
         if(loanType.equalsIgnoreCase("HomeLoan")) {
        	
            if (customer.getSalary() >= 50000) {
            	baseEligibilityAmount += customer.getSalary() * 30; // 30x salary
            } else {
            	baseEligibilityAmount += customer.getSalary() * 15; // 15x salary
            }
            }
        
        else if(loanType.equalsIgnoreCase("VehicleLoan")) {
        	
            if (customer.getSalary() >= 50000) {
            	baseEligibilityAmount += customer.getSalary() * 10; // 20x salary
            } else {
            	baseEligibilityAmount += customer.getSalary() * 5; // 10x salary
            }
            }else {
            	//if(loanType.equalsIgnoreCase("PersonalLoan")) {            	
                if (customer.getSalary() >= 50000) {
                	baseEligibilityAmount += customer.getSalary() * 20; // 20x salary
                } else {
                	baseEligibilityAmount += customer.getSalary() * 10; // 10x salary
                }
                }
        
        
        
        // Factor: Credit Score
        if (customer.getCreditScore() >= 750) {
        	baseEligibilityAmount *= 1.2; // 20% increase for high credit score
        } else if (customer.getCreditScore() < 600) {
        	baseEligibilityAmount *= 0.8; // 20% decrease for low credit score
        }

        // Factor: Age
        if (customer.getAge() < 25) {
        	baseEligibilityAmount *= 0.8; // 20% decrease for younger customers
        } else if (customer.getAge() > 50) {
        	baseEligibilityAmount *= 0.9; // 10% decrease for older customers
        }

        // Cap the loan amount to avoid excessive risks
        double cappedAmount = Math.min(baseEligibilityAmount, 2000000); // Max cap: 20 Lakhs

        //customer.setMaximumEligibleLoanAmount(cappedAmount);
        return cappedAmount;
    }
}
