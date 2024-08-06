package com.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {

	@Id
	private String itemId;
	
	private String itemName;
	private String itemMaker;
	private String hsnCode;
	private String category;
	private String subCategory;
	
	public Product() {}

	public Product(String itemId, String itemName, String itemMaker, String hsnCode, String category, String subCategory) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemMaker = itemMaker;
		this.hsnCode = hsnCode;
		this.category=category;
		this.subCategory=subCategory;
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemMaker() {
		return itemMaker;
	}

	public void setItemMaker(String itemMaker) {
		this.itemMaker = itemMaker;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	
	
}

