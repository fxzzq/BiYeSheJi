package com.xzq.entity;

public class Examquestions {

	private String uuid;
	private String creator;
	private String type;
	private String stem;
	private String content;
	private String analysis;
	private String answer;
	private Integer multi;
	private Integer total;
	private Integer wrongnum;
	private Integer rightnum;
	private Double rate;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getMulti() {
		return multi;
	}
	public void setMulti(Integer multi) {
		this.multi = multi;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getWrongnum() {
		return wrongnum;
	}
	public void setWrongnum(Integer wrongnum) {
		this.wrongnum = wrongnum;
	}
	public Integer getRightnum() {
		return rightnum;
	}
	public void setRightnum(Integer rightnum) {
		this.rightnum = rightnum;
	}
	
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Examquestions() {

	}
	public Examquestions(String uuid, String creator, String type, String stem, String content, String analysis,
			String answer, Integer multi, Integer total, Integer wrongnum, Integer rightnum, Double rate) {
		super();
		this.uuid = uuid;
		this.creator = creator;
		this.type = type;
		this.stem = stem;
		this.content = content;
		this.analysis = analysis;
		this.answer = answer;
		this.multi = multi;
		this.total = total;
		this.wrongnum = wrongnum;
		this.rightnum = rightnum;
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "Examquestions [uuid=" + uuid + ", creator=" + creator + ", type=" + type + ", stem=" + stem
				+ ", content=" + content + ", analysis=" + analysis + ", answer=" + answer + ", multi=" + multi
				+ ", total=" + total + ", wrongnum=" + wrongnum + ", rightnum=" + rightnum + ", rate=" + rate + "]";
	}

	
}
