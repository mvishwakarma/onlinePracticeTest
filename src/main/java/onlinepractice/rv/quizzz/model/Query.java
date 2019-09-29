package onlinepractice.rv.quizzz.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "query")
public class Query extends BaseModel implements UserOwned {
		
		@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
		@JsonIgnore
		private Calendar createdDate;
		
		@OneToOne
		@JsonIgnore
		private User createdBy;

		@NotNull(message = "query name not provided")
		private String name;
		
		@NotNull(message = "query email not provided")
		private String email;
		
		@NotNull(message = "query phoneNo not provided")
		private String phoneNo;
		
		@JsonIgnore
		private String queryfrom;
		
		@Size(max = 500, message = "message test quiz time in minutes ")
		private String message;
		
		


		public Calendar getCreatedDate() {
			return createdDate;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public String getQueryfrom() {
			return queryfrom;
		}

		public String getMessage() {
			return message;
		}

		public void setCreatedDate(Calendar createdDate) {
			this.createdDate = createdDate;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public void setQueryfrom(String queryfrom) {
			this.queryfrom = queryfrom;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public User getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(User createdBy) {
			this.createdBy = createdBy;
		}

		@Override
		@JsonIgnore
		public User getUser() {
			return getCreatedBy();
		}
		
		
	
		@Override
		public String toString() {
			return "Query [id=" + super.getId() + ", name=" + name + ", email= " + email + "message= "+ message + ", queryfrom= " + queryfrom
					+ "]";
		}

		
	
}


