package com.login.controller;


import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*import java.util.Date;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.login.model.Cart;
import com.login.model.Stock;
import com.login.repository.CartRepository;
import com.login.repository.GSTRateRepository;
import com.login.repository.StockRepository;

@RestController
public class StockController {
	 @Autowired
	 private StockRepository stockRepository;
	 
	 @Autowired
		private productService productService;
	 
	 @Autowired
	 private GSTRateRepository gstRateRepository;
	 
	 @Autowired
	 private CartRepository cartRepo;


	    @PostMapping("/addQty")
	    public String loginSubmit(@RequestParam String itemid,
	    						  @RequestParam String itemName,
	                              @RequestParam String batchno,
	                              @RequestParam String mfd,
	                              @RequestParam String exp,
	                              @RequestParam String vendorName,
	                              @RequestParam Double qty,
	                              @RequestParam Double bprice,
	                              @RequestParam Double sprice,
	                              Model model) {
	    	
	    	System.out.println("VendorName: "+vendorName);
	    		    		    	
	    	Stock stock=new Stock();
	    	stock.setItemId(itemid);
	    	stock.setItemName(itemName);
	    	stock.setBatchNo(batchno);
	    	
	        String mfddate = LocalDate.parse(mfd).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    	stock.setMfDate(mfddate);
	    	
	        String expdate = LocalDate.parse(exp).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    	stock.setExpDate(expdate);
	    	stock.setVendorId(vendorName);
	    	stock.setQuantity(qty);
	    	stock.setBuyPrice(bprice);
	    	stock.setSellPrice(sprice);
	    	
	    	//check if the input batchNo exists in stock table
	    	String batchno1 = stockRepository.getBatchNo(itemid,batchno);
	    	System.out.println("Batch No exists or not : "+batchno1);
	    	//if exists then delete the entry from stock table (itemid, batchno)
	    	if(batchno1 != null) {
	    		stockRepository.deleteAllByIdInBatch(itemid,batchno1);
	    	}
	    	//Save the stock
	    	stockRepository.save(stock);
	    	return "Stockistmenu";
	    	
	    }
	    
	    @GetMapping("/fetch-batch-no/{itemId}")
	    public ResponseEntity<List<String>> fetchBatchNo(@PathVariable String itemId) {
	        List<String> batchNos = stockRepository.findBatchByItemId(itemId);
	        System.out.println("Batch Nos : " + batchNos);
	        return ResponseEntity.ok(batchNos);
	    }
	    
	    @GetMapping("/fetchQty")
	    public ResponseEntity<Double> fetchQty(@RequestParam String itemid, @RequestParam String batchno) {
	    	 Object result = stockRepository.findQuantityByItemIdAndBatchNo(itemid, batchno);
	        // Implement logic to fetch quantity based on itemid and batchno from service
	    	System.out.println("Fetch quantity inputs: "+itemid+", "+batchno);
	    	System.out.println("Fetch quantity : "+(Double) result);
	    	
	    	 if (result instanceof Double) {
	    		 return ResponseEntity.ok((Double) result);
	    	    } else {
	    	        // Handle unexpected result
	    	        throw new IllegalStateException("Unexpected result type");
	    	    }
	    }
	    
	    
	    @PostMapping("/validateQuantity")
	    public ResponseEntity<String> validateQuantity(@RequestParam String itemid, @RequestParam String batchno, @RequestParam Double qty, 
	    		@RequestParam String itemName, @RequestParam String usernameField) {
	    	Double fetchedQty1 = cartRepo.findMatch(usernameField,itemid,batchno);
	    	if(fetchedQty1 == null) {
	    		fetchedQty1 = (double) 0;
	    	}
	        Object availableQuantity =  stockRepository.findQuantityByItemIdAndBatchNo(itemid, batchno);
	        if (qty + fetchedQty1 <= (Double) availableQuantity) {
	        	System.out.println("Quantity Validation getQuantity and available Quantity: "+qty+" , "+(Double)availableQuantity);
	        	
	        	Cart cart = new Cart();
	        	Double fetchedQty = cartRepo.findMatch(usernameField,itemid,batchno);
	        	System.out.println("Quantity fetched in cart table for" +usernameField +","+ itemid+","+ batchno +", "+fetchedQty);
	        	if(fetchedQty != null){
	        		Double Qty1 = fetchedQty + qty;
	        		//fetch sell value from stock
	    	        Object sellValue = stockRepository.getSellValue(itemid, batchno);
	    	        cart.setSellPrice((Double)sellValue);
	        		Double totaltrans = (Qty1)* cart.getSellPrice();
	        		 Double gst = gstRateRepository.findRateByItemName(itemName);
	        		Double totalWithgst = (totaltrans)*(1+(gst/100));
	        		Double gstAmount1 = (totalWithgst) - (totaltrans);
	        		Double gstcg = ((gstAmount1)/2) ;
	        		cartRepo.updateQty(usernameField,itemid,batchno,Qty1,totaltrans,totalWithgst,gstAmount1,gstcg);
	        		return ResponseEntity.ok("valid");
	        	}
	        	else {
	        	//fetch hsncode
	        	String hsncode = productService.gethsn(itemid);
	        	System.out.println("Fetched hsncode: "+ hsncode);
	        	
	        	//fetch sell value from stock
	        Object sellValue = stockRepository.getSellValue(itemid, batchno);
	        cart.setSellPrice((Double)sellValue);
	        // fetch GST rate from GSTRate
	        Double gst = gstRateRepository.findRateByItemName(itemName);
	      
        	//Calculate total Transaction Value
        	Double totalTranValue = (qty)*(cart.getSellPrice());
        	cart.setTransanctionValue(totalTranValue);
        	
        	// Calculate total transaction value with gst
        	Double totalWithGST = (totalTranValue)*(1+(gst/100));
        	
        	// calculate GST amount
        	Double gstAmount = (totalWithGST) - (totalTranValue);
        	
        	//calculate cgst,sgst
        	Double csgst = ((gstAmount)/2) ;
	        	
	        	
	        	cart.setItemId(itemid);
	        	cart.setItemName(itemName);
	        	cart.setBatchNo(batchno);
	        	cart.setQuantity(qty);
	        	cart.setHsnCode(hsncode);
	        	
	        	cart.setGstRate(gst);    	
	        	cart.setTotalwithGst(totalWithGST);
	        	cart.setGst(gstAmount);
	        	cart.setCgst(csgst);
	        	cart.setSgst(csgst);
	        	cart.setUserName(usernameField);
	        	cartRepo.save(cart);
	        	
	        	
	        	
	        	
	        	System.out.println("Fetched sellValue: "+ cart.getSellPrice());
	        	System.out.println("calculated totaltranvalue : "+totalTranValue);
	        	System.out.println("fetched gst : "+cart.getGstRate());
	        	System.out.println("calculated totaltranvalue With GST : "+totalWithGST);
	        	System.out.println("calculated GST : "+gstAmount);
	        	System.out.println("calculated CGST, SGST : "+csgst);
	        	
	        	
	        	return ResponseEntity.ok("valid");
	        	}
	        	
	        }
	        else {
	            return ResponseEntity.ok("Max available quantity is: " + availableQuantity +"\n"+"Item Quantity Added in Cart: "
	        +fetchedQty1 + "\n" + "Available Quantity to add in cart : " + ((Double) (availableQuantity)-(fetchedQty1)));
	        }
	    }
	    
