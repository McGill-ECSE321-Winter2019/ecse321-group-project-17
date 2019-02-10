package ca.mcgill.ecse321.cooperator.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.model.Coop;
import ca.mcgill.ecse321.model.Employer;
import ca.mcgill.ecse321.model.Student;

public interface CoopRepository extends CrudRepository<Coop, Integer>{
	
	Coop findCoopByStudent(Student student);
	
	Coop findCoopByTitle(String title);
	
	Coop findCoopByEmployer(Employer employer);

}
