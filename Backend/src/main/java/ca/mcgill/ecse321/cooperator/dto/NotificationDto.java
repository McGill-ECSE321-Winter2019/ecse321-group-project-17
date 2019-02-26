package ca.mcgill.ecse321.cooperator.dto;

public class NotificationDto {

		private Integer id; // primary key
		private String text; // not null
		private AdminDto sender;
		private StudentDto student;
		private EmployerDto employer;
	
		public NotificationDto() {
			
		}
		
		@SuppressWarnings("unchecked")
		public NotificationDto(Integer id) {
			this(id, "", null, null, null);
		}
		
		public NotificationDto(Integer id, String text, AdminDto admin, StudentDto student, EmployerDto employer) {
			this.id = id;
			this.text = text;
			this.sender = admin;
			this.student = student;
			this.employer = employer;
		}
		
		public Integer getID() {
			return id;
		}
		
		public AdminDto getSender() {
			return sender;
		}
		
		public void setSender(AdminDto sender) {
			this.sender = sender;
		}
		
		public void setStudent(StudentDto student) {
			this.student = student;
		}
		
		public EmployerDto getEmployer() {
			return employer;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		
}
