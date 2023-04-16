package com.storemanagement.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "customer")
@JsonInclude(Include.NON_NULL)
@javax.persistence.Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerEntity extends BaseEntity{

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email", unique = true)
	@NotNull
	private String email;

	@Column(name = "phone")
	@NotNull
	private String phoneNo;

	@Column(name = "address")
	private String address;

	@Column(name = "category")
	private String category;

	@Column(name = "outstanding_amount")
	private long outStandingAmount;
	
	@Column(name ="is_active")
	private Boolean isActive = true;
	
	@Column(name = "inserted_date")
	@CreationTimestamp
	private Date insertedDate;
	
	@Column(name = "updated_date")
	@UpdateTimestamp
	private Date updatedDate;
	
	@Column(name ="unformatted_phone", unique = true)
	@NotNull
	private String unformattedPhone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public long getOutStandingAmount() {
		return outStandingAmount;
	}

	public void setOutStandingAmount(long outStandingAmount) {
		this.outStandingAmount = outStandingAmount;
	}

	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getInsertedDate() {
		return insertedDate;
	}

	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
		if (phone != null) {
			unformattedPhone = phone.replaceAll("[^a-zA-Z0-9]", "");
			this.unformattedPhone = unformattedPhone;
		}
	}
	
}
