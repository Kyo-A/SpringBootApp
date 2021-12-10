package com.example.springbootapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonneDto {
	
	@NotEmpty
	@Size(min = 2, message = "first name should have at least 2 characters")
	private String nom;
	@NotEmpty
	@Size(min = 2, message = "last name should have at least 2 characters")
	private String prenom;

}
