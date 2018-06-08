package com.xzq.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xzq.dao.StudentRepository;
import com.xzq.entity.Assignment;
import com.xzq.entity.Mistakes;
import com.xzq.entity.Student;
import com.xzq.service.StudentService;
import com.xzq.service.TermService;
import com.xzq.utils.JSONUtil;
import com.xzq.utils.MD5Util;
import com.xzq.utils.Result;
@Service
public class StudentServiceImpl  implements StudentService{
	
	@Autowired
	private StudentRepository  studentRepository;
	
	@Autowired 
	private TermService termService;
	//学生account
	private String CURRENT_LOGIN_ACCOUNT_STU="";
	//学生name
	 private String CURRENT_LOGIN_name_STU="";
	 /**
	  * 学生登录 
	  * 
	  */
	 @Override
		public String stuLogin(Map<String, Object> map) {
		    Result result = new Result();
		    String account=map.get("account").toString();
		   //加密
		    MD5Util md5=new MD5Util();
		    String pwd=md5.md5(map.get("pwd").toString());	
		    String  pwd2=" "; 
		    String sname="";
		    String sql = "SELECT  *  FROM student  where account=?  " ;
		    List<Student> list= studentRepository.selectstudentByAccount(sql,account);
		     for (Student student : list) {
		    	 pwd2= student.getPwd();
		    	 sname = student.getName();
		    	 if(pwd2.equals(pwd)){
		        		result.setEmsg("登录成功");
		       	    	result.setData(list);
		       	    	result.setSuccess(true);
		       	    	CURRENT_LOGIN_ACCOUNT_STU=account;
		       	    	CURRENT_LOGIN_name_STU=sname;
		        	}
		        	else{
		        		   result.setEmsg("登录失败");
		        		   result.setSuccess(false);
		        	   }
			}
		return JSONUtil.toJson(result);	
		}
	 /**
	  * 学生注册
	  */
		@Override
		public String stuRegister(Map<String, Object> map) {
			 Result result = new Result();
			 Object account=map.get("account");
			 Object name=map.get("name");
			 Object pwd=map.get("pwd");
			 Object  classid=map.get("classid");
			 //加密
			 MD5Util md5=new MD5Util();
			 String	encodepwd= md5.md5((String)pwd);
			 result.setSuccess(false);
				if(account == null || "".equals(account.toString())||name==null|| "".equals(name.toString())||pwd==null|| "".equals(pwd.toString())||classid==null|| "".equals(classid.toString())) {
					result.setEmsg("参数传递失败,注册失败！");
					return JSONUtil.toJson(result);
				}
				//事务
			 String  sql="replace into student(account,name,pwd) values(?,?,?)";
			 String sql2="replace into stuclass(stuaccount,classid) values(?,?)";
			 studentRepository.savestudent(sql,account,name,encodepwd);
			 studentRepository.savestuclass(sql2,account,classid);
			 result.setEmsg("注册成功");
			 result.setSuccess(true);
			 return JSONUtil.toJson(result);
		}
	 
	/**
	 * 随堂练习列表
	 */
	@Override
	public String practiceList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String type = map.get("keyword")==null?"":map.get("keyword").toString();
    	long start = 0;
    	long limit = 25;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
    	if (termService.needUpdate()) {
			termService.update();
		}
    	//是否完成 交给前台  通过 answer是否为空（isfinish）
    	String sql = "select A.* FROM assignment A LEFT JOIN assignmentdetail D ON A.ID=D.assingid "
    			+ "WHERE A.type='随堂练习' and A.date between ? and ? and D.ACCOUNT=? AND (CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE A.`name` LIKE ? END) order by A.ctime desc LIMIT ?,?";
    	
