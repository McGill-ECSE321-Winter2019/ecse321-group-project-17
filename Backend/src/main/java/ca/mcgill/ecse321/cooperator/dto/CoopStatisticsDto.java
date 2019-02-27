package ca.mcgill.ecse321.cooperator.dto;

import org.springframework.beans.factory.annotation.Autowired;

import ca.mcgill.ecse321.cooperator.dao.CoopRepository;

public class CoopStatisticsDto {
	// filters
	private Integer currentyear; // current term of the students (i.e. Year 0, Year 1, Year 2, etc...)
	private Integer gradyear;	 // year in which the students graduate(i.e 2020)
	
	// coops
	private Integer notStartedCoops;
	private Integer inProgressCoops;
	private Integer completedCoops;
	private Integer incompleteCoops;
	private Integer totalCoops;
	
	@SuppressWarnings("unchecked")
	public CoopStatisticsDto() {
		this(0,0,0,0,0,0,0);
	}
	
	public CoopStatisticsDto(Integer currentyear, Integer gradyear, Integer notStartedCoops, Integer inProgressCoops, Integer completedCoops,
			Integer incompleteCoops, Integer totalCoops) {
		this.currentyear = currentyear;
		this.gradyear = gradyear;
		this.notStartedCoops = notStartedCoops;
		this.inProgressCoops = inProgressCoops;
		this.completedCoops = completedCoops;
		this.incompleteCoops = incompleteCoops;
		this.totalCoops = totalCoops;
	}
	
	public CoopStatisticsDto generateAllCoopStatistics(Integer currentyear, Integer gradyear) {
		CoopStatisticsDto csd = new CoopStatisticsDto();
		csd.currentyear = currentyear;
		csd.gradyear = gradyear;
		//fill statistics
		return csd;
	}

	public Integer getCurrentyear() {
		return currentyear;
	}

	public void setCurrentyear(Integer currentyear) {
		this.currentyear = currentyear;
	}

	public Integer getGradyear() {
		return gradyear;
	}

	public void setGradyear(Integer gradyear) {
		this.gradyear = gradyear;
	}

	public Integer getNotStartedCoops() {
		return notStartedCoops;
	}

	public void setNotStartedCoops(Integer notStartedCoops) {
		this.notStartedCoops = notStartedCoops;
	}

	public Integer getInProgressCoops() {
		return inProgressCoops;
	}

	public void setInProgressCoops(Integer inProgressCoops) {
		this.inProgressCoops = inProgressCoops;
	}

	public Integer getCompletedCoops() {
		return completedCoops;
	}

	public void setCompletedCoops(Integer completedCoops) {
		this.completedCoops = completedCoops;
	}

	public Integer getIncompleteCoops() {
		return incompleteCoops;
	}

	public void setIncompleteCoops(Integer incompleteCoops) {
		this.incompleteCoops = incompleteCoops;
	}

	public Integer getTotalCoops() {
		return totalCoops;
	}

	public void setTotalCoops(Integer totalCoops) {
		this.totalCoops = totalCoops;
	}
	
}
