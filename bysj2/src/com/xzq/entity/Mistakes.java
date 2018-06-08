package com.xzq.entity;



public class Mistakes {
	private String uuid;
	private String account;
	private String type;
	private String stem;
	private String content;
	private String analysis;
	private String answer;
	private Integer multi;
	private Integer lastanswer;
	private String lasttime;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public Integer getLastanswer() {
		return lastanswer;
	}
	public void setLastanswer(Integer lastanswer) {
		this.lastanswer = lastanswer;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	public Mistakes() {

	}
	public Mistakes(String uuid, String account, String type, String stem, String content, String analysis,
			String answer, Integer multi, Integer lastanswer, String lasttime) {
		this.uuid = uuid;
		this.account = account;
		this.type = type;
		this.stem = stem;
		this.content = content;
		this.analysis = analysis;
		this.answer = answer;
		this.multi = multi;
		this.lastanswer = lastanswer;
		this.lasttime = lasttime;
	}
	@Override
	public String toString() {
		return "Mistakes [uuid=" + uuid + ", account=" + account + ", type=" + type + ", stem=" + stem + ", content="
				+ content + ", analysis=" + analysis + ", answer=" + answer + ", multi=" + multi + ", lastanswer="
				+ lastanswer + ", lasttime=" + lasttime + "]";
	}
	
}
