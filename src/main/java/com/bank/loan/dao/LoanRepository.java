package com.bank.loan.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bank.loan.model.*;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<Loan, Integer>,JpaRepository<Loan,Integer> {

	@Query("select l from Loan l where l.id=?1")
	Customer findByCustomerId(int custId);
}
