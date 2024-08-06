package com.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*import com.login.model.Product;*/
import com.login.model.Vendor;
/*import com.login.repository.ProductRepository;*/
import com.login.repository.VendorRepository;
import com.login.service.VendorService;

@RestController
public class VendorController {
	 @Autowired
	    private VendorRepository vendorRepository;
	 
	 @Autowired
	 private VendorService vendorService;


	    @PostMapping("/add_vend")
	    public String loginSubmit(@RequestParam String vendorname,
	                              @RequestParam String vendorname2,
	                              @RequestParam Long phno,
	                              @RequestParam String email,
	                              @RequestParam String address,
	                              @RequestParam Long pin,
	                              @RequestParam String gstn,
	                              Model model) {
	    	
	    	Vendor vendor=new Vendor();
	    	vendor.setVendorName(vendorname);
	    	vendor.setVendorContact(vendorname2);
	    	vendor.setMobileNo(phno);
	    	vendor.setEmailId(email);
	    	vendor.setAddress(address);
	    	vendor.setPinCode(pin);
	    	vendor.setGstIn(gstn);

	    	vendorRepository.save(vendor);
	    	return "Stockistmenu";
	    }
	    
	    @GetMapping("/fetch-vendor-id")
	    public ResponseEntity<List<String>> getVendorId() {
	        List<String> vendorId = vendorService.findVendorNames();
	        
	        System.out.println("vendor names : " + vendorId);
	        return ResponseEntity.ok(vendorId);
	    }

}
