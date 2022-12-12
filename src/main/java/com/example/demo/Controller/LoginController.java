package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

 
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import com.example.demo.Util.JwtUtil;


@RestController
@CrossOrigin()
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userservice;

    @GetMapping("/")
    public String welcome() {
    	System.out.println("welkom");
        return "Welcome to javatechie !!";
    }

//    @PosMapping("/findByEmail")
//    public String findByEmail(@RequestBody AuthRequest authRequest) throws Exception {
//    	
//    }
   
   @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
        	 
        	authenticationManager.authenticate(
                   (Authentication) new UsernamePasswordAuthenticationToken
                   (authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
      return jwtUtil.generateToken(authRequest.getUserName());
    }
   
   @PostMapping("/AddUser")
	private User saveUser(@RequestBody User user) {
		System.out.println(" create an User" +user);
		userservice.saveUser(user);
		return user;
	}
}