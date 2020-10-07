package com.socin.sys.SocinSys.model.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name = "user", schema = "socin")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GitHubUser {
	@Id
	@Column(name = "id")
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "login")
	@JsonProperty("login")
	@Setter	
	private String login;
		
	@Column(name = "url")
	@JsonProperty("url")
	@Setter
	private String url;	
}