    	List<Assignment> list = studentRepository.selectpracticeList(sql, termService.getSdate(),termService.getEdate(),account,type,type,("%"+type+"%"),start,limit);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 
	 *课后作业列表
	 */
	@Override
	public String workList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String type = map.get("keyword")==null?"":map.get("keyword").toString();
    	long start = 0;
    	long limit = 25;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			result.setEmsg("参数传递失败!");
			return JSONUtil.toJson(result);
		}
    	if (termService.needUpdate()) {
			termService.update();
		}
    	////是否完成 交给前台  通过 answer是否为空（isfinish）
    	String sql = "select A.* FROM assignment A LEFT JOIN assigndetail D ON A.ID=D.assingid "
    			+ "WHERE A.type='课后作业' and A.sdate between ? and ? and D.ACCOUNT=? and (CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE A.`name` LIKE ? END) order by A.ctime desc LIMIT ?,?";
    	List<Assignment> list=studentRepository.selectworkList(sql,termService.getSdate(),termService.getEdate(),account,type,type,("%"+type+"%"),start,limit);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSONUtil.toJson(result);
	}
	/**
	 * 错题集首页列表
	 * 
	 */
	@Override
	public String mistakeList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	long start = 0;
    	long limit = 25;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			result.setEmsg("参数传递失败!");
			return JSONUtil.toJson(result);
		}
    	String sql = "SELECT * FROM MISTAKES where account = ? ORDER BY lasttime desc LIMIT ?,?";
    	List<Mistakes> list=studentRepository.selectmistakeList(sql,account,start,limit);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSONUtil.toJson(result);
	}
	/**
	 * 
	 * 删除错题集里试题，不再练习
	 */
	@Override
	public String deleteMistake(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object uuid = map.get("uuid");
    	if(uuid == null || "".equals(uuid.toString())) {
    		result.setEmsg("参数传递失败!");
			return JSONUtil.toJson(result);
    	}
    	String sql = "delete from MISTAKES where account = ? and uuid = ?";
    	//事务开启
    	studentRepository.deleteMistake(sql,account,uuid);
    	result.setEmsg("删除成功");
    	result.setSuccess(true);
    	return JSONUtil.toJson(result);
	}
	/**
	 * 加载做练习页面
	 */
	@Override
	public String practiceDetail(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object id = map.get("id");
    	if(id == null || "".equals(id.toString())) {
    		result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
    	}
    	String sql = "select * from assignment A left join " + 
    			"assignmentdetail D on A.id = D.assingid " + 
    			"where D.account = ? and A.id = ?";
    	List<Assignment> list = studentRepository.selectpracticeDetail(sql,account,id);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 获取错题集类型(错题的课程名)
	 * 
	 */
	@Override
	public String getMistakeType() {
		Result result = new Result();
		String account = "001";
		account = CURRENT_LOGIN_ACCOUNT_STU;
		String sql = "SELECT DISTINCT type as name from mistakes where account = ?";
		List list=studentRepository.selectMistakeType(sql,account);
		result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 
	 * 开始错题练习
	 */
	@Override
	public String getMistakes(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object type = map.get("type") == null?"":map.get("type");
    	Object condition = map.get("condition") == null?"":map.get("condition");
    	Object taccount = map.get("taccount") == null?"":map.get("taccount");
    	String sql = "SELECT * from MISTAKES where account = ? and creator=? and "
    			+ "(case when (? is null or ? = '') then 1=1 else type like ? end and case when (? is null or ? = '') then 1=1 else lastanswer = ? end) order by lasttime desc";
    	List<Mistakes> list = studentRepository.selectMistakes(sql,account,taccount,type,type,"%"+type+"%",condition,condition,condition);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
    	return JSON.toJSONString(result);
	}
	/**
	 * 
	 * 查看错题列表的错题详情（试题详情）
	 */
	@Override
	public String getMistakeDetail(Map<String, Object> map) {
		Result result = new Result();
		Object uuid = map.get("uuid");
		String account = "001";
		account = CURRENT_LOGIN_ACCOUNT_STU;
		result.setSuccess(false);
		if(uuid == null || "".equals(uuid.toString())) {
			result.setEmsg("参数传递失败!");
			return JSONUtil.toJson(result);
		}
		String sql = "select * from MISTAKES where uuid=? and account = ?";
		List<Mistakes> list=studentRepository.selectMistakeDetail(sql,uuid,account); 
		result.setData(list);
		result.setEmsg("查询成功");
		result.setSuccess(true);
		return JSONUtil.toJson(result);
	}
   /**
    * 做练习完提交答案 
    */
	@Override
	public String doPractice(Map<String, Object> map) {

		Result result = new Result();
		Object dataObj = map.get("data");
		Object id = map.get("id");
		String account = "001";
		account = CURRENT_LOGIN_ACCOUNT_STU;
		if (dataObj == null || id == null) {
			result.setSuccess(false);
			result.setEmsg("同步答题失败!");
			return JSON.toJSONString(result);
		}
		List<Object> data = null;
		try {
			data = JSONUtil.parseJson2List(dataObj.toString());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setEmsg("同步答题失败!");
			return JSON.toJSONString(result);
		}
		//首先查询该生是否答题
		String sql = "select answer from assignmentdetail where assingid = ? and account = ?";
	     List list = studentRepository.selectStudentIsAnswer(sql,id,account);
			if (list != null && !"".equals(list.toString())) {
				result.setSuccess(false);
				result.setEmsg("已答题，不能重复作答!");
				return JSON.toJSONString(result);
			}
		List<Object[]> params2 = new ArrayList<Object[]>();
		List<Object[]> params3 = new ArrayList<Object[]>();
		int total = 0;
		int right = 0;
		int wrong = 0;
		for (Object object : data) {
			Object uuid = ((Map)object).get("uuid");
			Object isrightobj = ((Map)object).get("isright");
			int isright = 0;
			int iswrong = 0;
			total ++;
			if ((Boolean) isrightobj) {
				right++;
				isright = 1;
			}
			else {
				wrong ++;
				iswrong = 1;
				params2.add(new Object[] {account,isright,uuid});
			}
			
			params3.add(new Object[] {iswrong,isright,uuid});
		}
		String sql2 = "REPLACE into mistakes(`uuid`,`creator`,`account`,`type`,`stem`,`content`,`analysis`,`answer`,`multi`,`lastanswer`,`lasttime`) (select `uuid`,`creator`,?,`type`,`stem`,`content`,`analysis`,`answer`,`multi`,?,now() from tb_proj_examinations_examqusetions where uuid=?)";
		String sql3 = "update examqusetions set total=total+1,wrongnum=wrongnum+?,rightnum=rightnum+? where uuid=?";
		String sql4 = "update assignmentdetail set answer = ?,total=?,wrongnum=?,rightnum=? where assingid = ? and account = ?";
		//事务
		studentRepository.updatemistakes(sql2,dataObj,total,wrong,right,id,account);
		studentRepository.updateexamqusetions(sql3,params2);
		studentRepository.updateassigndetail(sql4,params3);
		result.setEmsg("更新答题数据成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}

	/**
	 * 
	 * 练习阅卷结果展示
	 */
	@Override
	public String finishPractice(Map<String, Object> map) {
		Result result = new Result();
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object id = map.get("id");
    	if(id == null || "".equals(id.toString())) {
    		result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
    	}
    	String sql = "select *  from assignment A left join " + 
    			"assignmentdetail D on A.id = D.assingid " + 
    			"where D.account = ? and A.id = ?";
    	//Object[]param = new Object[] {};
    	//QueryResult queryResult = processor.routeTo(JdbcProcessor.class).jdbcQuery(sql, param).preValue(QueryResult.class);
    	//List<Map<String, Object>> data = queryResult.getData();
        //多表操作  有问题
    	List<Assignment> list=studentRepository.selectassignmentAnswer(sql,account,id);
    	result.setData(list);
    	result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSONUtil.toJson(result);
	}
	/**
	 * 
	 * 同步错题练习的答案
	 * 同步作答结果
	 */
	@Override
	public String asyncMistakes(Map<String, Object> map) {
		Result result = new Result();	
    	result.setSuccess(false);
    	String account = "001";
    	account = CURRENT_LOGIN_ACCOUNT_STU;
    	Object datasObj = map.get("data");
    	if (datasObj == null || "".equals(datasObj.toString())) {
    		result.setEmsg("参数传递失败!");
    		return JSONUtil.toJson(result);
		}
    	List<Object> data = JSONUtil.parseJson2List(datasObj.toString());
    	List<Object[]> params = new ArrayList<Object[]>();
    	int wrongnum = 0;
    	int rightnum = 0;
    	for (Object object : data) {
			Object uuid = ((Map)object).get("uuid");
			Object iswrongobj = ((Map)object).get("iswrong");
			if (Integer.parseInt(iswrongobj.toString()) == 0) {
				wrongnum++;
			}
			else {
				rightnum++;
			}
			Object[]param = new Object[] {iswrongobj,uuid,account};
			params.add(param);
		}
    	Object taccount = map.get("taccount");
    	//获取学生name
    	Object sname =CURRENT_LOGIN_name_STU;
    	Object type = map.get("type");
    	String sql = "update mistakes set lastanswer = ? where uuid=? and account = ?";
    	//给老师看学生的错题
    	String sql2 = "insert into mistakesforteacher(`taccount`,`saccount`,`sname`,`ctime`,`type`,`wrongnum`,`rightnum`) values(?,?,?,now(),?,?,?)";
    	//事务
    	studentRepository.updatemistakesByasync(sql, params);
    	studentRepository.savemistakesforteacher(sql2,taccount,account,sname,type,wrongnum,rightnum);
    	result.setEmsg("更新插入成功");
    	result.setSuccess(true);
    	return JSONUtil.toJson(result);
	}

}
