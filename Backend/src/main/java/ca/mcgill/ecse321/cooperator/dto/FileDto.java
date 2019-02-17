package ca.mcgill.ecse321.cooperator.dto;

public class FileDto {
	
	private Integer id;
	private CoopDto coop;

	public FileDto() {
	}
	
	@SuppressWarnings("unchecked")
	public FileDto(Integer id) {
		this(id, null);
	}
	
	public FileDto(Integer id, CoopDto coop) {
		this.id = id;
		this.coop = coop;
	}
	
	public Integer getID() {
		return id;
	}
	 
	public void setID(Integer id) {
		this.id = id;
	}
	
	public CoopDto getCoop() {
		return coop;
	}
	
	public void setCoop(CoopDto coop) {
		this.coop = coop;
	}
	
}