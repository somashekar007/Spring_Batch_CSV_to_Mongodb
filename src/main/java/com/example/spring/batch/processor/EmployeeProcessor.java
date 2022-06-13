package com.example.spring.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.spring.batch.model.Employee;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee item) throws Exception {
		// TODO Auto-generated method stub
	if(item.getAddress().equals("Mumbai"))
		{
		return null;
	}
	else
	{
		return item;
	}
	}

}
