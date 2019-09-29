package onlinepractice.rv.quizzz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pack")
public class Pack extends BaseModel implements UserOwned {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pack_id")
	private Long id;
	
	@OneToOne
	@JsonIgnore
	private User createdBy;

	@Size(min = 2, max = 100, message = "The name must be between 2 and 100 messages.")
	@NotNull(message = "Please provide a name for pack")
	private String name;

	@Size(max = 500, message = "The description can't be longer than 500 characters.")
	@NotNull(message = "Please, provide a description for pack")
	private String description;
	
	@Size(max = 500, message = "The pack objective can't be longer than 500 characters.")
	@NotNull(message = "Please, provide a pack objective for pack")
	private String packObjective;
	
	
	@Size(max = 200, message = "pack tags ")
	private String tags;
	
	@Size(min = 1, max = 200, message = "The thumbNailPath should be less than 200 characters")
	@NotNull(message = "No thumbNailPath text provided.")
	@Column(name = "thumb_nail_path")
	private String thumbNailPath;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "pack_quiz", joinColumns = { @JoinColumn(name = "pack_id") }, inverseJoinColumns = { @JoinColumn(name = "quiz_id") })
	private List<Quiz> packQuizs =  new ArrayList<Quiz>();
	

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	private Boolean isPublished = false;
	
	private Double packPrize;
	
	private Integer quizCount;

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
		Pack other = (Pack) obj;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public List<Quiz> getPackQuizs() {
		return packQuizs;
	}

	public Double getPackPrize() {
		return packPrize;
	}

	public Integer getQuizCount() {
		return quizCount;
	}

	public void setPackQuizs(List<Quiz> packQuizs) {
		this.packQuizs = packQuizs;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public void setPackPrize(Double packPrize) {
		this.packPrize = packPrize;
	}

	public void setQuizCount(Integer quizCount) {
		this.quizCount = quizCount;
	}

	public String getThumbNailPath() {
		return thumbNailPath;
	}

	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}

	public String getPackObjective() {
		return packObjective;
	}

	public void setPackObjective(String packObjective) {
		this.packObjective = packObjective;
	}


}
