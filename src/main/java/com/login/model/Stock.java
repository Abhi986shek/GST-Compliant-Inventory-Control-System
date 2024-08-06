package com.login.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_seq_gen")
	@SequenceGenerator(name = "product_seq_gen", sequenceName = "product_sequence", initialValue = 1, allocationSize = 1)
	private int stockId;
	private String itemId;
	private String itemName;
	private String batchNo;
	private String MfDate;
	private String expDate;
	private String vendorId;
	private Double quantity;
	private Double buyPrice;
	private Double sellPrice;
	
	public Stock() {}

	public Stock(String itemId, String batchNo, String mfDate, String expDate, String vendorId, Double quantity,
			Double buyPrice, Double sellPrice, String itemName) {
		super();
		this.itemId = itemId;
		this.batchNo = batchNo;
		this.MfDate = mfDate;
		this.expDate = expDate;
		this.vendorId = vendorId;
		this.quantity = quantity;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMfDate() {
		return MfDate;
	}

	public void setMfDate(String mfDate) {
		MfDate = mfDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	

}
