package com.example.springbootapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springbootapp.dao.PersonneRepository;
import com.example.springbootapp.model.Personne;

@Service(value= "personneService")
public class PersonneService implements IService<Personne> {

	@Autowired
	private PersonneRepository personneRepository;
	
	@Override
	public List<Personne> getAll() {
		return personneRepository.findAll();
	}

	@Override
	public Optional<Personne> getById(Long id) {
		return personneRepository.findById(id);
	}

	@Override
	@Transactional(readOnly= false)
	public Personne saveOrUpdate(Personne o) {
		return personneRepository.save(o);
	}

	@Override
	@Transactional(readOnly= false)
	public void delete(Long id) {
		personneRepository.deleteById(id);
	}

}
