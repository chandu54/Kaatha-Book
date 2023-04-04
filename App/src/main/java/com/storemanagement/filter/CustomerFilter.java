package com.storemanagement.filter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.storemanagement.filter.TransactionFilter.AmountOperator;

public class CustomerFilter {

	private String name;

	private String email;

	private String phoneNo;

	private String address;

	private String category;

	private Long outStandingAmount;
	
	private Boolean isActive;
	
	private Date fromDate;
	
	private Date toDate;
	
	private AmountOperator amountOperator;
	
	private StringQueryOperator nameOperator;

	private StringQueryOperator emailOperator;

	private StringQueryOperator phoneNoOperator;

	private StringQueryOperator addressOperator;

	private StringQueryOperator categoryOperator;
	
	
	@JsonIgnore
	private String unformattedPhone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getOutStandingAmount() {
		return outStandingAmount;
	}

	public void setOutStandingAmount(Long outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUnformattedPhone() {
		return unformattedPhone;
	}

	public void setUnformattedPhone(String unformattedPhone) {
		String phone = getPhoneNo();
		unformattedPhone = phone.replaceAll("[^a-zA-Z0-9]", "");
		this.unformattedPhone = unformattedPhone;
	}

	public AmountOperator getAmountOperator() {
		return amountOperator;
	}

	public void setAmountOperator(AmountOperator amountOperator) {
		this.amountOperator = amountOperator;
	}
	
	public StringQueryOperator getNameOperator() {
		return nameOperator;
	}

	public void setNameOperator(StringQueryOperator nameOperator) {
		this.nameOperator = nameOperator;
	}

	public StringQueryOperator getEmailOperator() {
		return emailOperator;
	}

	public void setEmailOperator(StringQueryOperator emailOperator) {
		this.emailOperator = emailOperator;
	}

	public StringQueryOperator getPhoneNoOperator() {
		return phoneNoOperator;
	}

	public void setPhoneNoOperator(StringQueryOperator phoneNoOperator) {
		this.phoneNoOperator = phoneNoOperator;
	}

	public StringQueryOperator getAddressOperator() {
		return addressOperator;
	}

	public void setAddressOperator(StringQueryOperator addressOperator) {
		this.addressOperator = addressOperator;
	}

	public StringQueryOperator getCategoryOperator() {
		return categoryOperator;
	}

	public void setCategoryOperator(StringQueryOperator categoryOperator) {
		this.categoryOperator = categoryOperator;
	}



	public enum StringQueryOperator {
		EQUALS, LIKE, STARTS_WITH, ENDS_WITH;
	}
	
}
