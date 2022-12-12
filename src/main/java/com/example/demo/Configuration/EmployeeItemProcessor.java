package com.example.demo.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.Entity.Employee;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeItemProcessor.class);

	@Override
	public Employee process(final Employee emp) throws Exception {

		String firstName = emp.getFirstName().toString();
		String lastName = emp.getLastName().toString();
		String mail = emp.getMail().toString();
		System.out.println("first name " + firstName + "last name " + lastName + "mail " + mail);
		Employee transformedEmp = new Employee(firstName, lastName, mail);
		LOGGER.info("Converting ( {} ) into ( {} )", emp, transformedEmp);

		return transformedEmp;
	}

}