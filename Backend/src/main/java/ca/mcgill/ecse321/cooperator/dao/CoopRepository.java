package ca.mcgill.ecse321.cooperator.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.CoopStatus;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Student;

public interface CoopRepository extends CrudRepository<Coop, Integer> {

	Set<Coop> findCoopByStudent(Student student);

	Set<Coop> findCoopByTitle(String title);

	Coop findCoopById(Integer id);

	Set<Coop> findCoopByEmployer(Employer employer);

	Set<Coop> findCoopByStatus(CoopStatus status);

}
