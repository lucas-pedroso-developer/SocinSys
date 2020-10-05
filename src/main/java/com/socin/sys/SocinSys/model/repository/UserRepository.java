package com.socin.sys.SocinSys.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socin.sys.SocinSys.model.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
}
