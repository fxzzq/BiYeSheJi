package com.xzq.entity;

public class Classtea {

	private Integer  id;
	private String teaid;
	private Integer classid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTeaid() {
		return teaid;
	}
	public void setTeaid(String teaid) {
		this.teaid = teaid;
	}
	public Integer getClassid() {
		return classid;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public Classtea() {
		
	}
	public Classtea(Integer id, String teaid, Integer classid) {
		this.id = id;
		this.teaid = teaid;
		this.classid = classid;
	}
	@Override
	public String toString() {
		return "Classtea [id=" + id + ", teaid=" + teaid + ", classid=" + classid + "]";
	}
    
}
