package com.login.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Sell {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sell_seq_gen")
	@SequenceGenerator(name = "sell_seq_gen", sequenceName = "sell_sequence", initialValue = 1, allocationSize = 1)
	private int sellId;
	
	private Double transanctionValue;
	private Double cgst;
	private Double sgst;
	private Double gst;
	private Double totalwithGst;
	private int invoiceNo;
	private String custName;
	private String custAddress;
	private String transDate;
	
	public Sell() {}

	public Sell(int sellId, Double transanctionValue, Double cgst, Double sgst, Double gst, Double totalwithGst,
			int invoiceNo, String custName, String custAddress, String transDate) {
		super();
		this.sellId = sellId;
		this.transanctionValue = transanctionValue;
		this.cgst = cgst;
		this.sgst = sgst;
		this.gst = gst;
		this.totalwithGst = totalwithGst;
		this.invoiceNo = invoiceNo;
		this.custName = custName;
		this.custAddress = custAddress;
		this.transDate = transDate;
	}

	public int getSellId() {
		return sellId;
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

	public int getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(int invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	
	
	
	
	

}
