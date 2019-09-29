package onlinepractice.rv.quizzz.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "menu")
public class Menu extends BaseModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "manu_id")
	private Long id;
	
	
	@Size(min = 1, max = 50, message = "The answer should be less than 50 characters")
	@NotNull(message = "No answer text provided.")
	private String name;
	
	private String url;
	
	@Column(name = "parent_id")
	private Long parentId;
	
	private Boolean isPublished = false;
	
	@Size(max = 200, message = "Quiz tags ")
	private String quizTags;
	
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	public Calendar getCreatedDate() {
		return createdDate;
	}

	
	

	@Override
	public String toString() {
		return "Answer [name=" + name + ", id=" + id + ", url=" + url +", parentId= " + parentId + ", createdDate=" + createdDate
				+ "]";
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
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
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}




	public String getName() {
		return name;
	}




	public String getUrl() {
		return url;
	}




	public Long getParentId() {
		return parentId;
	}




	public void setName(String name) {
		this.name = name;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}




	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}




	public Boolean getIsPublished() {
		return isPublished;
	}




	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}




	public String getQuizTags() {
		return quizTags;
	}




	public void setQuizTags(String quizTags) {
		this.quizTags = quizTags;
	}

	

}
