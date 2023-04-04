package com.storemanagement.data.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.javafaker.Faker;
import com.storemanagement.dto.CustomerDTO;
import com.storemanagement.dto.TransactionDTO;
import com.storemanagement.model.CustomerEntity;
import com.storemanagement.model.TransactionEntity;
import com.storemanagement.model.enums.TransactionType;

public class DataUtils {

	public static final Faker FAKER = new Faker();

	public static List<CustomerEntity> prepareCutomersList(int size) {
		List<CustomerEntity> customers = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			CustomerEntity customer = new CustomerEntity();
			customer.setAddress(FAKER.address().fullAddress());
			customer.setName(FAKER.name().fullName());
			customer.setPhoneNo(FAKER.phoneNumber().cellPhone());
			customer.setEmail(FAKER.name().firstName() + "@gmail.com");
			customers.add(customer);
		}
		return customers;

	}

	public static List<TransactionEntity> prepareTransactionList(int size, CustomerEntity customer,
			TransactionType type) {
		List<TransactionEntity> transactions = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			TransactionEntity transaction = new TransactionEntity();
			transaction.setAmount(1000L);
			transaction.setInsertedDate(new Date());
			transaction.setCustomer(customer);
			transaction.setType(type);
			transactions.add(transaction);

		}
		return transactions;
	}

	public static CustomerDTO converCustomerModelToDto(CustomerEntity customerModel, Boolean isMinialObject) {
		CustomerDTO customerDTO = new CustomerDTO();
		if (customerModel != null) {
			customerDTO.setId(customerModel.getId());
			customerDTO.setName(customerModel.getName());
			customerDTO.setOutStandingAmount(customerModel.getOutStandingAmount());
			if (isMinialObject == null || Boolean.FALSE.equals(isMinialObject)) {
				customerDTO.setActive(customerModel.isActive());
				customerDTO.setEmail(customerModel.getEmail());
				customerDTO.setPhoneNo(customerModel.getPhoneNo());
				customerDTO.setAddress(customerModel.getAddress());
				customerDTO.setCategory(customerModel.getCategory());
				customerDTO.setInsertedDate(customerModel.getInsertedDate());
				customerDTO.setUpdatedDate(customerModel.getUpdatedDate());
			}
		}
		return customerDTO;

	}

	public static CustomerEntity converCustomeDtoToModel(CustomerDTO customerDTO) {
		CustomerEntity customerModel = new CustomerEntity();
		if (customerDTO != null) {
			customerModel.setId(customerDTO.getId());
			customerModel.setName(customerDTO.getName());
			customerModel.setActive(customerDTO.isActive());
			customerModel.setEmail(customerDTO.getEmail());
			customerModel.setPhoneNo(customerDTO.getPhoneNo());
			customerModel.setAddress(customerDTO.getAddress());
			customerModel.setCategory(customerDTO.getCategory());
			customerModel.setOutStandingAmount(customerDTO.getOutStandingAmount());
			customerModel.setInsertedDate(customerDTO.getInsertedDate());
			customerModel.setUpdatedDate(customerDTO.getUpdatedDate());
			customerModel.setUnformattedPhone(customerDTO.getPhoneNo());
		}
		return customerModel;
	}

	public static TransactionEntity converTransactionDTOToModel(TransactionDTO transactionDTO) {
		TransactionEntity transactionModel = new TransactionEntity();
		transactionModel.setAmount(transactionDTO.getAmount());
		transactionModel.setType(transactionDTO.getType());
		transactionModel.setCustomer(converCustomeDtoToModel(transactionDTO.getCustomer()));
		transactionModel.setId(transactionDTO.getId());
		transactionModel.setInsertedDate(transactionDTO.getInsertedDate());
		transactionModel.setUpdatedDate(transactionDTO.getUpdatedDate());
		return transactionModel;
	}

	public static TransactionDTO converTransactionModelToDTO(TransactionEntity transactionModel) {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setAmount(transactionModel.getAmount());
		transactionDTO.setType(transactionModel.getType());
		transactionDTO.setCustomer(converCustomerModelToDto(transactionModel.getCustomer(), Boolean.TRUE));
		transactionDTO.setId(transactionModel.getId());
		transactionDTO.setInsertedDate(transactionModel.getInsertedDate());
		transactionDTO.setUpdatedDate(transactionModel.getUpdatedDate());
		return transactionDTO;
	}
}
