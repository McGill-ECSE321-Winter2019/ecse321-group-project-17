package ca.mcgill.ecse321.cooperator.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Report;

public interface ReportRepository extends CrudRepository<Report, Integer> {
	
	List<Report> findByCoop(Coop coop);

}