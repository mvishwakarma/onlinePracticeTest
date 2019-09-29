package onlinepractice.rv.quizzz.model.support;

import java.util.ArrayList;
import java.util.List;

import onlinepractice.rv.quizzz.model.CandidateResponse;

public class Result {
	private int totalQuestions = 0;
	private int correctQuestions = 0;
	
	private List<CandidateResponse> responseBundle = new ArrayList<CandidateResponse>();

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(int correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public void addAnswer(Boolean isCorrect) {
		totalQuestions++;
		if (isCorrect) {
			correctQuestions++;
		}
	}

	public List<CandidateResponse> getResponseBundle() {
		return responseBundle;
	}

	public void setResponseBundle(List<CandidateResponse> responseBundle) {
		this.responseBundle = responseBundle;
	}
	
	public void addResponseBundle(CandidateResponse response){
		getResponseBundle().add(response);
	}
}
