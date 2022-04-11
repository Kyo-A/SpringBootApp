package com.example.springbootapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.springbootapp.dao.PersonneRepository;
import com.example.springbootapp.model.Personne;
import com.example.springbootapp.service.PersonneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

@WebMvcTest(PersonneRestController.class)
public class PersonneRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	PersonneService personneService;

	Personne p1 = new Personne(1L, "John", "Wick");
	Personne p2 = new Personne(2L, "John", "Doe");
	Personne p3 = new Personne(3L, "John", "Wayne");

	@Test
	public void getAllPersons_success() throws Exception {
		List<Personne> personnes = new ArrayList<>(Arrays.asList(p1, p2, p3));

		Mockito.when(personneService.getAll()).thenReturn(personnes);

		mockMvc.perform(MockMvcRequestBuilders.get("/personnes").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[2].nom", is("John")));
	}

	@Test
	public void getPersonneById_success() throws Exception {
		Mockito.when(personneService.getById(p1.getId())).thenReturn(java.util.Optional.of(p1));

		mockMvc.perform(MockMvcRequestBuilders.get("/personnes/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.prenom", is("Wick")));
	}

	@Test
	public void createPersonne_success() throws Exception {
		Personne p = new Personne(4L, "Pat", "Pot");

		Mockito.when(personneService.saveOrUpdate(p)).thenReturn(p);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/personnes").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(p));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.nom", is("Pat")));
	}

	@Test
	public void updatePatientRecordById_success() throws Exception {
		Personne p = new Personne(2L, "Pat", "Pot");

		Mockito.when(personneService.getById(p2.getId())).thenReturn(java.util.Optional.of(p2));
		Mockito.when(personneService.saveOrUpdate(p)).thenReturn(p);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/personnes/2").with(csrf())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(p));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.nom", is("Pat")));
	}

	@Test
	public void deletePersonneById_success() throws Exception {
		Mockito.when(personneService.getById(p2.getId())).thenReturn(java.util.Optional.of(p2));

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/personnes/2").with(csrf()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
