package com.xzq.entity;

public class Stuclass {
	private String stuaccount;
	private Integer classid; 
	public void setStuaccount(String stuaccount) {
		this.stuaccount = stuaccount;
	}
	public String getStuaccount() {
		return stuaccount;
	}
	public void setClassid(Integer classid) {
		this.classid = classid;
	}
	public Integer getClassid() {
		return classid;
	}
	public Stuclass() {
	}
	public Stuclass(String stuaccount, Integer classid) {
		this.stuaccount = stuaccount;
		this.classid = classid;
	}
	@Override
	public String toString() {
		return "Stuclass [stuaccount=" + stuaccount + ", classid=" + classid + "]";
	}
	
}
