package com.example.springbootapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springbootapp.dao.UserRepository;
import com.example.springbootapp.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	// verifie l’existence d’un utilisateur selon la valeur de userName
	@Override
	public UserDetailsImpl loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(user == null)
			throw new UsernameNotFoundException("No UserName " + username);
		return new UserDetailsImpl(user);
	}

}
