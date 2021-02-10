package com.works.sevendays.entitiies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int uid;
	
	@NotNull(message = "Name cannot be null")
	@NotEmpty(message = "Name cannot be empty")
	@Size(min = 5, max = 30)
	private String name;
	
	@Column(unique = true, length = 50)
	@Email(message = "mail format not valid")
	@NotEmpty(message = "mail cannot be empty")
	private String mail;
	
	@NotEmpty(message = "Password cannot be empty")
	@NotNull(message = "Pasword cannot be null")
	//@Pattern(regexp = "")
	private String pass;
	
}
