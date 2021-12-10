package com.example.springbootapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.springbootapp.dto.PersonneDto;
import com.example.springbootapp.model.Personne;
import com.example.springbootapp.service.PersonneService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class PersonneRestController {

	@Autowired
	private PersonneService personneService;

	@GetMapping(value = "/personnes")
	public List<Personne> getPersonnes() {
		List<Personne> personnes = personneService.getAll();
		return personnes;
	}

	@GetMapping(value = "/personnes/{id}")
	public ResponseEntity<Personne> getPersonneById(@PathVariable Long id) {
		Personne personne = personneService.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne not found :" + id));
		return ResponseEntity.ok().body(personne);
	}

	@PostMapping(value = "/personnes")
	@Transactional
	public ResponseEntity<Personne> save(@Valid @RequestBody PersonneDto personneDto) {	
		Personne p = new Personne();
		p.setNom(personneDto.getNom());
		p.setPrenom(personneDto.getPrenom());
		personneService.saveOrUpdate(p);
		
		return new ResponseEntity<>(p, HttpStatus.OK);
	}

	@PutMapping(value = "/personnes/{id}")
	@Transactional
	public ResponseEntity<Personne> updatePersonne(@Valid @RequestBody PersonneDto personneDto, @PathVariable(value = "id") Long id)
			throws ResponseStatusException {
		Personne personneToUpdate = personneService.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne not found :: " + id));
		personneToUpdate.setNom(personneDto.getNom());
		personneToUpdate.setPrenom(personneDto.getPrenom());
		personneService.saveOrUpdate(personneToUpdate);
		return new ResponseEntity<Personne>(personneToUpdate, HttpStatus.OK);
	}

	@DeleteMapping(value = "/personnes/{id}")
	@Transactional
	public Map<String, Boolean> deletePersonne(@PathVariable(value = "id") Long id) throws ResponseStatusException {
		Personne personneToDelete = personneService.getById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne not found :: " + id));
		personneService.delete(personneToDelete.getId());
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
