package com.xzq.utils;

public class Configuration {
	private String classListUrl;   //班级列表Url
	
	private String classStudentsUrl;//班级学生Url
	
	private Long deadline;          
	
	private String termsUrl;        //学期Url
	
	private String zipTemplate;
	
	private String excelTemplate;

	public String getClassListUrl() {
		return classListUrl;
	}

	public void setClassListUrl(String classListUrl) {
		this.classListUrl = classListUrl;
	}

	public String getClassStudentsUrl() {
		return classStudentsUrl;
	}

	public void setClassStudentsUrl(String classStudentsUrl) {
		this.classStudentsUrl = classStudentsUrl;
	}

	public Long getDeadline() {
		return deadline;
	}

	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	public String getTermsUrl() {
		return termsUrl;
	}

	public void setTermsUrl(String termsUrl) {
		this.termsUrl = termsUrl;
	}

	public String getZipTemplate() {
		return zipTemplate;
	}

	public void setZipTemplate(String zipTemplate) {
		this.zipTemplate = zipTemplate;
	}

	public String getExcelTemplate() {
		return excelTemplate;
	}

	public void setExcelTemplate(String excelTemplate) {
		this.excelTemplate = excelTemplate;
	}

}
