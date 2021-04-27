package gr.andzach.libraryws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.andzach.libraryws.config.SecurityConfiguration;
import gr.andzach.libraryws.dto.CreateUserDto;
import gr.andzach.libraryws.dto.GetUserDto;
import gr.andzach.libraryws.model.User;
import gr.andzach.libraryws.repository.UserRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private SecurityConfiguration secureConf;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> user = userRepos.findByUsername(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User with username: "+username+" not found..");
		} else {
			return user.get();
		}
	}
	
	public GetUserDto getUser(Long userId) {
		Optional<User> user = userRepos.findById(userId);
		if(user.isPresent()) {
			Long id = user.get().getUserid();
			String username = user.get().getUsername();
			String email = user.get().getEmail();
			String role = user.get().getRole();
			
			GetUserDto userDto = new GetUserDto(id, username, email, role);
			return userDto;
			
		} else {
			throw new EntityNotFoundException("No user with id: "+userId+" was found!");
		}
	}
	
	public GetUserDto getUserByUsername(String username) {
		Optional<User> user = userRepos.findByUsername(username);
		if(user.isPresent()) {
			Long id = user.get().getUserid();
			String name = user.get().getUsername();
			String email = user.get().getEmail();
			String role = user.get().getRole();
			
			GetUserDto userDto = new GetUserDto(id, name, email, role);
			return userDto;
			
		} else {
			throw new EntityNotFoundException("No user with Username: "+username+" was found!");
		}
	}
	
	public List<GetUserDto> getUsers() {
		return userRepos.findAll().stream().map(user-> new GetUserDto(user.getUserid(), user.getUsername(), user.getEmail(), user.getRole())
		).collect(Collectors.toList());
		
		
	}
	
	public GetUserDto createUser(CreateUserDto userDto) {
		userDto.setPassword(secureConf.passwordEncoder().encode(userDto.getPassword()));
		
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		user.setEmail(userDto.getEmail());
		
		User createdUser = userRepos.save(user);
		
		GetUserDto createdUserDto = new GetUserDto(createdUser.getUserid(), createdUser.getUsername(), createdUser.getEmail(), createdUser.getRole());
		return createdUserDto;
	}

	public GetUserDto updateUser(Long userId, CreateUserDto user) {
		getUser(userId);
		
		User userToUpdate = new User();
		userToUpdate.setUsername(user.getUsername());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setRole(user.getRole());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setUserid(userId);
		
		user.setPassword(secureConf.passwordEncoder().encode(user.getPassword()));
		
		User updatedUser = userRepos.save(userToUpdate);
		GetUserDto userDto = new GetUserDto(updatedUser.getUserid(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getRole());
		return userDto;
	}
	
	public void deleteUser(Long userId) {
			userRepos.deleteById(userId);	
	}
	
	
}
