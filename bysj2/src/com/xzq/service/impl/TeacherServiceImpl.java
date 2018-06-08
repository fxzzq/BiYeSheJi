package com.xzq.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
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
import com.xzq.service.TeacherService;
import com.xzq.service.TermService;
import com.xzq.utils.JSONUtil;
import com.xzq.utils.MD5Util;
import com.xzq.utils.Result;
import com.xzq.utils.TimeUtil;



@Service
public class TeacherServiceImpl implements TeacherService {
     
	@Autowired
	private  TeacherRepository  teacherRepository;
	@Autowired 
	private TermService termService;
	
 	private String CURRENT_LOGIN_ACCOUNT_TEA="";
 	private String CURRENT_LOGIN_name_TEA="";
 	/**
 	 * 
 	 * 教师登录
 	 */
	@Override
	public String teaLogin(Map<String, Object> map) {
	    Result result = new Result();
	    String account=map.get("account").toString();
	   //加密
	    MD5Util md5=new MD5Util();
	    String pwd=md5.md5(map.get("pwd").toString());	
	    String  pwd2=" "; 
	    String tname="";
	    String sql = "SELECT  *  FROM teacher  where account=?  " ;
	    List<Teacher> list= teacherRepository.selectteacherByAccount(sql,account);
	     for (Teacher teacher : list) {
	    	 pwd2= teacher.getPwd();
	    	 tname = teacher.getName();
	    	 if(pwd2.equals(pwd)){
	        		result.setEmsg("登录成功");
	       	    	result.setData(list);
	       	    	result.setSuccess(true);
	       	    	CURRENT_LOGIN_ACCOUNT_TEA=account;
	       	    	CURRENT_LOGIN_name_TEA=tname;
	        	}
	        	else{
	        		   result.setEmsg("登录失败");
	        		   result.setSuccess(false);
	        	   }
		}
	return JSONUtil.toJson(result);	
	}
	/**
	 * 
	 * 教师注册
	 */
	@Override
	public String teaRegister(Map<String, Object> map) {

		 Result result = new Result();
		 Object account=map.get("account");
		 Object name=map.get("name");
		 Object pwd=map.get("pwd");
		 Object  classid=map.get("classid");
		 //加密
		 MD5Util md5=new MD5Util();
		 String	encodepwd= md5.md5((String)pwd);
		 //System.out.println("encodepwd::"+encodepwd);
		 result.setSuccess(false);
			if(account == null || "".equals(account.toString())||name==null|| "".equals(name.toString())||encodepwd==null|| "".equals(encodepwd.toString())||classid==null|| "".equals(classid.toString())) {
				result.setEmsg("参数传递失败,注册失败！");
				return JSONUtil.toJson(result);
			}
			//事务
		 String  sql="replace into teacher(account,name,pwd) values(?,?,?)";
		 String sql2="replace into classtea(teaid,classid) values(?,?)";
		 teacherRepository.saveteacher(sql,account,name,encodepwd);
		 teacherRepository.saveclasstea(sql2,account,classid);
		 result.setEmsg("注册成功");
		 result.setSuccess(true);
		 return JSONUtil.toJson(result);
	}
	/**
	 * 试题列表
	 */
	@Override
	public String getMyQuestionsList(Map<String, Object> map) {
		Result result = new Result();
		String account = "admin";
        account = CURRENT_LOGIN_ACCOUNT_TEA;
		Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
    	long start = 0; //0表示第一条记录
    	long limit = 25; //最大返回数
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql="select * FROM examquestions WHERE CREATOR = ? and"
    			+ "((CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `type` LIKE ? END) or (CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `stem` LIKE ? END)) LIMIT ?,?";
		  List<Examquestions> list = teacherRepository.selectExamQuestionsList(sql,account,keyword,keyword,("%"+keyword+"%"),keyword,keyword,("%"+keyword+"%"),start,limit);
		  result.setEmsg("查询成功");
		  result.setSuccess(true);
		  result.setData(list);
		  System.out.println("list"+list);
		  return JSON.toJSONString(result);
	}
   /**
    * 试题详情
    */
	@Override
	public String getQuestionDetail(Map<String, Object> map) {
		Result result = new Result();
		String uuid = (String) map.get("uuid");
		result.setSuccess(false);
		if(uuid == null || "".equals(uuid.toString())) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
		String sql = "select * from examquestions where uuid=?";
		List<Examquestions> list = teacherRepository.selectByPrimaryKey(sql, uuid);
		result.setEmsg("查询成功");
		result.setData(list);
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
     /**
      * 删除试题
      */
	@Override
	public String delQuestion(Map<String, Object> map) {
		Result result = new Result();
		String uuid = (String) map.get("uuid");
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		result.setSuccess(false);
		if(uuid == null || "".equals(uuid.toString())) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
		//根据用户名和试题的uuid来删除试题
		String sql = "delete from examquestions where uuid=? and creator=?";
		//封装成对象
		Examquestions obj=new Examquestions();
		obj.setCreator(account);
		obj.setUuid(uuid);
		teacherRepository.deleteExamQuestions(sql,obj);
		result.setEmsg("删除成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 获取试题类型
	 * 
	 */
	@Override
	public String getTypes() {
		Result result = new Result();
		String account = "admin";
		 //account = CURRENT_LOGIN_ACCOUNT_TEA;
		//from Examquestions where creator = ?  group by type HAVING(COUNT(type)>1)
		String hql = "SELECT distinct type  from Examquestions where creator = ?";
		 List list = teacherRepository.selectExamByTypes(hql,account);
		result.setEmsg("查询成功");
		result.setData(list);
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
    /**
     *编辑试题里根据类型去获取试题
     * 
     */
	@Override
	public String getQuestionsByType(Map<String, Object> map) {
		Result result = new Result();
    	String account = "admin";
    	account = CURRENT_LOGIN_ACCOUNT_TEA;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String type = map.get("type")==null?"":map.get("type").toString();
    	String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
    	if(type == null || "".equals(type)) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
    	long start = 0;
    	long limit = 15;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String hql = "from Examquestions";		
		List list = teacherRepository.selectByType(hql);
		result.setEmsg("查询成功");
		result.setData(list);
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 删除试卷
	 */
	@Override
	public String delPaper(Map<String, Object> map) {
		Result result = new Result();
		String uuid = (String) map.get("uuid");
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		result.setSuccess(false);
		if(uuid == null || "".equals(uuid.toString())) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
		//根据用户名和试卷的uuid来删除试卷
		String sql = "delete from paper where uuid=? and creator=?";
		//封装成对象
		Paper obj=new Paper();
		obj.setCreator(account);
		obj.setUuid(uuid);
		teacherRepository.deleteExamPaper(sql,obj);
		result.setEmsg("删除成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	
	}
    /**
     * 编辑试题里提交新增试卷
     * 
     */
	@Override
	public String addPaper(Map<String, Object> map) {
		Result result = new Result();
		result.setSuccess(false);
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		Object uuidsobj = map.get("uuids[]");
		String type = (String) map.get("type");
		String name = (String) map.get("name");
		System.out.println(uuidsobj+type+name);
		if (uuidsobj == null || "".equals(uuidsobj.toString()) ||
				type == null || "".equals(type.toString())||
				name == null || "".equals(name.toString())
				) {
			result.setEmsg("参数传递错误!");
			return JSON.toJSONString(result);
		}
		String uuids[] = null;
		if (uuidsobj instanceof String[]) {
			uuids = (String[]) uuidsobj;
		}
		else {
			uuids = new String[] {uuidsobj.toString()};
		}
		String sql="insert into paper(`uuid`,`name`,`type`,`creator`,`questions`) VALUES(UUID(),?,?,?," + 
				"(select CONCAT('[',GROUP_CONCAT(CONCAT('{\"uuid\":\"',uuid,'\",\"multi\":\"',multi,'\",\"type\":\"',type,'\",\"stem\":\"',stem,'\",\"content\":',content,',\"analysis\":\"',analysis,'\",\"answer\":\"',answer,'\"}')),']') "
				+ " as content from examquestions where uuid in(?";
		StringBuilder sb = new StringBuilder(sql);
		Object []params = new Object[uuids.length+3];
		params[0] = name;
		params[1] = type;
		params[2] = account;
		params[3] = uuids[0];
		for(int i=1;i<uuids.length;i++) {
			sb.append(",?");
			params[i+3] = uuids[i];
		}
		sb.append(")))");
		teacherRepository.savePaper(sb.toString(),name,type,account,uuids,uuids[0]);
	    result.setEmsg("新增成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 *试卷列表
	 */
	@Override
	public String getMyPaperList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "admin";
        account = CURRENT_LOGIN_ACCOUNT_TEA;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
    	Object istotalobj = map.get("istotal");
    	boolean istotal = false;
    	if (istotalobj != null && !"".equals(istotalobj.toString())) {
    		try {
    			if (Boolean.parseBoolean(istotalobj.toString())) {
    				istotal = true;
    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	long start = 0;
    	long limit = 25;
    	List<Paper> list=null;
    	//试题模块的试题列表
    	if(!istotal) {
    	String	sql = "select * FROM paper WHERE creator = ? and"
        			+ "((CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `type` LIKE ? END) or (CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `name` LIKE ? END)) LIMIT ?,?";
    		try {
    			start = Long.parseLong(startobj.toString());
    			limit = Long.parseLong(limitobj.toString());
    		} catch (Exception e) {
    			result.setEmsg("参数传递失败!");
    			return JSON.toJSONString(result);
    		}
    		list= teacherRepository.selectExamPaperList(sql, account, keyword, keyword,
    				("%" + keyword + "%"), keyword, keyword, ("%" + keyword + "%"), start, limit);
    	}
		result.setData(list);
		result.setEmsg("查询成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 
	 * 显示在布置作业的试卷下拉菜单
	 * 
	 */
	@Override
	public String getOnlyPaperList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "admin";
        account = CURRENT_LOGIN_ACCOUNT_TEA;
		Object istotalobj = map.get("istotal");
    	boolean istotal = false;
    	if (istotalobj != null && !"".equals(istotalobj.toString())) {
    		try {
    			if (Boolean.parseBoolean(istotalobj.toString())) {
    				istotal = true;
    			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	String sql = "select * FROM paper WHERE creator = ?";
        //显示在布置作业的下拉菜单
    	List<Paper> list = teacherRepository.selectOnlyPaperList(sql,account);
    	result.setData(list);
		result.setEmsg("查询成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	
    /**
     * 试卷详情
     * 
     */
	@Override
	public String getPaperDetail(Map<String, Object> input) {
		Result result = new Result();
		String uuid = (String) input.get("uuid");
		result.setSuccess(false);
		if(uuid == null || "".equals(uuid.toString())) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
		String sql = "select * from paper where uuid=?";
         List<Paper> list = teacherRepository.selectPaperByPrimaryKey(sql, uuid);
		result.setData(list);
		result.setEmsg("查询成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}

	/**
	 * 作业列表
	 * 
	 */
	@Override
	public String getArrangeList(Map<String, Object> map) {
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		Result result = new Result();
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	long start = 0;
    	long limit = 25;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.setEmsg("获取参数失败!");
			return JSON.toJSONString(result);
		}
    	//当前学期处理
    	if (termService.needUpdate()) {
			termService.update();
		}
		String sql = "select * from assignment where creator = ? and isdel=0 and date between ? and ? order by ctime desc limit ?,?";
		List<Assignment> list = teacherRepository.selectAssignment(sql,account,termService.getSdate(),termService.getEdate(),start,limit);
		System.out.println(list);
		result.setData(list);
		result.setEmsg("作业列表查询成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}
   /**
    * 学期列表
    * 
    */
	@Override
	public String getTerms() {
		   Result result = new Result();
		   String sql="select * from termlist";
		   List<Termlist> list = teacherRepository.selectTermsAll(sql);
		   result.setData(list);
		   result.setEmsg("查询成功");
		   result.setSuccess(true);
		   return JSON.toJSONString(result);
	}
	/**
	 * 班级信息
	 * 
	 */
	@Override
	public String getMyClass() {
		Result result = new Result();
		String sql="select * from classlist ";
		List<Classlist> list = teacherRepository.selectClass(sql);
		result.setData(list);
		result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 布置作业提交
	 * 
	 */
	@Override
	public String arrange(Map<String, Object> input) {
		Result result = new Result();
		String name = (String) input.get("name");            //获取试卷名称
		String uuid = (String)input.get("paper_uuid");     //获取试卷id		        
		String classid =(String) input.get("classid");     //获取班级
		String type = (String)input.get("type");           //获取类型  随堂/课后
		String info = (String)input.get("info") == null?"":(String)input.get("info");  //获取备注信息
		System.out.println(name+uuid+"classid:"+classid+type+info);
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		result.setSuccess(false);      
		if(uuid == null || "".equals(uuid.toString()) ||
				name == null || "".equals(name.toString()) ||
				classid == null || "".equals(classid.toString()) ||
				type == null || "".equals(type.toString())
				) {
			result.setEmsg("参数传递失败!+++");
			return JSON.toJSONString(result);
		}
		long classidl = 0;
		try {
			classidl = Long.parseLong(classid.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.setEmsg("参数传递错误!---");
			return JSON.toJSONString(result);
		}
		String webresource = null;
		//根据班级 classid   来     获取学生的学生列表 
		webresource= getClassStuInfo(classid);
		//把学生的信息转入Map
		Map<String, Object> stus = JSONUtil.parseJson2Map(webresource);
		if (!(Boolean) stus.get("success")) {
			return webresource;
		}
		//把学生的信息转入List<Map<String, Object>> 
		List<Map<String, Object>> stulist = (List<Map<String, Object>>) stus.get("data");
		if (stulist.size() == 0) {
			result.setEmsg("您还没有学生，不能布置作业!");
			return JSON.toJSONString(result);
		}
		Date now = new Date();
		// 布置作业完成期限（毫秒）默认30天=2592000000ms
		Date after = new Date(now.getTime() +Long.parseLong("2592000000"));
		String ctime = TimeUtil.dateToString(now, TimeUtil.DATETIME_PATTERN);
		String stime = TimeUtil.dateToString(now, TimeUtil.DATETIME_PATTERN);
		System.out.println(stime);
		String etime = TimeUtil.dateToString(after, TimeUtil.DATETIME_PATTERN);
		System.out.println(etime);
		String sdate = TimeUtil.dateToString(now);
		System.out.println(sdate);
		String sql = "insert into assignment(`creator`,`name`,`type`,`ctime`,`stime`,`etime`,`date`,`isdel`,`paper`,`info`) "
				+ "values(?,?,?,?,?,?,?,?,(select questions from paper where uuid = ?),?)";
		try {
			teacherRepository.saveAssignment(sql,account,name,type,ctime,stime,etime,sdate,0,uuid,info);
		} catch (Exception e) {
			if (e.getMessage().contains("cannot be null")) {
				result.setEmsg("试题不能为空，请返回重新添加!");
			}
			else {
				result.setEmsg("数据库修改失败，请稍后重试!");
			}
			e.printStackTrace();
			return JSON.toJSONString(result);
		}
		 //获取作业id  <====根据创建作业时间+account 查询
		String sql2="select id from assignment where  creator=? and ctime=?";
		int assingid = teacherRepository.selectAssignmentByAccountAndCtime(sql2,account,ctime);
		System.out.println(assingid);
		 // 插入 作业详情表
		String sql3 = "insert into  assignmentdetail(`assingid`,`account`,`name`,`classid`) values(?,?,?,?)";
		teacherRepository.saveAssignmentdetail(sql3,assingid,stulist,classid);
		result.setEmsg("新增作业成功");
		result.setSuccess(true);
	    return JSON.toJSONString(result);
	}
	/**
	 * 学期统计详情  
	 * 查询每个学生每天所有作业的正确率。
	 * 
	 */
	@Override
	public String getTermStatistics(Map<String, Object> map) {
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		Result result = new Result();
    	Object classid = map.get("classid");
    	Object sdate = map.get("sdate");
    	Object edate = map.get("edate");
    	if(classid == null || "".equals(classid) ||
    			sdate == null || "".equals(sdate) ||
    			edate == null || "".equals(edate)
    			) {
    		result.setEmsg("参数传递错误!");
    		return JSON.toJSONString(result);
    	}
    	String hql = "select s.`NAME`,a.date,sum(RIGHTNUM),sum(TOTAL) from Assignment a" + 
    			"LEFT  JOIN Assignmentdetail  s on  a.ASSIGNID=s.ASSINGID group by `NAME`,DATE";
     List list=teacherRepository.selctTermStatistics(hql,account,classid,sdate,edate);
    	result.setData(list);
		result.setEmsg("查询成功");
    	result.setSuccess(true);
    	return JSON.toJSONString(result);
	}

	@Override
	public String delArrange(Map<String, Object> input) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 作业详情
     * @param input
     * @return
     */
	@Override
	public String getPaperStatistics(Map<String, Object> map) {
		Result result = new Result();
		Object id = map.get("id");
		String account = "admin";
		account = CURRENT_LOGIN_ACCOUNT_TEA;
		result.setSuccess(false);
		if(id == null || "".equals(id.toString())) {
			result.setEmsg("参数传递失败!");
			return JSON.toJSONString(result);
		}
		String sql = "SELECT * FROM assignmentdetail where assingid = ? ORDER BY rightnum desc";
		  List<Assignmentdetail> list = teacherRepository.selectAssigndetailById(sql,id);
		    result.setData(list);
			result.setEmsg("查询成功");
	    	result.setSuccess(true);
		  return JSON.toJSONString(result);
	}


	@Override
	public String getQuestionsList() {
	   String sql="select * from examquestions";
	   List<Examquestions> list = teacherRepository.selectExamQuestionList(sql);
	   return JSON.toJSONString(list);
	}
	
	/**
	 * 
	 * 根据班级classid 查询班级学生名单
	 */
	@Override
	public String getClassStuInfo(Object classid) {
		Result result = new Result();
    	String sql="SELECT b.* FROM stuclass AS a ,student AS b WHERE a.classid=? AND a.stuaccount=b.account";
    	List list = teacherRepository.selectStuInfo(sql,classid);
    	result.setData(list);
		result.setEmsg("查询成功");
    	result.setSuccess(true);
		return JSON.toJSONString(result);
	}
	/**
	 * 
	 *教师获取学生错题列表
	 */
	@Override
	public String getStuMistakeList(Map<String, Object> map) {
		Result result = new Result();
    	String account = "admin";
    	account = CURRENT_LOGIN_ACCOUNT_TEA;
    	Object startobj = map.get("start");
    	Object limitobj = map.get("limit");
    	String keyword = map.get("keyword")==null?"":map.get("keyword").toString();
    	long start = 0;
    	long limit = 15;
    	try {
			start = Long.parseLong(startobj.toString());
			limit = Long.parseLong(limitobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "select * from MISTAKESFORTEACHER where taccount = ? and "
				+ "((CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `type` LIKE ? END) "
				+ "or (CASE WHEN (''=? OR ? IS NULL) THEN 1=1 ELSE `sname` LIKE ? END)) order by id desc LIMIT ?,?";
		List<Stumistakes> list = teacherRepository.selectStuMistakeList(sql,account,keyword,keyword,"%"+keyword+"%",keyword,keyword,"%"+keyword+"%",start,limit);
		result.setData(list);
		result.setEmsg("查询成功");
		result.setSuccess(true);
		return JSONUtil.toJson(result);
	}


		
}
