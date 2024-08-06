package com.login.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cart_seq_gen")
	@SequenceGenerator(name = "cart_seq_gen", sequenceName = "cart_sequence", initialValue = 1, allocationSize = 1)
	private int cartId;
	
	private String batchNo;
	private Double quantity;
	private String itemId;
	private String userName;
	private String itemName;
	private String hsnCode;
	private Double transanctionValue;
	private Double cgst;
	private Double sgst;
	private Double gst;
	private Double totalwithGst;
	private Double sellPrice;
	private Double gstRate; 
	
	public Cart() {}

	public Cart(String batchNo, Double quantity, String itemId, String hsnCode, Double transanctionValue, Double cgst,
			Double sgst, Double gst, Double totalwithGst, Double sellPrice, Double gstRate, String itemName, String userName) {
		super();
		this.batchNo = batchNo;
		this.quantity = quantity;
		this.itemId = itemId;
		this.hsnCode = hsnCode;
		this.transanctionValue = transanctionValue;
		this.cgst = cgst;
		this.sgst = sgst;
		this.gst = gst;
		this.totalwithGst = totalwithGst;
		this.sellPrice = sellPrice;
		this.gstRate = gstRate;
		this.itemName = itemName;
		this.userName = userName;
	}

	
	public int getCartId() {
		return cartId;
	}


	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getTransanctionValue() {
		return transanctionValue;
	}

	public void setTransanctionValue(Double transanctionValue) {
		this.transanctionValue = transanctionValue;
	}

	public Double getCgst() {
		return cgst;
	}

	public void setCgst(Double cgst) {
		this.cgst = cgst;
	}

	public Double getSgst() {
		return sgst;
	}

	public void setSgst(Double sgst) {
		this.sgst = sgst;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public Double getTotalwithGst() {
		return totalwithGst;
	}

	public void setTotalwithGst(Double totalwithGst) {
		this.totalwithGst = totalwithGst;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getGstRate() {
		return gstRate;
	}

	public void setGstRate(Double gstRate) {
		this.gstRate = gstRate;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
