package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Integer>{

	Profile findProfileByEmail(String email);
	
	Profile findProfileByName(String name);
	
	Profile findProfileByPhone(String phone);
	
}
