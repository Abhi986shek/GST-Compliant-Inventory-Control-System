package com.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import com.login.model.Product;*/
import com.login.repository.ProductRepository;

@Service
public class productService {
	
	@Autowired
	ProductRepository productRepository;

	public boolean deleteItemById(String itemId) {
		productRepository.deleteById(itemId);
		return true;
		
	}
/*
	public boolean deleteItemByid(Product userid) {
		productRepository.delete(userid);
		return true;
	}*/

	public List<String> findItemId() {
		
		return productRepository.findItemId();
	}

	public String gethsn(String itemid) {
		// TODO Auto-generated method stub
		return productRepository.gethsn(itemid);
	}
	
	
		
	}

