package com.storemanagement.filter;

import java.util.Date;

import com.storemanagement.model.enums.TransactionType;

public class TransactionFilter {

	private Long customerId;
	private TransactionType type;
	private Date fromDate;
	private Date toDate;
	private Long amount;
	private AmountOperator amountOperator;


	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public AmountOperator getAmountOperator() {
		return amountOperator;
	}

	public void setAmountOperator(AmountOperator amountOperator) {
		this.amountOperator = amountOperator;
	}
	
	public enum AmountOperator {
		EQUALS("="), GREATER_THAN(">"), GREATER_THAN_OR_EQUAL_TO(">="), LESS_THAN("<"), LESS_THAN_OR_EQUAL_TO("<=");

		private String type;

		AmountOperator(String type) {
			this.setType(type);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

}
