package com.example.demo.Controller;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Entity.Employee;
import com.example.demo.Service.EmployeeService;
import org.springframework.web.bind.annotation.RequestBody;  

@CrossOrigin() // open for all ports
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/Employees")
	private List<Employee> getAllEmployee() {
		return employeeService.getEmployees();
	}

	@PostMapping("/Employee")
	private Employee saveEmployee(@RequestBody Employee emp) {
		System.out.println(" create an emplyee" +emp);
		employeeService.saveEmployee(emp);
		return emp;
	}

	// generate a random employee

	@PostMapping("/RandomEmployee")
	private Employee saveEmployee() {
		Employee emp = new Employee();
		emp.setFirstName(generetaName());
		emp.setLastName(generetaName());
		emp.setMail(generateRandomEmailAddress("gmail.com"));
		employeeService.saveEmployee(emp);

		return emp;
	}

	public String generateRandomEmailAddress(String domain) {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		String emailAddress = RandomStringUtils.random(length, useLetters, useNumbers);

		System.out.println(emailAddress);
		return emailAddress + "@" + domain;
	}

	public String generetaName() {

		return RandomStringUtils.randomAlphabetic(8);
	}
}
