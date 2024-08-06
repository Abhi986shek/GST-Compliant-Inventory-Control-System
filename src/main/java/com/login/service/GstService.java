package com.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.model.GSTRate;
import com.login.model.Users;
import com.login.repository.GSTRateRepository;

@Service
public class GstService {
	
	@Autowired
	private GSTRateRepository gstRateRepository;
	
	public List<String> getAllCat(){
		
		return gstRateRepository.findDistinctCat();
	}

	public List<String> getItemNamesByCat(String itemId) {
		return gstRateRepository.findItemNamesByItemId(itemId);
	}

	public double findByItemName(String name) {
		
		return gstRateRepository.findRateByItemName(name);
	}

	public GSTRate updateRate(String itemNames, double rate) {
		
		GSTRate gstRate=gstRateRepository.findById(itemNames).orElseThrow(() -> new RuntimeException("Item not found"));
		gstRate.setRate(rate);
		return gstRateRepository.save(gstRate);
		
	}
	
	
	
	
}
