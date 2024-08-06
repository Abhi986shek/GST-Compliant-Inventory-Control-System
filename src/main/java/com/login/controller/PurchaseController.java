package com.login.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.login.model.Cart;
import com.login.model.Invoice;
import com.login.model.Sell;
import com.login.model.Stock;
import com.login.repository.CartRepository;
import com.login.repository.InvoiceRepository;
import com.login.repository.SellRepository;
import com.login.repository.StockRepository;

@Controller
public class PurchaseController {
	
	@Autowired
	private StockRepository stockRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private SellRepository sellRepo;
	

	@PostMapping("/Purchase")
	public String purchase(@RequestParam String usernameField,@RequestParam String cname,@RequestParam String address, Model model) {
		System.out.println("From CustInfo: "+usernameField+", "+cname+", "+address);
		
		//fetch cart details using the usernameField
		List<Cart> cartItems = cartRepo.getCartItemsByUsername(usernameField);
		
		


	        Integer invoiceno = invoiceRepo.getMaxinvoiceNo();
	        if(invoiceno == null){
	        	invoiceno = 0;
	        }
	        invoiceno = invoiceno + 1;
	        System.out.println("Max Invoice No: " +invoiceno);
		// insert details into invoice table
	        for (Cart cart : cartItems) {
	        	  //Optional<Invoice> existingInvoice = invoiceRepo.findByItemIdAndBatchNo(cart.getItemId(), cart.getBatchNo());
	        	  //if (!existingInvoice.isPresent()) {
	        	//fetching quantity from stock
	        	Object availableQuantity =  stockRepo.findQuantityByItemIdAndBatchNo(cart.getItemId(), cart.getBatchNo());
	        	System.out.println("Fetched Quantity from stock : "+(Double)availableQuantity);
	        	System.out.println("Quantity in view cart : "+ cart.getQuantity());
	        	
	        	//TODO - need to add condition check for quantity
	        	
	            Invoice invoice = new Invoice();
	            invoice.setInvoiceNo(invoiceno);
	            invoice.setBatchNo(cart.getBatchNo());
	            invoice.setQuantity(cart.getQuantity());
	            invoice.setItemId(cart.getItemId());
	            invoice.setItemName(cart.getItemName());
	            invoice.setHsnCode(cart.getHsnCode());
	            invoice.setTransanctionValue(cart.getTransanctionValue());
	            invoice.setCgst(cart.getCgst());
	            invoice.setSgst(cart.getSgst());
	            invoice.setGst(cart.getGst());
	            invoice.setTotalwithGst(cart.getTotalwithGst());
	            invoice.setSellPrice(cart.getSellPrice());
	            invoice.setGstRate(cart.getGstRate());


	            // Add invoice to the list
	            invoiceRepo.save(invoice);
	        	 // }
	        }
	        
	      //calculate total quantity,transaction value, gst, cgst, sgst, totalWithGst for list of cart items
			 double totaltranValue = 0;
		     double totalwithgst = 0;
		     double cgst = 0;
		     double sgst = 0;
		     double gst = 0;
		     
	        for (Cart cart1 : cartItems) {
	        	totaltranValue +=cart1.getTransanctionValue();
	        	totalwithgst += cart1.getTotalwithGst();
	            cgst +=cart1.getCgst();
	            sgst +=cart1.getSgst();
	            gst += cart1.getGst();
	        }
		
	     // insert details into sell table (also set transaction date as system date)
	        Sell sell = new Sell();
	        sell.setCgst(cgst);
	        sell.setSgst(sgst);
	        
	        //fetch max invoice number
	        Integer invoiceno1 = invoiceRepo.getMaxinvoiceNo();
	        sell.setInvoiceNo(invoiceno1);
	        sell.setCustAddress(address);
	        sell.setCustName(cname);
	        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	        sell.setTransDate(formattedDate);
	        sell.setGst(gst);
	        sell.setTotalwithGst(totalwithgst);
	        sell.setTransanctionValue(totaltranValue);
	        
	        sellRepo.save(sell);

		
		
		// update stock table and reduce the quantity
	        for (Cart cart2 : cartItems) {
	        	
	        	// fetch the quantity entered in cart by itemid and batchno
	        	Double quanTity = cart2.getQuantity();
	        	String itemId1 = cart2.getItemId();
	        	String batchNo1 = cart2.getBatchNo();
	        	
	        	
	        	Stock stock = new Stock();
	        	// fetch quantity from stock for the particular itemid and batchno
	        	Object availableQuantity =  stockRepo.findQuantityByItemIdAndBatchNo(itemId1, batchNo1);
	        	System.out.println("Fetched Quantity before deduction : "+(Double)availableQuantity);
	        	
	        	Double newQuantity = ((Double) availableQuantity) - quanTity;
	        	System.out.println("Fetched Quantity after deduction : "+newQuantity);
	        	
	        	//update quantity
	        	stockRepo.updateQuantity(itemId1,batchNo1,newQuantity);
	        	}
	        Integer latestStockId = sellRepo.latestStockId();
	        System.out.println("Fetched latest Sell Id: "+latestStockId);
	        
		// delete details from cart table
	        cartRepo.deleteCartByUserName(usernameField);
	    
		//Fetch custname, cust address, transaction date from stock based on max stock-id
	        Sell sell1 = sellRepo.findByMaxSellId(latestStockId);
	        
	        //Prepare data to pass to the invoice.html template
	        model.addAttribute("details", new String[]{sell1.getCustName(), sell1.getCustAddress(), sell1.getTransDate()});
	        
	        //fetch max invoice number
	        Integer invoiceno2 = invoiceRepo.getMaxinvoiceNo();
	        System.out.println("Max invoice number: "+invoiceno2);
	        
	        //fetch list of details from invoice based on max invoice no
	        List<Invoice> invoiceItems = invoiceRepo.findByInvoiceNo(invoiceno2);
	        
	        for (Invoice inv2 : invoiceItems) {
	        	System.out.println("Fetched invoice data: "+inv2.getTotalwithGst());
	        }
	        
	        // pass latest invoice no 
	        model.addAttribute("inv", invoiceno2);
	        
	        //pass invoiceItems
	        model.addAttribute("items", invoiceItems);
	        
	        System.out.print("Fetch sell details from max sell id :" +sell1.getCustName());
		
	        return "invoice";
		}

}
