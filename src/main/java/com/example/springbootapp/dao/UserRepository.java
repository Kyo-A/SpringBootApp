package com.example.springbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootapp.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUserName(String username);

}
