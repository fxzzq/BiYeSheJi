package com.xzq.entity;

import java.util.Date;



public class Assignment {
	private Integer id;
	private String name;
	private String creator;
	private String type;
	private String ctime;
	private String stime;
	private String etime;
	private Date date;
	private Integer  isdel;
	private String paper;
	private String Info;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public String getPaper() {
		return paper;
	}
	public void setPaper(String paper) {
		this.paper = paper;
	}
	public String getInfo() {
		return Info;
	}
	public void setInfo(String info) {
		Info = info;
	}
	public Assignment() {

	}
	public Assignment(Integer id, String name, String creator, String type, String ctime, String stime, String etime,
			Date date, Integer isdel, String paper, String info) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		this.type = type;
		this.ctime = ctime;
		this.stime = stime;
		this.etime = etime;
		this.date = date;
		this.isdel = isdel;
		this.paper = paper;
		Info = info;
	}
	@Override
	public String toString() {
		return "Assignment [id=" + id + ", name=" + name + ", creator=" + creator + ", type=" + type + ", ctime="
				+ ctime + ", stime=" + stime + ", etime=" + etime + ", date=" + date + ", isdel=" + isdel + ", paper="
				+ paper + ", Info=" + Info + "]";
	} 
	
	
}
