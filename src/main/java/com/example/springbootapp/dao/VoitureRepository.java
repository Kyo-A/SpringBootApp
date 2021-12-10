package com.example.springbootapp.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootapp.model.Voiture;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Long> {
	
	List<Voiture> findByPersonneId(Long personneId);
	
	Optional<Voiture> findByIdAndPersonneId(Long id, Long personneId);

}
