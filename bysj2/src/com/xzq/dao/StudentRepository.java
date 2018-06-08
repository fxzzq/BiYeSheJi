package com.xzq.dao;

import java.util.List;

import com.xzq.entity.Assignment;
import com.xzq.entity.Mistakes;
import com.xzq.entity.Student;

public interface StudentRepository {

	List<Assignment> selectpracticeList(String sql,Object... args);

	List<Assignment> selectpracticeDetail(String sql,Object... args);

	List selectStudentIsAnswer(String sql,Object... args);

	void updatemistakes(String sql,Object... args);
	
	void updatemistakesByasync(String sql, List<Object[]> params);

	void updateexamqusetions(String sql, List<Object[]> params);

	void updateassigndetail(String sql, List<Object[]> params);

	List<Assignment> selectassignmentAnswer(String sql,Object... args);

	List<Assignment> selectworkList(String sql,Object... args);

	List selectMistakeType(String sql, Object... args);

	List<Mistakes> selectMistakes(String sql,Object... args);

	List<Mistakes> selectmistakeList(String sql,Object... args);

	void deleteMistake(String sql,Object... args);

	List<Mistakes> selectMistakeDetail(String sql, Object... args);

	void savemistakesforteacher(String sql,Object... args);

	List<Student> selectstudentByAccount(String sql,Object... args);

	void savestudent(String sql,Object... args);

	void savestuclass(String sql,Object... args);

	

	
	
    
}
