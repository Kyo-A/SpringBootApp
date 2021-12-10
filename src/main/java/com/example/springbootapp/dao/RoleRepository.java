package com.example.springbootapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

}
