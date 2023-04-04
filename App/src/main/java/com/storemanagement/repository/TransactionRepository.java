package com.storemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.storemanagement.filter.TransactionFilter;
import com.storemanagement.filter.TransactionFilter.AmountOperator;
import com.storemanagement.model.TransactionEntity;
import com.storemanagement.model.enums.TransactionType;

public interface TransactionRepository
		extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {

	@Query(value = "select t from TransactionEntity t where t.customer.id=:customerId")
	List<TransactionEntity> findAllTransactionsByCustomerId(@Param("customerId") Long customerId);

	static Specification<TransactionEntity> getSpecificationFromFilter(TransactionFilter transactionFilter) {
		Specification<TransactionEntity> sepcification = getDateSpecification(transactionFilter.getFromDate(),
				transactionFilter.getToDate());

		if (transactionFilter.getCustomerId() != null) {
			sepcification = sepcification.and(getCustomerSpecification(transactionFilter.getCustomerId()));
		}

		if (transactionFilter.getType() != null) {
			sepcification = sepcification.and(getTypeSpecification(transactionFilter.getType()));
		}

		if (transactionFilter.getAmount() != null) {
			sepcification = sepcification
					.and(getAmountSpecification(transactionFilter.getAmount(), transactionFilter.getAmountOperator()));
		}
		return sepcification;
	}

	static Specification<TransactionEntity> getCustomerSpecification(Long customerId) {
		return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(transaction.get("customer").get("id"), customerId);
	}

	static Specification<TransactionEntity> getTypeSpecification(TransactionType type) {
		return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(transaction.get("type"), type);
	}

	static Specification<TransactionEntity> getAmountSpecification(Long amount, AmountOperator amountOperator) {
		switch (amountOperator) {
		case EQUALS:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(transaction.get("amount"), amount);
		case GREATER_THAN:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.gt(transaction.get("amount"), amount);
		case GREATER_THAN_OR_EQUAL_TO:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(transaction.get("amount"), amount);
		case LESS_THAN:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lt(transaction.get("amount"), amount);
		case LESS_THAN_OR_EQUAL_TO:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(transaction.get("amount"), amount);
		default:
			return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(transaction.get("amount"), amount);
		}
	}

	static Specification<TransactionEntity> getDateSpecification(Date fromDate, Date toDate) {
		return (transaction, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(transaction.get("insertedDate"),
				fromDate, toDate);
	}

}
