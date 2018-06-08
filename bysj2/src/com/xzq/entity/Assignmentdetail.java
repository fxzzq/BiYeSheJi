package com.xzq.entity;



public class Assignmentdetail {
	private Integer id;
	private Integer assingid;
	private  String account;
	private String name;
	private Integer classid;
	private String answer;
	private Integer total;
	private Integer wrongnum;
	private Integer rightnum;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAssingid() {
		return assingid;
	}
	public void setAssingid(Integer assingid) {
		this.assingid = assingid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	public Assignmentdetail() {

	}
	public Assignmentdetail(Integer id, Integer assingid, String account, String name, Integer classid, String answer,
			Integer total, Integer wrongnum, Integer rightnum) {
		this.id = id;
		this.assingid = assingid;
		this.account = account;
		this.name = name;
		this.classid = classid;
		this.answer = answer;
		this.total = total;
		this.wrongnum = wrongnum;
		this.rightnum = rightnum;
	}
	@Override
	public String toString() {
		return "Assignmentdetail [id=" + id + ", assingid=" + assingid + ", account=" + account + ", name=" + name
				+ ", classid=" + classid + ", answer=" + answer + ", total=" + total + ", wrongnum=" + wrongnum
				+ ", rightnum=" + rightnum + "]";
	}
	
	 }
