package onlinepractice.rv.quizzz.model.support;

public class PaymentGatewayResponse {
	
	String pgPaymentId;
	String pgPaymetStatus;
	String pgId;
	String pgtxnId;
	
	public String getPgPaymentId() {
		return pgPaymentId;
	}
	public String getPgPaymetStatus() {
		return pgPaymetStatus;
	}
	public String getPgId() {
		return pgId;
	}
	public String getPgtxnId() {
		return pgtxnId;
	}
	public void setPgPaymentId(String pgPaymentId) {
		this.pgPaymentId = pgPaymentId;
	}
	public void setPgPaymetStatus(String pgPaymetStatus) {
		this.pgPaymetStatus = pgPaymetStatus;
	}
	public void setPgId(String pgId) {
		this.pgId = pgId;
	}
	public void setPgtxnId(String pgtxnId) {
		this.pgtxnId = pgtxnId;
	}
	

}
