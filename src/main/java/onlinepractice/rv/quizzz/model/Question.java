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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "question")
public class Question extends BaseModel implements UserOwned {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;

	@Size(min = 2, max = 150, message = "The question should be between 2 and 150 characters")
	@NotNull(message = "Question text not provided")
	private String text;

	@ManyToOne
	@JsonIgnore
	private Quiz quiz;

	@Column(name = "q_order")
	private Integer order;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "question_answer", joinColumns = { @JoinColumn(name = "question_id") }, inverseJoinColumns = { @JoinColumn(name = "answer_id") })
	private List<Answer> answers = new ArrayList<Answer>();

	@JsonIgnore
	@OneToOne
	private Answer correctAnswer;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;

	@Column(name = "image_path")
	private String imagepath;
	
	@Column(name = "category")
	private String category;
	
	@JsonIgnore
	private Boolean isValid = false;
	
	
	private Boolean isMorethenOneCorrect = false;
	
	@Column(name = "multiple_correct_answers")
	private String multipleCorrectAnswers;
	
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public Long getId() {
		return id;
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
		Question other = (Question) obj;
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

	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	@JsonIgnore
	public User getUser() {
		return quiz.getUser();
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getImagepath() {
		return imagepath;
	}

	public String getCategory() {
		return category;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsMorethenOneCorrect() {
		return isMorethenOneCorrect;
	}



	public void setIsMorethenOneCorrect(Boolean isMorethenOneCorrect) {
		this.isMorethenOneCorrect = isMorethenOneCorrect;
	}

	public String getMultipleCorrectAnswers() {
		return multipleCorrectAnswers;
	}

	public void setMultipleCorrectAnswers(String multipleCorrectAnswers) {
		this.multipleCorrectAnswers = multipleCorrectAnswers;
	}




}
