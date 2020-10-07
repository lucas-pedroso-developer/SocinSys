package com.socin.sys.SocinSys.api.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.socin.sys.SocinSys.api.dto.UserDTO;
import com.socin.sys.SocinSys.exception.AuthenticationError;
import com.socin.sys.SocinSys.model.entity.GitHubUser;
import com.socin.sys.SocinSys.model.entity.User;

@RestController
@RequestMapping("/api")
public class GithubResource {
		
   @Autowired
   RestTemplate restTemplate;
   
   @RequestMapping(value = "/github-users/{page}", method = RequestMethod.GET)
   public ResponseEntity<GitHubUser[]> getGithubUsers(@PathVariable Integer page) {
	   ResponseEntity<GitHubUser[]> response =
			   restTemplate.getForEntity(
			   "https://api.github.com/users?page=" + page, //+ "&per_page=2",
			   GitHubUser[].class);
	   GitHubUser[] gitHubUsers = response.getBody();	   
	   System.out.print("page");
	   System.out.print("https://api.github.com/users?page=" + page);
	   return new ResponseEntity<GitHubUser[]>(gitHubUsers, HttpStatus.CREATED);
   }  	
}
