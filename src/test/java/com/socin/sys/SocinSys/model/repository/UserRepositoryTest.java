package com.socin.sys.SocinSys.model.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.socin.sys.SocinSys.model.entity.User;

//@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest  {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void verifyIfEmailExists() {
		
		User user = createUser();
		entityManager.persist(user);
		
		boolean result = repository.existsByEmail("usuario@email.com");
		
		
		Assertions.assertThat(result).isTrue();
		
	}
	
	@Test
	public void verifyIfEmailNotExists() {		
		boolean result = repository.existsByEmail("usuario@email.com");
		Assertions.assertThat(result).isFalse();
	}
	
	@Test
	public void shouldSaveUser() {
		User user = createUser();
		User savedUser = repository.save(user);
		Assertions.assertThat(savedUser.getId()).isNotNull();
	}
	
	@Test
	public void shouldGetUserByEmail() {
		User user = createUser();
		entityManager.persist(user);
		
		Optional<User> result = repository.findByEmail("usuario@email.com");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	@Test
	public void shouldReturnsVoidWhenUserEmailNotExistsInDatabase() {				
		Optional<User> result = repository.findByEmail("usuario@email.com");		
		Assertions.assertThat(result.isPresent()).isFalse();		
	}
	
	public static User createUser() {
		return User.builder()
						.name("usuario")
						.email("usuario@email.com")			
						.age(20)
						.job("Developer")
						.password("123")							
						.build();
	}
	
	
}