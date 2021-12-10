package com.example.springbootapp.service;

import java.util.List;
import java.util.Optional;

public interface IService<T> {

	List<T> getAll();
	
	Optional<T> getById(Long id);
	
	T saveOrUpdate(T o);
	
	void delete(Long id);
}
