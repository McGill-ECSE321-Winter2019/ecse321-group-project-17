package ca.mcgill.ecse321.cooperator.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Integer> {

	Employer findEmployerByName(String name);

	Employer findEmployerByEmail(String email);

	Set<Employer> findEmployerByCompany(String company);

}
