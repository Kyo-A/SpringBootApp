package com.example.springbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootapp.model.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

}
