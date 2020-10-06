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
import org.springframework.boot.test.mock.mockito.SpyBean;
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
	
	@SpyBean
	UserServiceImpl service;
	
	@MockBean
	UserRepository repository;
	
	
	@Test
	public void shouldSaveUser() {
		Mockito.doNothing().when(service).validateEmail(Mockito.anyString());
		User user = User.builder().id(1l).name("usuario").email("email@email.com").password("password").age(20).job("Programmer").build();
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		User savedUser = service.saveUser(new User());
		
		Assertions.assertThat(savedUser).isNotNull();
		Assertions.assertThat(savedUser.getId()).isEqualTo(1l);
		Assertions.assertThat(savedUser.getName()).isEqualTo("usuario");
		Assertions.assertThat(savedUser.getEmail()).isEqualTo("email@email.com");
		Assertions.assertThat(savedUser.getPassword()).isEqualTo("password");
		Assertions.assertThat(savedUser.getAge()).isEqualTo(20);
		Assertions.assertThat(savedUser.getJob()).isEqualTo("Programmer");
	}
	
	@Test(expected = BusinessRulesException.class)
	public void shouldNotSaveUserRegistered() {
		String email = "email@email.com";
		User user = User.builder().email(email).build();
		Mockito.doThrow(BusinessRulesException.class).when(service).validateEmail(email);
		
		service.saveUser(user);
		Mockito.verify(repository, Mockito.never()).save(user);
	}
	
	@Test(expected = Test.None.class)
	public void shouldAuthenticateUser() {
		String email = "email@email.com";
		String password = "123";
		
		User user = User.builder().email(email).password(password).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(user));
		User result = service.authenticate(email, password);
		Assertions.assertThat(result).isNotNull();
	}
		
	@Test
	public void shouldThrowsErrorWhenNotFindRegisteredUser() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		Throwable exception = Assertions.catchThrowable(() -> service.authenticate("email@email.com", "password"));
		Assertions.assertThat(exception).isInstanceOf(AuthenticationError.class).hasMessage("Usuário não encontrado!");
	}
	
	@Test
	public void shouldThrowsErrorWhenWrongPassword() {
		String password = "password";
		User user = User.builder().email("email@email.com").password(password).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
		
		Throwable exception = Assertions.catchThrowable( () -> service.authenticate("email@email.com", "123") );
		Assertions.assertThat(exception).isInstanceOf(AuthenticationError.class).hasMessage("Senha inválida");
		
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
