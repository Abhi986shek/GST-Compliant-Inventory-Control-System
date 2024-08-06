package com.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*import com.login.model.GSTRate;*/
import com.login.repository.GSTRateRepository;
import com.login.service.GstService;

@RestController
public class GstRateController {

    @Autowired
    private GstService gstService;

    @Autowired
    GSTRateRepository gstRepository;

    @GetMapping("/fetch-gst-rates")
    public ResponseEntity<List<String>> getcat() {
        List<String> distcats = gstService.getAllCat();
        System.out.println("GST RATE CAT : " + distcats);
        return ResponseEntity.ok(distcats);
    }
    

    @GetMapping("/fetch-item-names/{itemId}")
    public ResponseEntity<List<String>> fetchItemNames(@PathVariable String itemId) {
        List<String> itemNames = gstService.getItemNamesByCat(itemId);
        System.out.println("GST RATE items : " + itemNames);
        return ResponseEntity.ok(itemNames);
    }

    @GetMapping("/fetch-item-rates/{name}")
    public ResponseEntity<Double> getGSTRate(@PathVariable String name, Model model) {
        double rate = gstService.findByItemName(name);
        System.out.println("GST ITEM Name: " + name);
        System.out.println("GST ITEM RATE: " + rate);
        return ResponseEntity.ok(rate);
    }

    @PostMapping("/updateGST")
    public ResponseEntity<String> updateGST(@RequestParam String itemNames,
                            @RequestParam double rate,
                            Model model) {
        System.out.println("Update RATE: " + itemNames + "," + rate);
        gstService.updateRate(itemNames, rate);
        model.addAttribute("message", "GST rate successfully updated.");
        return ResponseEntity.ok("Successfully updated the GST rate.");
    }


	
	/*
	 * @GetMapping("find_sub_cat/{category}") public ResponseEntity<GSTRate>
	 * searchItem(@PathVariable String category) {
	 * 
	 * 
	 * GSTRate gstRate = gstRateRepository.findByCat(cat); if(gstRate != null) {
	 * return ResponseEntity.ok(gstRate); } else { return
	 * ResponseEntity.notFound().build(); } }
	 */
	

}
