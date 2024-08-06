package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.login.model.Product;
import com.login.model.Stock;
import com.login.repository.CartRepository;
import com.login.repository.StockRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	 @Autowired
	 private StockRepository stockRepository;

	@Autowired
	private CartRepository cartRepo;
	
	 @PostMapping("/delete")
	    public ResponseEntity<String> deleteCartItem(@RequestParam Integer cartId) {
		 System.out.println("cart delete: "+cartId);
	        try {
	        	cartRepo.deleteById(cartId);
	            return ResponseEntity.ok("Item deleted successfully");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete item");
	        }
	    }
	 
	 
		
}
