package com.xzq.entity;
/**
 * 教师使用
 * @author ACT
 *
 */
public class Stumistakes {
	private String id;
	private String sname; //学生姓名
	private String type;  //试题类型
	private String rightnum; 
	private String wrongnum;
	private String ctime;
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRightnum() {
		return rightnum;
	}
	public void setRightnum(String rightnum) {
		this.rightnum = rightnum;
	}
	public String getWrongnum() {
		return wrongnum;
	}
	public void setWrongnum(String wrongnum) {
		this.wrongnum = wrongnum;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Stumistakes() {
		super();
	}
	
	public Stumistakes(String id, String sname, String type, String rightnum, String wrongnum, String ctime) {
		super();
		this.id = id;
		this.sname = sname;
		this.type = type;
		this.rightnum = rightnum;
		this.wrongnum = wrongnum;
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "Stumistakes [id=" + id + ", sname=" + sname + ", type=" + type + ", rightnum=" + rightnum
				+ ", wrongnum=" + wrongnum + ", ctime=" + ctime + "]";
	}
	
   
}
