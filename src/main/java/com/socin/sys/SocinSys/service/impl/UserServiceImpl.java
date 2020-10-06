package com.socin.sys.SocinSys.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.socin.sys.SocinSys.exception.AuthenticationError;
import com.socin.sys.SocinSys.exception.BusinessRulesException;
import com.socin.sys.SocinSys.model.entity.User;
import com.socin.sys.SocinSys.model.repository.UserRepository;
import com.socin.sys.SocinSys.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository repository; 
	
	
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User authenticate(String email, String password) {
		Optional<User> user = repository.findByEmail(email);
		if(!user.isPresent()) {
			throw new AuthenticationError("Usuário não encontrado!");
		}
		
		if(!user.get().getPassword().equals(password)) {
			throw new AuthenticationError("Senha inválida");
		}
		return user.get();
	}

	@Override
	@Transactional
	public User saveUser(User user) {
		validateEmail(user.getEmail());
		return repository.save(user);		
	}

	@Override
	public void validateEmail(String email) {
		
		boolean exists = repository.existsByEmail(email);
		if(exists) {
			throw new BusinessRulesException("Já existe um usuário cadastrado com esse email!");
		}
		
	}

}
