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
@Table(name = "student_feedback")
public class StudentFeedback extends BaseModel implements UserOwned {
		
		@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
		@JsonIgnore
		private Calendar createdDate;
		
		@OneToOne
		@JsonIgnore
		private User createdBy;

		@NotNull(message = "feedback name not provided")
		private String name;
		
		@NotNull(message = "feedback email not provided")
		private String email;
		
		@NotNull(message = "feedback mobileNo not provided")
		private String mobileNo;
		
		@NotNull(message = "feedback state not provided")
		private String state;
		
		@NotNull(message = "feedback district not provided")
		private String district;
		
		@NotNull(message = "feedback overAllExprate not provided")
		private int overAllExprate;
		
		@NotNull(message = "feedback navigatebetweenQuestion not provided")
		private Boolean navigatebetweenQuestion;
		
		@NotNull(message = "feedback understandMarkingScheme not provided")
		private Boolean understandMarkingScheme;

		@NotNull(message = "feedback confidenceonCBT not provided")
		private Boolean confidenceonCBT;
		
		private Boolean userfriendyTest;
		
//		private Boolean staffhelful;
		
//		@NotNull(message = "feedback interested in practice more not provided")
//		private Boolean interestedinPracticeMore;
//		
//		@NotNull(message = "feedback infra is more useful not provided")
//		private Boolean infraIsUseful;
		
		
		@NotNull(message = "feedback category not provided")
		private String category;
		
		@NotNull(message = "feedback subject not provided")
		private String subject;
		
//		private String centerName;
		
		@Size(max = 500, message = "message test quiz time in minutes ")
		private String feedback;

		public Calendar getCreatedDate() {
			return createdDate;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
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
		
		
		public String getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public int getOverAllExprate() {
			return overAllExprate;
		}

		public void setOverAllExprate(int overAllExprate) {
			this.overAllExprate = overAllExprate;
		}

		public Boolean getNavigatebetweenQuestion() {
			return navigatebetweenQuestion;
		}

		public void setNavigatebetweenQuestion(Boolean navigatebetweenQuestion) {
			this.navigatebetweenQuestion = navigatebetweenQuestion;
		}

		public Boolean getUnderstandMarkingScheme() {
			return understandMarkingScheme;
		}

		public void setUnderstandMarkingScheme(Boolean understandMarkingScheme) {
			this.understandMarkingScheme = understandMarkingScheme;
		}

		public Boolean getConfidenceonCBT() {
			return confidenceonCBT;
		}

		public void setConfidenceonCBT(Boolean confidenceonCBT) {
			this.confidenceonCBT = confidenceonCBT;
		}

		public Boolean getUserfriendyTest() {
			return userfriendyTest;
		}

		public void setUserfriendyTest(Boolean userfriendyTest) {
			this.userfriendyTest = userfriendyTest;
		}

//		public Boolean getStaffhelful() {
//			return staffhelful;
//		}
//
//		public void setStaffhelful(Boolean staffhelful) {
//			this.staffhelful = staffhelful;
//		}

//		public Boolean getInterestedinPracticeMore() {
//			return interestedinPracticeMore;
//		}
//
//		public void setInterestedinPracticeMore(Boolean interestedinPracticeMore) {
//			this.interestedinPracticeMore = interestedinPracticeMore;
//		}
//
//		public Boolean getInfraIsUseful() {
//			return infraIsUseful;
//		}
//
//		public void setInfraIsUseful(Boolean infraIsUseful) {
//			this.infraIsUseful = infraIsUseful;
//		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

//		public String getCenterName() {
//			return centerName;
//		}
//
//		public void setCenterName(String centerName) {
//			this.centerName = centerName;
//		}

		public String getFeedback() {
			return feedback;
		}

		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}

		@Override
		public String toString() {
			return "Query [id=" + super.getId() + ", name=" + name + ", email= " + email + "feedback= "+ feedback + ", "
					+ "]";
		}

		
	
}


