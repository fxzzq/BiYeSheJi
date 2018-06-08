package com.xzq.entity;


public class Termlist {

	private Integer id;
	private String sdate;
	private String edate;
	private String name;
	private String iscurrent;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIscurrent() {
		return iscurrent;
	}
	public void setIscurrent(String iscurrent) {
		this.iscurrent = iscurrent;
	}
	
	public Termlist() {
	
	}
	public Termlist(Integer id, String sdate, String edate, String name, String iscurrent) {
		this.id = id;
		this.sdate = sdate;
		this.edate = edate;
		this.name = name;
		this.iscurrent = iscurrent;
	}
	@Override
	public String toString() {
		return "Termlist [id=" + id + ", sdate=" + sdate + ", edate=" + edate + ", name=" + name + ", iscurrent="
				+ iscurrent + "]";
	}
	
	
}
