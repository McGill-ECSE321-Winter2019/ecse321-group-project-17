package ca.mcgill.ecse321.cooperator.dto;

public class NotificationDto {

		private ProfileDto sender;
		private ProfileDto receiver;
		private Integer id;
		private String text;
	
		public NotificationDto() {
			
		}
		
		@SuppressWarnings("unchecked")
		public NotificationDto(Integer id) {
			this(id, "", null, null);
		}
		
		public NotificationDto(Integer id, String text, ProfileDto sender, ProfileDto receiver) {
			this.id = id;
			this.text = text;
			this.sender = receiver;
			this.receiver = receiver;
		}
		
		public Integer getID() {
			return id;
		}
		
		public ProfileDto getSender() {
			return sender;
		}
		
		public void setSender(ProfileDto sender) {
			this.sender = sender;
		}
		
		public void setReceiver(ProfileDto receiver) {
			this.receiver = receiver;
		}
		
		public ProfileDto getReceiver() {
			return receiver;
		}
		
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		
		
}
