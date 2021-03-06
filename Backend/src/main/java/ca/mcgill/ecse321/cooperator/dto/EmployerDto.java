package ca.mcgill.ecse321.cooperator.dto;

import net.bytebuddy.utility.RandomString;

public class EmployerDto extends ProfileDto {

	private String email; // primary key
	private String password; // not empty, not null
	private String name;
	private String phone;
	private String company;
	private Integer id;

	public EmployerDto() {
	}

	@SuppressWarnings("unchecked")
	public EmployerDto(String email) {
		this(email, RandomString.make(10), "", 0, "", "");
	}

	public EmployerDto(String email, String password, String name, Integer id, String phone, String company) {
		this.name = name;
		this.email = email;
		this.id = id;
		this.password = password;
		this.company = company;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
