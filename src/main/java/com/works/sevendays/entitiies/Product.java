package com.works.sevendays.entitiies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Min(value = 1, message = "pid Min value = 1")
	private int pid ;
	
	@NotNull(message = "Title cannnot be null.!")
	@NotEmpty(message = "Title cannot be empty.!")
	@Size(min = 3, max = 30, message = "Title must be between 3-30 character.!")
	@Column(unique = true)
	private String title;
	
	private String detail;
	
	@Min(value = 10, message = "Price must be min 10")
	@Max(value = 1000, message = "Price must be max 1000")
	private Integer price;

}
