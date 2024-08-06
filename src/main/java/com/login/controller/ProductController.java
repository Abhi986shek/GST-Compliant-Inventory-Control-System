package com.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.http.HttpStatus;*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.login.model.GSTRate;
import com.login.model.Product;
import com.login.repository.GSTRateRepository;
import com.login.repository.ProductRepository;
/*import org.springframework.web.servlet.ModelAndView;*/
import com.login.repository.StockRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private productService productService;
	
	@Autowired
	private GSTRateRepository gstRateRepo;
	 @Autowired
	 private StockRepository stockRepository;

	@PostMapping("/input_product")
	public String loginSubmit(@RequestParam String itemid, @RequestParam String itemname, @RequestParam String maker,
			@RequestParam String hsn, @RequestParam String category, @RequestParam String sub_cat, @RequestParam Double gstrate) {
		try {
			// Create a Product object and save it to the database
			Product product = new Product();
			product.setItemId(itemid);
			product.setItemName(itemname);
			product.setItemMaker(maker);
			product.setHsnCode(hsn);
			product.setCategory(category);
			product.setSubCategory(sub_cat);
			// Set category and sub-category as needed
			productRepository.save(product);
			
			//crate gstrate
			GSTRate gstRate = new GSTRate();
			gstRate.setCat(category);
			gstRate.setItem(itemname);
			gstRate.setRate(gstrate);
			gstRateRepo.save(gstRate);
			
			return "redirect:/Stockistmenu"; // Assuming Stockistmenu is a view name
			
			
		} catch (Exception e) {
			e.printStackTrace(); // Log the exception for debugging
			return "redirect:/error"; // Redirect to an error page or handle the exception gracefully
		}
	}
	
    @GetMapping("/validate_itemid")
    public ResponseEntity<Map<String, Boolean>> validateItemId(@RequestParam String itemid) {
        boolean exists = productRepository.existsById(itemid);
        System.out.println("validate item iD: "+exists);
        Map<String, Boolean> response = new HashMap<>();
        System.out.println("Response : "+response);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/validate_itemname")
    public ResponseEntity<Map<String, Boolean>> validateItemName(@RequestParam String itemName) {
        boolean exists = productRepository.existsByItemname(itemName);
        System.out.println("validate item Name: "+exists);
        Map<String, Boolean> response = new HashMap<>();
        System.out.println("Response : "+response);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

	@GetMapping("/getItem")
	public ResponseEntity<Product> searchItem(@RequestParam String id) {
		Product product = productRepository.findByItemId(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/removeItem/{itemId}/{itemName}")
	public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable String itemId,
			@PathVariable String itemName) {

		System.out.println("delete by ID: " + itemId);
		boolean deleted = productService.deleteItemById(itemId);
		int deletedCount = stockRepository.deleteByItemId(itemId);
		int deletegst = gstRateRepo.deleteGst(itemName);
		// productRepository.deleteById(itemId);
		Map<String, Object> response = new HashMap<>();
		response.put("success", deleted);
		return ResponseEntity.ok(response);

	}
	
	@GetMapping("/fetch-item-id")
    public ResponseEntity<List<String>> getItemId() {
        List<String> itemId = productService.findItemId();
        
        System.out.println("Item Id : " + itemId);
        return ResponseEntity.ok(itemId);
    }
	

	   @GetMapping("/find_sub_cat")
	    public ResponseEntity<List<String>> fetchSubCategories(@RequestParam String category) {
	        List<String> subCategories = productRepository.findSubCatByCat(category);
	        System.out.println("Sub Categories: " + subCategories);
	        return ResponseEntity.ok(subCategories);
	    }
	
}
