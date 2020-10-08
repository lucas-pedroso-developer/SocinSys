package com.socin.sys.SocinSys.service;

import java.util.Optional;

import com.socin.sys.SocinSys.model.entity.User;

public interface UserService {
	User authenticate(String email, String senha);
	User saveUser(User user);
	void validateEmail(String email);
	Optional<User> getUserById(long id);
	User update(User user);
	void delete(User user);
	void validate(User user);	
}
