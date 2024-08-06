package com.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vendor {

	@Id
	private String vendorName;
	private String vendorContact;
	private Long mobileNo;
	private String emailId;
	private String address;
	private Long pinCode;
	private String gstIn;
	
	public Vendor() {}

	public Vendor(String vendorName, String vendorContact, Long mobileNo, String emailId, String address, Long pinCode,
			String gstIn) {
		super();
		this.vendorName = vendorName;
		this.vendorContact = vendorContact;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.address = address;
		this.pinCode = pinCode;
		this.gstIn = gstIn;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorContact() {
		return vendorContact;
	}

	public void setVendorContact(String vendorContact) {
		this.vendorContact = vendorContact;
	}

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPinCode() {
		return pinCode;
	}

	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}
	
	
}
