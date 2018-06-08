package com.xzq.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xzq.dao.TeacherRepository;
import com.xzq.entity.Assignment;
import com.xzq.entity.Assignmentdetail;
import com.xzq.entity.Classlist;
import com.xzq.entity.Examquestions;
import com.xzq.entity.Paper;
import com.xzq.entity.Student;
import com.xzq.entity.Stumistakes;
import com.xzq.entity.Teacher;
import com.xzq.entity.Termlist;


@Repository
public class TeacherRepositoryImpl implements TeacherRepository {

	@Autowired
    private SessionFactory sessionFactory;
	
    private Session getCurrentSession() {
    	//spring来管理事务 getCurrentSession
    	//hibernate自己管理使用sessionFactory.openSession()
        return this.sessionFactory.getCurrentSession();
    }
	@Override
	public List<Examquestions> selectExamQuestionsList(String sql,Object...args) {
		//底层使用Hibernate
		
		Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
	    //接收args
		
			query.setString(0, (String) args[0]);
			query.setString(1, (String) args[1]);
			query.setString(2, (String) args[2]);
			query.setString(3, (String) args[3]);
			query.setString(4, (String) args[4]);
			query.setString(5, (String) args[5]);
			query.setString(6, (String) args[6]);
			query.setLong(7, (Long) args[7]);
			query.setLong(8, (Long) args[8]);
		
		query.addEntity(Examquestions.class);// 为了返回list

		List<Examquestions> list = query.list();
		return list;
	}

	@Override
	public List<Examquestions> selectExamQuestionList(String sql) {
	   Session session = getCurrentSession();
	   List<Examquestions> list = session.createSQLQuery(sql).addEntity(Examquestions.class).list();
		System.out.println(list);
	   return list;
	}

	@Override
	public List<Examquestions> selectByPrimaryKey(String sql, Object... args) {
	     Session session = getCurrentSession();
	     SQLQuery query = session.createSQLQuery(sql);
	     query.setString(0, (String) args[0]);
	     List<Examquestions> list =query.addEntity(Examquestions.class).list();
	     System.out.println(list);
		return list;
	}

	@Override
	public List<Paper> selectExamPaperList(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString(0, (String) args[0]);
		query.setString(1, (String) args[1]);
		query.setString(2, (String) args[2]);
		query.setString(3, (String) args[3]);
		query.setString(4, (String) args[4]);
		query.setString(5, (String) args[5]);
		query.setString(6, (String) args[6]);
		query.setLong(7, (Long) args[7]);
		query.setLong(8, (Long) args[8]);
		List<Paper> list = query.addEntity(Paper.class).list();
		return list;
	}

	@Override
	public List<Paper> selectPaperByPrimaryKey(String sql, Object... args) {
		 Session session = getCurrentSession();
	     SQLQuery query = session.createSQLQuery(sql);
	     query.setString(0, (String) args[0]);
	     List<Paper> list =query.addEntity(Paper.class).list();
		return list;
	}

	@Override
	public void deleteExamQuestions(String sql, Examquestions obj) {
		 //事务开启交给spring
		 Session session = getCurrentSession();
	     session.delete(obj);	
	}

	@Override
	public void deleteExamPaper(String sql, Paper obj) {
		 Session session = getCurrentSession();
	     session.delete(obj);		
	}

