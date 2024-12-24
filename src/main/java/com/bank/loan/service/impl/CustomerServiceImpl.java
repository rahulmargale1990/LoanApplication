package com.bank.loan.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bank.loan.dao.CustomerRepository;
import com.bank.loan.exception.CustomerAlreadyRegisteredException;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.service.iCustomerService;

@Service
@Primary
public class CustomerServiceImpl implements iCustomerService {

	@Autowired
	private CustomerRepository customerDao;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Customer addCustomer(Customer c) {
		Customer customer = customerDao.checkCustomer(c.getEmail(), c.getAdhaar(), c.getPan(), c.getPhone());
		if (customer != null) {
			throw new CustomerAlreadyRegisteredException("Customer Already Registered: " + customer.getId());
		}
		return customerDao.save(c);
	}

	@Override
	public String doLogin(String email, String password) {
		String customeruser = null;
		try {
			customeruser = customerDao.findCustomerByEmailAndPassword(email, password);
			logger.info("Customer: " + customeruser + " Logged In Successfully");
			if(customeruser.isEmpty()) {
				return "Incorrect Username or Password";
			}else {
				return customeruser+" is successfully Login";
				
			}
		} catch (Exception e) {
			throw new CustomerNotFoundException("Customer Not Found or Invalid Credentials ");
		}
	}

	public Customer updateCustomer(Customer c) {
		Customer customer = customerDao.findById(c.getId())
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + c.getId()));
		BeanUtils.copyProperties(c, customer);
		return customerDao.save(customer);
	}

	@Override
	public List<Customer> getCustomers(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return customerDao.findAll(pageable).toList();
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
		logger.info("Customer Found: " + customerId);
		return customer;
	}

}
