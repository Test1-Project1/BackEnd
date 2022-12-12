package com.example.demo.Service;

 

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Employee;
import com.example.demo.Repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	  public List<Employee> getEmployees() {
		  List<Employee> emloyeeList = new ArrayList<Employee>();  
		  employeeRepository.findAll().forEach(emp-> emloyeeList.add(emp));  
		  return emloyeeList;  
	    }
	  
	  public Employee saveEmployee(Employee employee) {
	        Employee savedEmployee = employeeRepository.save(employee);
	        return savedEmployee;
	    }
}
