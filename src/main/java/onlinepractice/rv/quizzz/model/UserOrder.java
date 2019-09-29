package onlinepractice.rv.quizzz.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserOrder")
public class UserOrder extends BaseModel implements UserOwned {
	
	@OneToOne
	@JsonIgnore
	private User createdBy;

	private String mobileno;
	
	private Long quizId;
	
	private Long packId;
	
	private Boolean isBulkOrder = false;
	


	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@JsonIgnore
	private Calendar createdDate;
	
	@Override
	public User getUser() {
		return this.getCreatedBy();
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public String getMobileno() {
		return mobileno;
	}

	public Long getQuizId() {
		return quizId;
	}

	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public void setQuizId(Long quiz_id) {
		this.quizId = quiz_id;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = quizId.intValue();
		result = prime * result + (( createdBy.getId()== null) ? 0 : createdBy.getId().hashCode());
		return result;
	}
	
	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public Boolean getIsBulkOrder() {
		return isBulkOrder;
	}

	public void setIsBulkOrder(Boolean isBulkOrder) {
		this.isBulkOrder = isBulkOrder;
	}

}
