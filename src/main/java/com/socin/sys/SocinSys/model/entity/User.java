package com.socin.sys.SocinSys.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

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
public class User {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	@Setter
	private String name;
	
	@Column(name = "age")
	@Setter
	private Integer age;
	
	@Column(name = "email")
	@Setter
	private String email;
	
	@Column(name = "password")
	@Setter
	private String password;
	
	@Column(name = "job")
	@Setter
	private String job;
	
}
