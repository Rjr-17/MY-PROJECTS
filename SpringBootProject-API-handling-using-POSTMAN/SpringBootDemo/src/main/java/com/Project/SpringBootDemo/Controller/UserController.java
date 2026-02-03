package com.Project.SpringBootDemo.Controller;


//import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.SpringBootDemo.Exception.ResourceNotFoundException;
import com.Project.SpringBootDemo.entity.userEntity;
import com.Project.SpringBootDemo.repository.UserRepository;
//import com.Project.SpringBootDemo.model.Users;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<userEntity> getUsers() {
	//	return Arrays.asList(new Users(1L,"raja","abc@mail.com"),
		//		new Users(2l,"ram","ram@mail.com"),
			//	new Users(3l,"prabu","rahul@mail.com"));
		return userRepository.findAll();
	}
	@PostMapping
	public userEntity createUser(@RequestBody userEntity user) {
		
		return userRepository.save(user);
	}
	@GetMapping("/{id}")
	public userEntity getUserbyId(@PathVariable Long id) {
		return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not found in that id:"+id));
		
	}
	@PutMapping("/{id}")
	public userEntity UpdateUser(@PathVariable Long id,@RequestBody userEntity user) {
		
		userEntity userData = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not found in that id:"+id));
		userData.setEmail(user.getEmail());
		userData.setName(user.getName());  
		return userRepository.save(userData);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserbyId(@PathVariable Long id) {
		userEntity userData=userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not found in that id:"+id));
	    userRepository.delete(userData);
	    return ResponseEntity.ok().build();
	}

}
