package com.xzq.service;

import java.util.List;
import java.util.Map;



public interface TeacherService {
	
	public String teaLogin(Map<String, Object> map);

	public String teaRegister(Map<String, Object> map);
	
	public String getMyQuestionsList(Map<String, Object> map);
	
	public String getQuestionsList(); 
	
	public String getQuestionDetail(Map<String, Object> map);

	public String delQuestion(Map<String, Object> map); 

	public String getTypes(); 

	public String getQuestionsByType(Map<String, Object> map); 

	public String delPaper(Map<String, Object> map); 

	public String addPaper(Map<String, Object> map);

	public String getMyPaperList(Map<String, Object> map);
	
	public String getPaperDetail(Map<String, Object> map);

	public String getMyClass();

	public String arrange(Map<String, Object> map);

	public String getArrangeList(Map<String, Object> map);

	public String getTerms();

	public String getTermStatistics(Map<String, Object> map);

	public String delArrange(Map<String, Object> map);

	public String getPaperStatistics(Map<String, Object> input);

	public String getOnlyPaperList(Map<String, Object> map);

	public String getClassStuInfo(Object classid);

	public String getStuMistakeList(Map<String, Object> map);

}
