package com.socin.sys.SocinSys.service;

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
