package com.sh.carexx.model.uc;

public class PlayCards {

	private Long id;

	private Long jushu;

	private String score;

	private String[] scores;

	private String operantScore;

	private String[] operantScores;

	private Byte status;

	private Byte isDeleted;

	public String[] getOperantScores() {
		return operantScores;
	}

	public void setOperantScores(String[] operantScores) {
		this.operantScores = operantScores;
	}

	public String[] getScores() {
		return scores;
	}

	public void setScores(String[] scores) {
		this.scores = scores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJushu() {
		return jushu;
	}

	public void setJushu(Long jushu) {
		this.jushu = jushu;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getOperantScore() {
		return operantScore;
	}

	public void setOperantScore(String operantScore) {
		this.operantScore = operantScore;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}
}