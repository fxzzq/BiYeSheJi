package com.xzq.entity;

public class Student {

	private String  account;
	private String name;
	private String pwd;
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccount() {
		return account;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPwd() {
		return pwd;
	}
     public Student() {
		
	}
	public Student(String account, String name, String pwd) {
		
		this.account = account;
		this.name = name;
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "Student [account=" + account + ", name=" + name + ", pwd=" + pwd + "]";
	}
     
}
