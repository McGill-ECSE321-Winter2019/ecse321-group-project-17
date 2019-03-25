package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {

	Administrator findAdministratorByName(String name);

	Administrator findAdministratorByEmail(String email);

}
