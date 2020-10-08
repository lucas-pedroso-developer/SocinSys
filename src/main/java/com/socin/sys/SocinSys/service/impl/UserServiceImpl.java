package com.socin.sys.SocinSys.service.impl;

import java.util.Objects;
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
	
	@Override
	public Optional<User> getUserById(long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public User update(User user) {
		Objects.requireNonNull(user.getId());
		validate(user);
		return repository.save(user);
	}

	@Override
	@Transactional
	public void delete(User user) {
		Objects.requireNonNull(user.getId());
		repository.delete(user);
		
	}
	
	@Override
	public void validate(User user) {
		if(user.getEmail() == null || user.getEmail().trim().equals("")) {
			throw new BusinessRulesException("informe um email válido");
		}
		
		if(user.getJob() == null) {
			throw new BusinessRulesException("Informe uma profissão válida");
		}
		
		if(user.getAge() == null) {
			throw new BusinessRulesException("Informe uma idade válida");
		}
		
		if(user.getName() == null) {
			throw new BusinessRulesException("Informe um nome válido");
		}		
	}

}
