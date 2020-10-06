package com.socin.sys.SocinSys.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socin.sys.SocinSys.api.dto.UserDTO;
import com.socin.sys.SocinSys.exception.AuthenticationError;
import com.socin.sys.SocinSys.exception.BusinessRulesException;
import com.socin.sys.SocinSys.model.entity.User;
import com.socin.sys.SocinSys.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	private UserService service;
	
	public UserResource(UserService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity save(@RequestBody UserDTO dto) {
		User user = User.builder().name(dto.getName()).email(dto.getEmail()).password(dto.getPassword()).age(dto.getAge()).job(dto.getJob()).build();
		try {
			User savedUser = service.saveUser(user);
			return new ResponseEntity(savedUser, HttpStatus.CREATED);
		} catch(BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody UserDTO dto) {
		try {
			User authenticatedUser = service.authenticate(dto.getEmail(), dto.getPassword());
			return new ResponseEntity(authenticatedUser, HttpStatus.CREATED);
		} catch(AuthenticationError e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
}
