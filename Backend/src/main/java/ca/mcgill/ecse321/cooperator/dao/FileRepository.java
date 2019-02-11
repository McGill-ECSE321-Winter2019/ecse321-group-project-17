package ca.mcgill.ecse321.cooperator.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.File;

public interface FileRepository extends CrudRepository<File, Integer> {
	
	List<File> findByCoop(Coop coop);

}
