package com.storemanagement.model.enums;

public enum TransactionType {
	
	DEBIT("Debit"),
	CREDIT("Credit");
	private String type;
	
	TransactionType(String type){
		this.setType(type);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
