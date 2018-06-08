package com.xzq.entity;

public class Paper {
	private String uuid;
	private  String name;
	private String type;
	private String creator;
	private String questions;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public Paper() {

	}
	public Paper(String uuid, String name, String type, String creator, String questions) {
		this.uuid = uuid;
		this.name = name;
		this.type = type;
		this.creator = creator;
		this.questions = questions;
	}
	@Override
	public String toString() {
		return "Paper [uuid=" + uuid + ", name=" + name + ", type=" + type + ", creator=" + creator + ", questions="
				+ questions + "]";
	}
	
}
