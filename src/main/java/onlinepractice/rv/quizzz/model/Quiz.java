package onlinepractice.rv.quizzz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "quiz")
public class Quiz extends BaseModel implements UserOwned {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "quiz_id")
	private Long id;
	
	@OneToOne
	@JsonIgnore
	private User createdBy;

	@Size(min = 2, max = 100, message = "The name must be between 2 and 100 messages.")
	@NotNull(message = "Please provide a name")
	private String name;

	@Size(max = 500, message = "The description can't be longer than 500 characters.")
	@NotNull(message = "Please, provide a description")
	private String description;
	
	@Size(max = 100, message = "The portions can't be longer than 25 characters.")
	private String portions="math,0|chemistory,31|physics,61";

	@Size(max = 10, message = "quiz time in minutes ")
	private String time="180";
	
	@Size(max = 200, message = "Quiz tags ")
	private String tags;
	
	@JsonIgnore	
	@ManyToMany(mappedBy="enrolledquizes")
	private List<User> enrolledUsers = new ArrayList<User>();
	
	private Boolean isTimerEnable = true;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Question> questions;
	
	@JsonIgnore
	@ManyToMany(mappedBy="packQuizs")
	private List<Pack> packs = new ArrayList<Pack>();

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	private Boolean isPublished = false;
	
	private Boolean isFree = false;
	
	private Double quizPrize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quiz other = (Quiz) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> exercises) {
		this.questions = exercises;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	@JsonIgnore
	public User getUser() {
		return getCreatedBy();
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getPortions() {
		return portions;
	}

	public void setPortions(String portions) {
		this.portions = portions;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Boolean getIsTimerEnable() {
		return isTimerEnable;
	}

	public void setIsTimerEnable(Boolean isTimerEnable) {
		this.isTimerEnable = isTimerEnable;
	}

	public Boolean getIsFree() {
		return isFree;
	}

	public void setIsFree(Boolean isFree) {
		this.isFree = isFree;
	}

	public List<User> getEnrolledUsers() {
		return enrolledUsers;
	}

	public void setEnrolledUsers(List<User> enrolledUsers) {
		this.enrolledUsers = enrolledUsers;
	}

	public Double getQuizPrize() {
		return quizPrize;
	}

	public void setQuizPrize(Double quizPrize) {
		this.quizPrize = quizPrize;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}


}
