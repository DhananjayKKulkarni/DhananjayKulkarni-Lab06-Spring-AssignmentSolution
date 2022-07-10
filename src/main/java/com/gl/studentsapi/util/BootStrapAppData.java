package com.gl.studentsapi.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gl.studentsapi.model.Role;
import com.gl.studentsapi.model.Student;
import com.gl.studentsapi.model.User;
import com.gl.studentsapi.repository.RoleRepository;
import com.gl.studentsapi.repository.StudentRepository;
import com.gl.studentsapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BootStrapAppData implements ApplicationListener<ApplicationReadyEvent>{
	
	private final PasswordEncoder passwordEncoder;
	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// Sample data for Student table
		for(int i=1; i<=10; i++) {
			Student student = new Student();
			student.setFirstName("FirstName_" + i);
			student.setLastName("LastName_" + i);
			student.setCountry("Country_" + i);
			student.setCourse("Course_" + i);
			studentRepository.save(student);
		}
		
		// Sample data for User and Role tables
		
		Role userRole=new Role();
		userRole.setRoleName("USER");
		
		Role adminRole=new Role();
		adminRole.setRoleName("ADMIN");
		
		User user=new User();
		user.setUserName("user");
		user.setPassword(passwordEncoder.encode("user"));
		
		User admin=new User();
		admin.setUserName("admin");
		admin.setPassword(passwordEncoder.encode("admin"));
				
		admin.addRole(adminRole);
		admin.addRole(userRole);
		
		user.addRole(userRole);
		
		userRepository.save(user);
		userRepository.save(admin);
		
		roleRepository.save(userRole);
		roleRepository.save(adminRole);
	}

}
