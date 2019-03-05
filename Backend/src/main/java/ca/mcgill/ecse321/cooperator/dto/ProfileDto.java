package ca.mcgill.ecse321.cooperator.dto;

import net.bytebuddy.utility.RandomString;

public class ProfileDto {
	
		private String email; // primary key
		private String password; // not empty, not null
		private String name;
		private String phone;
	
		public ProfileDto() {	
		}
		
		@SuppressWarnings("unchecked")
		public ProfileDto(String email) {
			this(email, RandomString.make(10), "", "");
		}
		
		public ProfileDto(String email, String password, String name, String phone) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.phone = phone;
		}
		
		public String getName() {
			return name;
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
