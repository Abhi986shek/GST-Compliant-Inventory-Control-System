package com.login.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class GSTRate {
	
	
	@Id
	private String item;
	private String cat;
	private Double rate;
	
	public GSTRate() {}

	public GSTRate(String cat, String item, Double rate) {
		super();
		this.cat = cat;
		this.item = item;
		this.rate = rate;
	}

	public String getCat() {
		return cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	

}
