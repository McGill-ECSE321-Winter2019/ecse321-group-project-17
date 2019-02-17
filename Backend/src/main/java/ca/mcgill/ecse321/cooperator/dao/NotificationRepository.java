package ca.mcgill.ecse321.cooperator.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Notification;
import ca.mcgill.ecse321.cooperator.model.Profile;

public interface NotificationRepository extends CrudRepository<Notification, Integer>{
	
	Set<Notification> findByProfile(Profile profile);

}
