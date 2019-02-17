package ca.mcgill.ecse321.cooperator.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.bytebuddy.utility.RandomString;

public class AdminDto {

		private String email; // primary key
		private String password; // not null, not empty
		private String name;
		private List<CoopDto> coops;
		private String phone;
		private Integer id;
		private Set<NotificationDto> sent;
	
		public AdminDto() {
		}
		
		@SuppressWarnings("unchecked")
		public AdminDto(String email) {
			this(email, RandomString.make(10), "", 0, "", Collections.EMPTY_LIST, Collections.EMPTY_SET);
		}
		
		public AdminDto(String email, String password, String name, Integer id, String phone, List<CoopDto> list, Set<NotificationDto> sent) {
			this.name = name;
			this.email = email;
			this.id = id;
			this.password = password;
			this.coops = list;
			this.sent = sent;
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
			return sent;
		}
		
		public void setNotifications(Set<NotificationDto> sent) {
			this.sent = sent;
		}
}
