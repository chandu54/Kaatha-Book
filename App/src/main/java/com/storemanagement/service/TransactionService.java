package com.storemanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.storemanagement.exception.NoDataFoundException;
import com.storemanagement.filter.TransactionFilter;
import com.storemanagement.model.CustomerEntity;
import com.storemanagement.model.TransactionEntity;
import com.storemanagement.model.enums.TransactionType;
import com.storemanagement.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	public TransactionRepository transactionRepository;

	@Autowired
	public CustomerService customerService;
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	public TransactionEntity getTransaction(Long id) throws NoDataFoundException{
		return transactionRepository.findById(id).orElseThrow(() -> {
			logger.debug("No Transaction Found with id: {}", id);
			return new NoDataFoundException("No Transaction Found with id: " + id);
		});

	}

	public TransactionEntity createTransaction(TransactionEntity transaction) throws NoDataFoundException {
		CustomerEntity customer = customerService.getCustomer(transaction.getCustomer().getId());
		if (transaction.getType().equals(TransactionType.CREDIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() - transaction.getAmount());
		} else if (transaction.getType().equals(TransactionType.DEBIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() + transaction.getAmount());
		}
		customerService.updateCustomer(customer);
		return transactionRepository.save(transaction);
	}

	public TransactionEntity updateTransaction(TransactionEntity transaction) throws NoDataFoundException{
		TransactionEntity existing = this.getTransaction(transaction.getId());
		transaction.setCustomer(existing.getCustomer());
		if (existing.getType() != transaction.getType() && existing.getAmount() != transaction.getAmount()) {
			return updateTransactionTypeAndAmount(transaction, existing);
		} else if (existing.getType() != transaction.getType()) {
			return updateTransactionType(transaction);
		} else if (existing.getAmount() != transaction.getAmount()) {
			return updateTransactionAmount(transaction, existing);
		} else {
			return transactionRepository.save(transaction);
		}
	}

	public TransactionEntity updateTransactionType(TransactionEntity transaction) {
		CustomerEntity customer = transaction.getCustomer();
		if (transaction.getType().equals(TransactionType.CREDIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() - (2 * transaction.getAmount()));
		} else if (transaction.getType().equals(TransactionType.DEBIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() + (2 * transaction.getAmount()));
		}
		customerService.updateCustomer(customer);
		return transactionRepository.save(transaction);
	}

	
	private TransactionEntity updateTransactionTypeAndAmount(TransactionEntity cuurent, TransactionEntity existing) {
		CustomerEntity customer = cuurent.getCustomer();
		if (cuurent.getType().equals(TransactionType.CREDIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() - existing.getAmount());
			customer.setOutStandingAmount(customer.getOutStandingAmount() - cuurent.getAmount());
		} else if (cuurent.getType().equals(TransactionType.DEBIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() + existing.getAmount());
			customer.setOutStandingAmount(customer.getOutStandingAmount() + cuurent.getAmount());
		}
		customerService.updateCustomer(customer);
		return transactionRepository.save(cuurent);

	}

	public TransactionEntity updateTransactionAmount(TransactionEntity cuurent, TransactionEntity existing) {
		CustomerEntity customer = cuurent.getCustomer();
		if (cuurent.getType().equals(TransactionType.CREDIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() + existing.getAmount());
			customer.setOutStandingAmount(customer.getOutStandingAmount() - cuurent.getAmount());
		} else if (cuurent.getType().equals(TransactionType.DEBIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() - existing.getAmount());
			customer.setOutStandingAmount(customer.getOutStandingAmount() + cuurent.getAmount());
		}
		customerService.updateCustomer(customer);
		return transactionRepository.save(cuurent);
	}

	public void deleteTransaction(Long id) {
		TransactionEntity existing = this.getTransaction(id);
		CustomerEntity customer = existing.getCustomer();
		if (existing.getType().equals(TransactionType.CREDIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() + existing.getAmount());
		} else if (existing.getType().equals(TransactionType.DEBIT)) {
			customer.setOutStandingAmount(customer.getOutStandingAmount() - existing.getAmount());
		}
		transactionRepository.delete(existing);
		customerService.updateCustomer(customer);
	}

	public void deleteTransactionsByCustomer(CustomerEntity customer) throws NoDataFoundException {
		List<TransactionEntity> transactionsByCustomer = getAllTransactionsByCustomer(customer.getId());
		transactionRepository.deleteInBatch(transactionsByCustomer);
	}

	public Page<TransactionEntity> getAllTransactions(Pageable pageRequest) {
		return transactionRepository.findAll(pageRequest);
	}

	public List<TransactionEntity> getAllTransactionsByCustomer(Long customerId) {
		return transactionRepository.findAllTransactionsByCustomerId(customerId);
	}
	
	public Page<TransactionEntity> getTransactionsByFilter(TransactionFilter transactionFilter, Pageable pageRequest) {
		//TODO Need to check cache if customer is presented or not 
		return transactionRepository.findAll(TransactionRepository.getSpecificationFromFilter(transactionFilter), pageRequest);
	}
	
}
