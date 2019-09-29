package onlinepractice.rv.quizzz.model.support;

import java.util.List;

public class Response {
	private Long question;
	private List<Long> selectedAnswers;

	public Long getQuestion() {
		return question;
	}

	public void setQuestion(Long question) {
		this.question = question;
	}


	public List<Long> getSelectedAnswers() {
		return selectedAnswers;
	}

	public void setSelectedAnswers(List<Long> selectedAnswers) {
		this.selectedAnswers = selectedAnswers;
	}

}