	@Override
	public List selectExamByTypes(String hql, Object... args) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		query.setString(0, (String) args[0]);
		List<String> list = query.list();
		return list ;
	}
	@Override
	public void savePaper(String sql, Object... args) {
		Session session = getCurrentSession();
		//保存到Paper对象
		//Paper paper =new Paper();	
		//paper.setName((String)args[0]);
		//paper.setType((String) args[1]);
		//paper.setCreator((String) args[2]);
		//UUID 基于 16 进制，由 32 位小写的 16 进制数字组成，如下：
		//aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee
		//paper.setUuid(uuid);
		//把试题添加到questions
		//paper.setQuestions(questions);
		//session.save(paper);

		SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //试卷名称
		 query.setString(1,(String)args[1]);   //试卷类型
		 query.setString(2,(String)args[2]);   //新增试卷老师账号
		 String[] uuids= (String[]) args[3];    //所有编辑的试题
		 query.setString(3, (String) args[4]);   //第一个试题
		for(int i=1;i<uuids.length;i++) {
			query.setString(i+3, uuids[i]);			
		}
		
		query.executeUpdate();
	}
	@Override
	public List<Examquestions> selectByType(String hql, Object... args) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
	/*	query.setString(0, (String) args[0]);  //type
		query.setString(1, (String) args[1]);  //account,
		query.setString(2, (String) args[2]);  //start
		query.setString(3, (String) args[3]);   //limit
*/		List<Examquestions> list = query.list();
		System.out.println(list);
		return list ;
	}
	@Override
	public List<Assignment> selectAssignment(String sql, Object... args) {
		 Session session = getCurrentSession();
		 SQLQuery query = session.createSQLQuery(sql);
		    query.setString(0, (String) args[0]);   // account
			query.setString(1, (String) args[1]);  //termService.getSdate()
			query.setString(2, (String) args[2]);  //termService.getEdate()
			query.setLong(3, (Long) args[3]); //start
			query.setLong(4, (Long) args[4]);//limitS
		 List list = query.addEntity(Assignment.class).list();
		return list;
	}
	@Override
	public List<Termlist> selectTermsAll(String sql) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		List<Termlist> list = query.addEntity(Termlist.class).list();
		return list;
	}
	@Override
	public List<Classlist> selectClass(String sql) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		List<Classlist> list = query.addEntity(Classlist.class).list();
		return list;
	}
	@Override
	public List<Paper> selectOnlyPaperList(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString(0, (String) args[0]);
		List<Paper> list = query.addEntity(Paper.class).list();
		return list;
	}
	@Override
	public List selectStuInfo(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setString(0, (String) args[0]);
		List<Student> list = query.addEntity(Student.class).list();
		for(Student u:list) {
			System.out.println(u);
			}
		return list;
	}
	@Override
	public void saveAssignment(String sql, Object... args) {
		 Session session = getCurrentSession();
		 SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //account
		 query.setString(1,(String)args[1]);   //name
		 query.setString(2,(String)args[2]);   //type
		 query.setString(3,(String)args[3]);   //ctime
		 query.setString(4,(String)args[4]);  //stime
		 query.setString(5,(String)args[5]);	//etime
		 query.setString(6,(String)args[6]);	//sdate
		 query.setInteger(7,(Integer)args[7]);     //isdel
		 query.setString(8,(String)args[8]);	// uuid
		 query.setString(9,(String)args[9]);	//info
		 query.executeUpdate();
	}
	@Override
	public int selectAssignmentByAccountAndCtime(String sql, Object... args) {
		 Session session = getCurrentSession();
		 SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0, (String) args[0]);
		 query.setString(1, (String) args[1]);
		 int assignment =(int)query.uniqueResult();
		 System.out.println("----作业id--------------"+assignment);
		return assignment;
	}
	@Override
	public void saveAssignmentdetail(String sql, Object... args) {
		 //批处理  --批量插入
		//遍历布置作业老师的所有学生
		 Session session = getCurrentSession();
		 SQLQuery query = session.createSQLQuery(sql);
		 int count=0;
		 System.out.println("args[0]--assingid:"+args[0]);
		 System.out.println("args[1]:"+args[1]);
		 System.out.println("args[2]--classid:"+args[2]);
         for(Map<String, Object> map : (List<Map<String, Object>>)args[1]) {
        	  
        	 query.setInteger(0, (Integer) args[0]);     //assingid
     		 query.setString(1, (String) map.get("account"));
     		 query.setString(2, (String) map.get("name"));
     		 query.setString(3, (String) args[2]);     //classid
     		 query.executeUpdate(); 
     		 //每当累加器是20的倍数的时候，将session中数据刷入数据库，并且清空session缓存  
     		 if(count%20==0) {
     			session.flush();  
                session.clear();
     		 }
          }	
	}
	@Override
	public List<Assignmentdetail> selectAssigndetailById(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setInteger(0, (int) args[0]);
		List<Assignmentdetail> list = query.addEntity(Assignmentdetail.class).list();
		return list;
	}
	@Override
	public List selctTermStatistics(String hql, Object... args) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		//account,classid,sdate,edate
		query.setString(0, (String) args[0]);
		query.setInteger(1, (int) args[1]);
		query.setString(2, (String) args[2]);
		query.setString(3, (String) args[3]);
		List<Object[]> list = query.list();
		return list ;
		
	}
	@Override
	public List<Stumistakes> selectStuMistakeList(String sql, Object... args) {
		
		return null;
	}
	@Override
	public void saveteacher(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //account
		 query.setString(1,(String)args[1]);   //name
		 query.setString(2,(String)args[2]);   //encodepwd
		 query.executeUpdate();
	}
	@Override
	public void saveclasstea(String sql, Object... args) {
		Session session = getCurrentSession();
		SQLQuery query = session.createSQLQuery(sql);
		 query.setString(0,(String)args[0]);   //account
		 query.setString(1,(String)args[1]);   //classid  
		 query.executeUpdate();
	}
	@Override
	public List<Teacher> selectteacherByAccount(String sql, Object... args) {
		Session session = getCurrentSession();
    	SQLQuery query = session.createSQLQuery(sql);
    	query.setString(0, (String) args[0]);  
		query.addEntity(Teacher.class);// 为了返回list
		List<Teacher> list = query.list();
		return list;
	}


}
