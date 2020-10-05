package com.socin.sys.SocinSys.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.socin.sys.SocinSys.exception.AuthenticationError;
import com.socin.sys.SocinSys.exception.BusinessRulesException;
import com.socin.sys.SocinSys.model.entity.User;
import com.socin.sys.SocinSys.model.repository.UserRepository;
import com.socin.sys.SocinSys.service.impl.UserServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserServiceTest {
	
	UserService service;
	
	@MockBean
	UserRepository repository;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UserRepository.class);
		service = new UserServiceImpl(repository);
	}
	
	@Test
	public void shouldAuthenticateUser() {
		String email = "email@email.com";
		String password = "123";
		
		User user = User.builder().email(email).password(password).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));
		User result = service.authenticate(email, password);
		Assertions.assertThat(result).isNotNull();
	}
		
	@Test(expected = AuthenticationError.class)
	public void shouldThrowsErrorWhenNotFindRegisteredUser() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		service.authenticate("email@email.com", "password");
	}
	
	@Test(expected = Test.None.class)
	public void validadeEmail() {		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		service.validateEmail("email@email.com");
	}
	
	@Test(expected = BusinessRulesException.class)
	public void errorWhenEmailIsRegistered() {		
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		service.validateEmail("email@email.com");		
	}
	
}
