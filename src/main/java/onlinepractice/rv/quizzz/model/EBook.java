package onlinepractice.rv.quizzz.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ebook")
public class EBook extends BaseModel  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ebook_id")
	private Long id;
	
	
	@Size(min = 1, max = 50, message = "The name should be less than 50 characters")
	@NotNull(message = "No name text provided.")
	private String bookName;
	
	@Size(min = 1, max = 250, message = "The description should be less than 250 characters")
	@NotNull(message = "No description text provided.")
	private String description;
	
	private Boolean isPublished = false;
	
	@Size(min = 1, max = 30, message = "The stream should be less than 30 characters")
	@NotNull(message = "No stream text provided.")
	private String stream;

	@Size(min = 1, max = 20, message = "The subject should be less than 30 characters")
	@NotNull(message = "No subject text provided.")
	private String subject;
	
	@Size(min = 1, max = 60, message = "The name should be less than 60 characters")
	@NotNull(message = "No authorname text provided.")
	@Column(name = "author_name")
	private String authorName;

	@Size(min = 1, max = 10, message = "The language should be less than 10 characters")
	@NotNull(message = "No language text provided.")
	private String language;

	@Size(min = 1, max = 200, message = "The path should be less than 200 characters")
	@NotNull(message = "No path text provided.")
	@Column(name = "path")
	private String path;

	@Size(min = 1, max = 200, message = "The thumbNailPath should be less than 200 characters")
	@NotNull(message = "No thumbNailPath text provided.")
	@Column(name = "thumb_nail_path")
	private String thumbNailPath;
	
	@Column(name = "a_order")
	private Integer order;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;


	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getThumbNailPath() {
		return thumbNailPath;
	}

	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
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
		EBook other = (EBook) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
}
