package com.storemanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.storemanagement.exception.AlreadyExistsException;
import com.storemanagement.exception.NoDataFoundException;
import com.storemanagement.filter.CustomerFilter;
import com.storemanagement.model.CustomerEntity;
import com.storemanagement.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	public CustomerRepository customerReposiroty;
	
	@Autowired
	private TransactionService transactionService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	public CustomerEntity getCustomer(Long id) throws NoDataFoundException {
		// TODO Need to look cache hook
		return customerReposiroty.findById(id).orElseThrow(() -> {
			logger.debug("No Customer Found with id: {}", id);
			return new NoDataFoundException("No Customer Found with id: " + id);
		});
	}
	
	public CustomerEntity getCustomerByEmail(String email) throws NoDataFoundException {
		return customerReposiroty.findCustomerByEmail(email).orElseThrow(() -> {
			logger.debug("No Customer Found with email: {}", email);
			return new NoDataFoundException("No Customer Found with email: " + email);
		});
	}
	
	public CustomerEntity getCustomerByPhone(String phone) throws NoDataFoundException {
		if (phone != null) {
			String unformattedPhone = phone.replaceAll("[^a-zA-Z0-9]", "");
			return customerReposiroty.findCustomerByPhone(unformattedPhone).orElseThrow(() -> {
				logger.debug("No Customer Found with phone: {}", phone);
				return new NoDataFoundException("No Customer Found with phone: " + phone);
			});
		}
		throw new NoDataFoundException("No Customer Found with phone: " + phone);
	}

	public CustomerEntity createCustomer(CustomerEntity customer) throws AlreadyExistsException{
		CustomerEntity customerEntity = null;
		try {
			customerEntity =  customerReposiroty.save(customer);
		} catch (DataIntegrityViolationException exception) {
			throw new AlreadyExistsException("Customer already exists with email or phone.");
		}
		return customerEntity;
	}

	public CustomerEntity updateCustomer(CustomerEntity customer) throws NoDataFoundException, AlreadyExistsException{
		CustomerEntity customerEntity = getCustomer(customer.getId());
		try {
			customerEntity =  customerReposiroty.save(customer);
		} catch (DataIntegrityViolationException e) {
			throw new AlreadyExistsException("Customer with phone or email is already exists" );
		}
		return customerEntity;
	}

	public void deleteCustomer(Long id) throws NoDataFoundException {
		CustomerEntity customerEntity = getCustomer(id);
		transactionService.deleteTransactionsByCustomer(customerEntity);
		customerReposiroty.delete(customerEntity);
	}
	
	public Page<CustomerEntity> getAllCustomers(Pageable pageRequest) {
		return customerReposiroty.findAll(pageRequest);
	}

	public Page<CustomerEntity> getCustomersByFilter(CustomerFilter customerFilter, PageRequest pageRequest) {
		return customerReposiroty.findAll(CustomerRepository.getSpecificationFromFilter(customerFilter), pageRequest);
	}


}
