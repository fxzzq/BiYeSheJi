package com.xzq.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xzq.dao.StudentRepository;
import com.xzq.entity.Assignment;
import com.xzq.entity.Examquestions;
import com.xzq.entity.Mistakes;
import com.xzq.entity.Student;
@Repository
public class StudentRepositoryImpl implements StudentRepository{
	@Autowired
    private SessionFactory sessionFactory;
	
    private Session getCurrentSession() {
    	//spring来管理事务 getCurrentSession
    	//hibernate自己管理使用sessionFactory.openSession()
        return this.sessionFactory.getCurrentSession();
    }

    @Override
	public List<Student> selectstudentByAccount(String sql, Object... args) {
    	Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.setString(0, (String) args[0]);  
		query.addEntity(Student.class);// 为了返回list
		List<Student> list = query.list();
		return list;
	}
    
    
	@Override
	public List<Assignment> selectpracticeList(String sql, Object... args) {
		Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
	    //接收termService.getSdate(),termService.getEdate(),
    	//account,type,type,("%"+type+"%"),start,limit
		
			query.setString(0, (String) args[0]);
			query.setString(1, (String) args[1]);
			query.setString(2, (String) args[2]);
			query.setString(3, (String) args[3]);
			query.setString(4, (String) args[4]);
			query.setString(5, (String) args[5]);
			query.setLong(6, (Long) args[6]);
			query.setLong(7, (Long) args[7]);
		query.addEntity(Examquestions.class);// 为了返回list
		List<Assignment> list = query.list();
		return list;
	}

	@Override
	public List<Assignment> selectpracticeDetail(String sql, Object... args) {
		Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.setString(0, (String) args[0]);  //account
		query.setString(1, (String) args[1]);  //id
		query.addEntity(Examquestions.class);// 为了返回list
		List<Assignment> list = query.list();
		return list;
	}

	@Override
	public List selectStudentIsAnswer(String sql, Object... args) {
		Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.setString(0, (String) args[0]); //id 
		query.setString(1, (String) args[1]); //account
		return query.list();
	}

	@Override
	public void updatemistakes(String sql, Object... args) {
		
		
	}

	@Override
	public void updateexamqusetions(String sql, List<Object[]> params) {
		
		
	}

	@Override
	public void updateassigndetail(String sql, List<Object[]> params) {
		
		
	}

	@Override
	public List<Assignment> selectassignmentAnswer(String sql, Object... args) {
		
		return null;
	}

	@Override
	public List<Assignment> selectworkList(String sql, Object... args) {
	
		return null;
	}

	@Override
	public List selectMistakeType(String sql, Object... args) {
		
		return null;
	}

	@Override
	public List<Mistakes> selectMistakes(String sql, Object... args) {
		
		return null;
	}

	@Override
	public List<Mistakes> selectmistakeList(String sql, Object... args) {
		
		return null;
	}

	@Override
	public void deleteMistake(String sql, Object... args) {
	
		
	}

	@Override
	public List<Mistakes> selectMistakeDetail(String sql, Object... args) {
		
		return null;
	}

	@Override
	public void updatemistakesByasync(String sql, List<Object[]> params) {
		
	}

	@Override
	public void savemistakesforteacher(String sql, Object... args) {
		
		
	}

	@Override
	public void savestudent(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //account
		 query.setString(1,(String)args[1]);   //name
		 query.setString(2,(String)args[2]);   //encodepwd
		 query.executeUpdate();
	}
	@Override
	public void savestuclass(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //account
		 query.setString(1,(String)args[1]);   //classid  
		 query.executeUpdate();
	}

	
    

}
