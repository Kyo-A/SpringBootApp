package com.example.springbootapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.springbootapp.dao.PersonneRepository;
import com.example.springbootapp.dao.VoitureRepository;
import com.example.springbootapp.exception.ResourceNotFoundException;
import com.example.springbootapp.model.Voiture;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class VoitureRestController {

	@Autowired
	private PersonneRepository personneRepository;

	@Autowired
	private VoitureRepository voitureRepository;

	@ApiOperation(value = "Récupère toutes les voitures d'une personne !")
	@GetMapping("/personnes/{personneId}/voitures")
	public List<Voiture> getCarsByPerson(@PathVariable("personneId") Long personneId) {
		if (!personneRepository.existsById(personneId))
			throw new ResourceNotFoundException("Personne non trouvée :" + personneId);
		return voitureRepository.findByPersonneId(personneId);
	}

	@ApiOperation(value = "Récupère une voiture d'une personne !")
	@GetMapping("/personnes/{personneId}/voitures/{voitureId}")
	public Voiture getOneCarByPerson(@PathVariable("personneId") Long personneId,
			@PathVariable(value = "voitureId") Long voitureId) {
		if (!personneRepository.existsById(personneId))
			throw new ResourceNotFoundException("Personne non trouvée :" + personneId);
		return voitureRepository.findByIdAndPersonneId(voitureId, personneId).orElseThrow(
				() -> new ResourceNotFoundException("Voiture non trouvée :" + voitureId));
	}

	@ApiOperation(value = "Enregistre une voiture !")
	@PostMapping("/personnes/{personneId}/voitures")
	public Voiture save(@PathVariable("personneId") Long personneId, @RequestBody Voiture voiture) {
		return personneRepository.findById(personneId).map(personne -> {
			voiture.setPersonne(personne);
			return voitureRepository.save(voiture);
		}).orElseThrow(() -> new ResourceNotFoundException("Personne non trouvée :" + personneId));
	}

	@ApiOperation(value = "Met à jour une voiture !")
	@PutMapping("/personnes/{personneId}/voitures/{voitureId}")
	public Voiture updateCourse(@PathVariable(value = "personneId") Long personneId,
			@PathVariable(value = "voitureId") Long voitureId, @RequestBody Voiture voitureRequest) {
		if (!personneRepository.existsById(personneId))
			throw new ResourceNotFoundException("Personne non trouvée :" + personneId);
		return voitureRepository.findById(voitureId).map(voiture -> {
			voiture.setMarque(voitureRequest.getMarque());
			voiture.setModele(voitureRequest.getModele());
			return voitureRepository.save(voiture);
		}).orElseThrow(() -> new ResourceNotFoundException("Voiture non trouvée :" + voitureId));
	}

	@ApiOperation(value = "Supprime une voiture !")
	@DeleteMapping("/personnes/{personneId}/voitures/{voitureId}")
	public ResponseEntity<?> deleteCourse(@PathVariable(value = "personneId") Long personneId,
			@PathVariable(value = "voitureId") Long voitureId) {
		if (!personneRepository.existsById(personneId))
			throw new ResourceNotFoundException("Personne non trouvée :" + personneId);
		return voitureRepository.findByIdAndPersonneId(voitureId, personneId).map(voiture -> {
			voitureRepository.delete(voiture);
			return ResponseEntity.ok().build();
		}).orElseThrow(()-> new ResourceNotFoundException("Voiture non trouvée :" + voitureId));
	}

}
