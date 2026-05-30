package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Credentials;

@RestController
public class TestController {
	
	@Autowired
	Credentials credentials;
	
	@GetMapping("/creds")
	public Credentials getCredentials() {
		return credentials;
	}

}
