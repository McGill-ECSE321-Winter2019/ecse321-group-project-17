package ca.mcgill.ecse321.cooperator.dto;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import net.bytebuddy.utility.RandomString;

public class ProfileDto {
	
		private String email; // primary key
		private String password; // not empty, not null
		private String name;
		private Integer id;
		private String phone;
		private List<CoopDto> coops;
		private Set<NotificationDto> received;
	
		public ProfileDto() {	
		}
		
		@SuppressWarnings("unchecked")
		public ProfileDto(String email) {
			this(email, RandomString.make(10), "", 0, "", Collections.EMPTY_LIST, Collections.EMPTY_SET);
		}
		
		public ProfileDto(String email, String password, String name, Integer id, String phone, List<CoopDto> list,Set<NotificationDto> received) {
			this.name = name;
			this.email = email;
			this.id = id;
			this.password = password;
			this.coops = list;
			this.received = received;
			this.phone = phone;
		}
		
		public String getName() {
			return name;
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
		
		public List<CoopDto> getCoops() {
			return coops;
		}
		
		public void setCoops(List<CoopDto> coops) {
			this.coops = coops;
		}
		
		public Set<NotificationDto> getNotifications() {
			return received;
		}
		
		public void setNotifications(Set<NotificationDto> received) {
			this.received = received;
		}
}
