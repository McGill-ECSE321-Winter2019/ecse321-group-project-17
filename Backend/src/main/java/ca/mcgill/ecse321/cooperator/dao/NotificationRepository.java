package ca.mcgill.ecse321.cooperator.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Administrator;
import ca.mcgill.ecse321.cooperator.model.Employer;
import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Student;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

	Set<Notification> findByEmployer(Employer employer);

	Set<Notification> findByStudent(Student student);

	Set<Notification> findBySender(Administrator sender);

}
