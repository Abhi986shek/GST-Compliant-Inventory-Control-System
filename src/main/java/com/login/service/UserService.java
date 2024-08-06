package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.model.Users;
import com.login.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password, String role) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
        	if(role.equals(user.getRole()))
        	{
        	System.out.println("inside userService: "+user.getUsername()+","+user.getPassword()+","+user.getRole()+","+user.getStatus());
            return password.equals(user.getPassword());
        	
        }
        return false;
        }
		return false;
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users updateEmployee(String userid, String status) {
    	
    	Users user=userRepository.findById(userid).orElseThrow(() -> new RuntimeException("User not found"));
    	user.setStatus(status);
    	return userRepository.save(user);
		
	}
    
    public boolean isUserUnblocked(String uid) {
        Users user = userRepository.findByUsername(uid);
        return user != null && "Unblock".equals(user.getStatus());
    }

    
    
}