	    @GetMapping("/fetchCartItems/{username}")
	    @ResponseBody
	    public List<Cart> fetchCartItems(@PathVariable String username) {
	    	System.out.println("Username in fetch Cart: " +username);
	        List<Cart> cartItems = cartRepo.getCartItemsByUsername(username); // Fetch all items from cart
	        return cartItems;
	    }
	    
	    @GetMapping("/getBatch")
		public ResponseEntity<Stock> searchItem(@RequestParam String itemid,@RequestParam String batchno) {
			System.out.println("calling getBatch");
			Stock stock = stockRepository.findByBatchNo(itemid,batchno);
			if (stock != null) {
				return ResponseEntity.ok(stock);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
	    
		@GetMapping("/fetchExp")
		public ResponseEntity<List<Stock>> searchItem(@RequestParam String date) {
			System.out.println("Input Expiry date: " + date);

	        // Convert input date to 'YYYY-MM-DD 00:00:00' format
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
	        LocalDateTime startOfDay = parsedDate.atStartOfDay(); // Start of the day

	        String formattedDate = startOfDay.format(outputFormatter);
	        System.out.println("Formatted Date: " + formattedDate);
			/*
			 * Product product = productRepository.findByItemId(id); if (product != null) {
			 * return ResponseEntity.ok(product); } else { return
			 * ResponseEntity.notFound().build(); }
			 */
	        List<Stock> stock = stockRepository.findByExpiry(formattedDate);
	        if (stock != null) {
	        	return ResponseEntity.ok(stock);
	        }
	        else {
	        	return ResponseEntity.notFound().build();
	        }
			
		}
		
	    @DeleteMapping("/removeExp")
	    public ResponseEntity<?> removeItemsByExpiry(@RequestParam String date) {
	        // Convert input date to 'YYYY-MM-DD 00:00:00' format
	        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
	        LocalDateTime startOfDay = parsedDate.atStartOfDay(); // Start of the day

	        String formattedDate = startOfDay.format(outputFormatter);
	        int deletedCount = stockRepository.deleteByExpiry(formattedDate);
	        if (deletedCount > 0) {
	        	return ResponseEntity.ok("valid");
	        } else {
	            return ResponseEntity.ok("Not able to Remove");
	        }
	    }
	    
	    // Response class for success messages
	    public static class SuccessResponse {
	        private boolean success;

	        public SuccessResponse(boolean success) {
	            this.success = success;
	        }

	        public boolean isSuccess() {
	            return success;
	        }

	        public void setSuccess(boolean success) {
	            this.success = success;
	        }
	    }
	
	    
		/*
		 * @PostMapping("/addCart") public ResponseEntity<String> addToCart(@RequestBody
		 * Stock cartItemForm) { // Validate quantity here against available stock
		 * quantity Object availableQuantity =
		 * stockRepository.findQuantityByItemIdAndBatchNo(cartItemForm.getItemId(),
		 * cartItemForm.getBatchNo());
		 * System.out.println("Quantity Validation getQuantity and available Quantity: "
		 * +cartItemForm.getQuantity()+" , "+(Double)availableQuantity);
		 * 
		 * if (cartItemForm.getQuantity() > (Double)availableQuantity) { return
		 * ResponseEntity.badRequest().body("Quantity exceeds available stock."); }
		 * 
		 * // Proceed with adding to cart logic // Example:
		 * cartService.addToCart(cartItemForm);
		 * 
		 * return ResponseEntity.ok("Item added to cart successfully."); }
		 */
	    
	    @GetMapping("/cashierMenu")
	    public String cashierMenu(Model model, Principal principal) {
	        model.addAttribute("username", principal.getName());
	        return "cashierMenu";
	    }
		    
		    
		    @GetMapping("/addToCart")
		    public String addToCart(Model model, Principal principal) {
		        String username = principal.getName();
		        model.addAttribute("username", username);
		        return "addToCart";
		    }
}
