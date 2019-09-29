package onlinepractice.rv.quizzz.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Candidate_Response")
public class CandidateResponse extends BaseModel /*implements UserOwned*/ {

	@Column(name = "created_By_Id")
	private Long createdById;
	
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private Calendar createdDate;
	
	@Column(name = "quiz_id")
	private Long quizId;

	@Column(name = "question_id")
	private Long questionId;
	
	@Column(name = "candidate_response_id")
	private Long candidateResponseId;
	
	@Column(name = "candidate_response_ids")
	private String candidateResponseIds;
	
	@Column(name = "candidate_response_order_id")
	private Long candidateResponseOrderId;
	
	@Column(name = "candidate_response_order_ids")
	private String candidateResponseOrderIds;

	@Column(name = "candidate_response_status")
	private String candidateResponseStatus;
	
	@Column(name = "correct_Answer_Order_id")
	private Long correctAnswerOrderId;
	
	@Column(name = "correct_Answer_Order_ids")
	private String correctAnswerOrderIds;
	
	
	@Column(name = "is_correct")
	private Boolean IsCorrect = false;
	

	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public Long getQuizId() {
		return quizId;
	}
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getCandidateResponseId() {
		return candidateResponseId;
	}
	public void setCandidateResponseId(Long candidateResponseId) {
		this.candidateResponseId = candidateResponseId;
	}
	public String getCandidateResponseStatus() {
		return candidateResponseStatus;
	}
	public void setCandidateResponseStatus(String candidateResponseStatus) {
		this.candidateResponseStatus = candidateResponseStatus;
	}
	public boolean isIsCorrect() {
		return IsCorrect;
	}
	public void setIsCorrect(boolean isCorrect) {
		IsCorrect = isCorrect;
	}
	@Override
	public String toString() {
		return "Candidate Response [quiz_id=" + quizId + ", question=" + questionId + ", candidateResponseId= " + candidateResponseId + "IsCorrect= "+ IsCorrect + ", createdDate= " + createdDate
				+ "]";
	}
	
	public Long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	public Long getCorrectAnswerOrderId() {
		return correctAnswerOrderId;
	}
	public void setCorrectAnswerOrderId(Long correctAnswerOrderId) {
		this.correctAnswerOrderId = correctAnswerOrderId;
	}
	public Long getCandidateResponseOrderId() {
		return candidateResponseOrderId;
	}
	public void setCandidateResponseOrderId(Long candidateResponseOrderId) {
		this.candidateResponseOrderId = candidateResponseOrderId;
	}
	public String getCandidateResponseIds() {
		return candidateResponseIds;
	}
	public Boolean getIsCorrect() {
		return IsCorrect;
	}
	public void setCandidateResponseIds(String candidateResponseIds) {
		this.candidateResponseIds = candidateResponseIds;
	}
	public void setIsCorrect(Boolean isCorrect) {
		IsCorrect = isCorrect;
	}
	public String getCandidateResponseOrderIds() {
		return candidateResponseOrderIds;
	}
	public void setCandidateResponseOrderIds(String candidateResponseOrderIds) {
		this.candidateResponseOrderIds = candidateResponseOrderIds;
	}
	public String getCorrectAnswerOrderIds() {
		return correctAnswerOrderIds;
	}
	public void setCorrectAnswerOrderIds(String correctAnswerOrderIds) {
		this.correctAnswerOrderIds = correctAnswerOrderIds;
	}
	
}
