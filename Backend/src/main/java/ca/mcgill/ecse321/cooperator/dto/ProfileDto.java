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
		private String phone;
		private Set<NotificationDto> received;
		private Set<NotificationDto> sent;
	
		public ProfileDto() {	
		}
		
		@SuppressWarnings("unchecked")
		public ProfileDto(String email) {
			this(email, RandomString.make(10), "", "", Collections.EMPTY_SET, Collections.EMPTY_SET);
		}
		
		public ProfileDto(String email, String password, String name, String phone, Set<NotificationDto> received, Set<NotificationDto> sent) {
			this.name = name;
			this.email = email;
			this.password = password;
			this.received = received;
			this.sent = sent;
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
		
		public Set<NotificationDto> getNotifications() {
			return received;
		}
		
		public void setNotifications(Set<NotificationDto> received) {
			this.received = received;
		}
}
