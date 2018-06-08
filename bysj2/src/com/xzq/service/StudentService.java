package com.xzq.service;

import java.util.Map;

public interface StudentService {
	
	public String stuLogin(Map<String, Object> map);
	
	public String stuRegister(Map<String, Object> map);
	
	public String practiceList(Map<String, Object> map);

	public String workList(Map<String, Object> map);

	public String mistakeList(Map<String, Object> map);

	public String deleteMistake(Map<String, Object> map);

	public String practiceDetail(Map<String, Object> map);

	public String getMistakeType();

	public String getMistakes(Map<String, Object> map);

	public String getMistakeDetail(Map<String, Object> map);

	public String doPractice(Map<String, Object> map);

	public String finishPractice(Map<String, Object> map);

	public String asyncMistakes(Map<String, Object> map);



}
