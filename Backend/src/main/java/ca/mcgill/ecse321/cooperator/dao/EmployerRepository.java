package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Integer> {
	
	Employer findEmployerByName(String name);
	
}
