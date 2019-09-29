package onlinepractice.rv.quizzz.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "UserPayment")
public class UserPayment extends BaseModel {
	

	@OneToOne
	@JsonIgnore
	UserOrder userOrder;

	private Long pgOrderId;

	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
	@JsonIgnore
	private Calendar createdDate;
	

	private String pgTxnId;
	
	private String pgOrderStatus;
	
	private String pgId;
	
	private String pgPaymentId;
	
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public UserOrder getUserOrder() {
		return userOrder;
	}

	public Long getPgOrderId() {
		return pgOrderId;
	}

	public String getPgTxnId() {
		return pgTxnId;
	}

	public String getPgOrderStatus() {
		return pgOrderStatus;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public void setUserOrder(
			UserOrder studentenrollmentRequerst) {
		this.userOrder = studentenrollmentRequerst;
	}

	public void setPgOrderId(Long pgOrderId) {
		this.pgOrderId = pgOrderId;
	}

	public void setPgTxnId(String pgTxnId) {
		this.pgTxnId = pgTxnId;
	}

	public void setPgOrderStatus(String pgOrderStatus) {
		this.pgOrderStatus = pgOrderStatus;
	}

	public String getPgId() {
		return pgId;
	}

	public String getPgPaymentId() {
		return pgPaymentId;
	}

	public void setPgId(String pgId) {
		this.pgId = pgId;
	}

	public void setPgPaymentId(String pgPaymentId) {
		this.pgPaymentId = pgPaymentId;
	}

}
