package com.bank.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

//import com.bank.loan.property.FileStorageProperties;


@SpringBootApplication
//@EnableConfigurationProperties({FileStorageProperties.class})
public class BankLoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankLoanApplication.class, args);
	}


}
