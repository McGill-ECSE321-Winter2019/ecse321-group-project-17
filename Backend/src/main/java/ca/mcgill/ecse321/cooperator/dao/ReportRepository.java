package ca.mcgill.ecse321.cooperator.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.cooperator.model.Coop;
import ca.mcgill.ecse321.cooperator.model.Report;
import ca.mcgill.ecse321.cooperator.model.ReportStatus;
import ca.mcgill.ecse321.cooperator.model.ReportType;

public interface ReportRepository extends CrudRepository<Report, Integer> {

	Report findReportByid(Integer id);

	List<Report> findByCoop(Coop coop);

	List<Report> findByStatus(ReportStatus status);

	List<Report> findByType(ReportType type);

}