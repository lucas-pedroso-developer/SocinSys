package com.socin.sys.SocinSys.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.socin.sys.SocinSys.api.dto.UserDTO;
import com.socin.sys.SocinSys.exception.AuthenticationError;
import com.socin.sys.SocinSys.exception.BusinessRulesException;
import com.socin.sys.SocinSys.model.entity.User;
import com.socin.sys.SocinSys.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {
	
	private final UserService service;
		
	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody UserDTO dto) {
		try {			
			User authenticatedUser = service.authenticate(dto.getEmail(), dto.getPassword());			
			return ResponseEntity.ok(authenticatedUser);			
		} catch(AuthenticationError e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
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
	
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
		try {						
			return service.getUserById(id).map(entity -> {
				User user = convert(dto);
				user.setId(entity.getId());
				service.update(user);
				return ResponseEntity.ok(user);				
			}).orElseGet(() -> new ResponseEntity("Usuário não encontrado na base de Dados", HttpStatus.BAD_REQUEST) );						
		} catch(BusinessRulesException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ) {
		return service.getUserById(id).map(entidade -> {
			service.delete(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Usuário não encontrado na base de Dados", HttpStatus.BAD_REQUEST) );
	}
			
	private User convert(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());		
		user.setName(dto.getName());
		user.setAge(dto.getAge());
		user.setEmail(dto.getEmail());
		user.setJob(dto.getJob());
		user.setPassword(dto.getPassword());
		return user;
	}
	
}
