package com.bank.loan.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bank.loan.model.Customer;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>,JpaRepository<Customer,Integer> {

	Page<Customer> findAll(Pageable pageable);

	@Query("select c from Customer c where c.email=?1 or c.adhaar=?2 or c.pan=?3 or c.phone=?4")
	Customer checkCustomer(String email, long adhaar, String pan, long phone);

	@Query("select c.fname from Customer c where c.email=?1 and c.password=?2")
	String findCustomerByEmailAndPassword(String email, String password);

	//Object findById(int id);

	//Customer save(Customer customer);
	//@Query("select * from Customer where customer_id=?1")
   // Customer  findById(int customerId);

}
