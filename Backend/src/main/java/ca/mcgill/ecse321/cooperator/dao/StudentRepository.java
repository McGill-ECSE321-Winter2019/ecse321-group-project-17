package ca.mcgill.ecse321.cooperator.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer>{
	
	Student findStudentByName(String name);
	
	List<Student> findByCoop(Coop coop);

}
