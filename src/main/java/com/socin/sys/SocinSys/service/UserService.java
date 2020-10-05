package com.socin.sys.SocinSys.service;

import com.socin.sys.SocinSys.model.entity.User;

public interface UserService {
	User authenticate(String email, String senha);
	User saveUser(User user);
	void validateEmail(String email);
}
