package com.login.controller;

/*import java.util.HashMap;
import java.util.Map;*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*import com.login.model.Product;*/
import com.login.model.Users;
import com.login.repository.UserRepository;
import com.login.service.UserService;

@Controller
public class ManagerController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/addEmp")
    public String loginSubmit(@RequestParam String userid,
                              @RequestParam String pass,
                              @RequestParam String name,
                              @RequestParam String type,
                              Model model)
    {
		
		Users user=new Users();
		user.setUsername(userid);
		user.setPassword(pass);
		user.setName(name);
		user.setRole(type);
		user.setStatus("Unblock");
		userRepository.save(user);
		return "Managermenu.html"; 
		
    }
	
	 @GetMapping("/getUserDetails")
	    public ResponseEntity<Users> searchItem(@RequestParam String userid) {
		
		 
	        Users user = userRepository.findByUsername(userid);
	        if (user != null) {
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	

	 @PostMapping("/rmv_emp")
	    public String updateUserStatus(@RequestParam String userid,
	    		@RequestParam String status) {
		 
		 userService.updateEmployee(userid,status);
			return "Managermenu.html";
		 
	 }


	    @DeleteMapping("/deleteUser/{userid}")
	    public ResponseEntity<Void> deleteUser(@PathVariable String userid) {
	        Users user = userRepository.findByUsername(userid);
	        if (user != null) {
	            userRepository.delete(user);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
}
