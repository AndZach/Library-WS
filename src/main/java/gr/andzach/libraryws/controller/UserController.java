package gr.andzach.libraryws.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sun.istack.NotNull;

import gr.andzach.libraryws.dto.CreateUserDto;
import gr.andzach.libraryws.dto.GetUserDto;
import gr.andzach.libraryws.model.User;
import gr.andzach.libraryws.repository.UserRepository;
import gr.andzach.libraryws.service.UserDetailsServiceImpl;

@RestController
@Validated
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
	
	@GetMapping("")
	public ResponseEntity<List<GetUserDto>> getUsers() {
		List<GetUserDto> users = userService.getUsers();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<GetUserDto> getUser(@PathVariable Long id) {
		GetUserDto user = userService.getUser(id);
			return ResponseEntity.ok(user);
	}
	
	@GetMapping(path="", params="username")
	public ResponseEntity<GetUserDto> getUserByUsername(@RequestParam  String username) {
		GetUserDto user = userService.getUserByUsername(username);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserDto> createUser(@RequestBody CreateUserDto user) {
		GetUserDto createdUser = userService.createUser(user);
		URI newUserUri=ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build(createdUser.getUserId());
		return ResponseEntity.created(newUserUri).body(createdUser);
	}
	
	@PutMapping(path="/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetUserDto> updateUser (@PathVariable Long userId, @RequestBody CreateUserDto user) {
		GetUserDto updatedUser = userService.updateUser(userId, user);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok("User with id: " + userId + " was deleted!");
	}
	
	
}
