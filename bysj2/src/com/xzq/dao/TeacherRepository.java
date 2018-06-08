package com.xzq.dao;

import java.util.List;

import com.xzq.entity.Assignment;
import com.xzq.entity.Assignmentdetail;
import com.xzq.entity.Classlist;
import com.xzq.entity.Examquestions;
import com.xzq.entity.Paper;
import com.xzq.entity.Stumistakes;
import com.xzq.entity.Teacher;
import com.xzq.entity.Termlist;


public interface TeacherRepository {

	public List<Examquestions> selectExamQuestionsList(String sql, Object... args);

	public List<Examquestions> selectByPrimaryKey(String sql, Object... args);
	
	public List<Examquestions> selectExamQuestionList(String sql);

	public List<Paper> selectExamPaperList(String sql, Object... args);

	public List<Paper> selectPaperByPrimaryKey(String sql, Object... args);

	public void deleteExamQuestions(String sql, Examquestions obj);
	
	public void deleteExamPaper(String sql, Paper obj);
	
	public List selectExamByTypes(String hql,Object... args);

	public void savePaper(String sql, Object... args);

	public List<Examquestions> selectByType(String hql,Object... args);
	
	public List<Assignment> selectAssignment(String sql,Object... args);

	public List<Termlist> selectTermsAll(String sql);

	public List<Classlist> selectClass(String sql);

	public List<Paper> selectOnlyPaperList(String sql, Object... args);

	public List selectStuInfo(String sql,  Object... args);

	public void saveAssignment(String sql, Object... args);

	public int selectAssignmentByAccountAndCtime(String sql,Object... args);
	
	public void  saveAssignmentdetail(String sql, Object... args);

	public List<Assignmentdetail> selectAssigndetailById(String sql, Object... args);

	public List selctTermStatistics(String hql, Object... args);

	public 	List<Stumistakes>  selectStuMistakeList(String sql,Object... args);

	public void saveteacher(String sql,Object... args);

	public void saveclasstea(String sql,Object... args);

	public List<Teacher> selectteacherByAccount(String sql,Object... args);

}
