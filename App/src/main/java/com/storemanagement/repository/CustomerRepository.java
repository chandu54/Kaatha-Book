package com.storemanagement.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.storemanagement.filter.CustomerFilter;
import com.storemanagement.filter.CustomerFilter.StringQueryOperator;
import com.storemanagement.filter.TransactionFilter.AmountOperator;
import com.storemanagement.model.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity> {

	@Query(value = "select c from CustomerEntity c where c.email=:email")
	Optional<CustomerEntity> findCustomerByEmail(@Param("email") String email);

	@Query(value = "select c from CustomerEntity c where c.unformattedPhone=:unformattedPhone")
	Optional<CustomerEntity> findCustomerByPhone(@Param("unformattedPhone") String unformattedPhone);

	static Specification<CustomerEntity> getSpecificationFromFilter(CustomerFilter customerFilter) {
		Specification<CustomerEntity> sepcification = getDateSpecification(customerFilter.getFromDate(),
				customerFilter.getToDate());

		if (customerFilter.getAddress() != null) {
			sepcification = sepcification.and(getStringTypeFieldSpecification("address", customerFilter.getAddress(),
					customerFilter.getAddressOperator()));
		}

		if (customerFilter.getCategory() != null) {
			sepcification = sepcification.and(getStringTypeFieldSpecification("category", customerFilter.getCategory(),
					customerFilter.getCategoryOperator()));
		}

		if (customerFilter.getEmail() != null) {
			sepcification = sepcification.and(getStringTypeFieldSpecification("email", customerFilter.getEmail(),
					customerFilter.getEmailOperator()));
		}
		if (customerFilter.getName() != null) {
			sepcification = sepcification.and(getStringTypeFieldSpecification("name", customerFilter.getName(),
					customerFilter.getNameOperator()));
		}

		if (customerFilter.getUnformattedPhone() != null) {
			sepcification = sepcification.and(getStringTypeFieldSpecification("unformattedPhone",
					customerFilter.getUnformattedPhone(), customerFilter.getPhoneNoOperator()));
		}

		if (customerFilter.getOutStandingAmount() != null) {
			sepcification = sepcification.and(getOutStandingAmountSpecification(customerFilter.getOutStandingAmount(),
					customerFilter.getAmountOperator()));
		}
		return sepcification;
	}

	static Specification<CustomerEntity> getOutStandingAmountSpecification(Long amount, AmountOperator amountOperator) {
		switch (amountOperator) {
		case EQUALS:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder
					.equal(customer.get("outStandingAmount"), amount);
		case GREATER_THAN:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.gt(customer.get("outStandingAmount"),
					amount);
		case GREATER_THAN_OR_EQUAL_TO:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(customer.get("outStandingAmount"),
					amount);
		case LESS_THAN:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lt(customer.get("outStandingAmount"),
					amount);
		case LESS_THAN_OR_EQUAL_TO:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(customer.get("outStandingAmount"),
					amount);
		default:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder
					.equal(customer.get("outStandingAmount"), amount);
		}
	}

	static Specification<CustomerEntity> getStringTypeFieldSpecification(String fieldName, String value,
			StringQueryOperator stringOperator) {
		switch (stringOperator) {
		case EQUALS:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(customer.get(fieldName), value);
		case LIKE:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(customer.get(fieldName),
					"%" + value + "%");
		case STARTS_WITH:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(customer.get(fieldName),
					value + "%");
		case ENDS_WITH:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(customer.get(fieldName),
					"%" + value);
		default:
			return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(customer.get(fieldName), value);
		}
	}

	static Specification<CustomerEntity> getDateSpecification(Date fromDate, Date toDate) {
		return (customer, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(customer.get("insertedDate"),
				fromDate, toDate);
	}

}
