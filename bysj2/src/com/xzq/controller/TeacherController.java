package com.xzq.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xzq.service.TeacherService;




@Controller
@RequestMapping("/proj/examinations")
public class TeacherController {
	

	@Autowired
	private TeacherService examinationService;
	/**
	 * 教师登录
	 * 
	 */
	
	@RequestMapping(value = "/teaLogin.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String teaLogin(@RequestParam("account")String account,@RequestParam("pwd")String pwd){		
		Map<String,Object> map=new HashMap<>();
		map.put("account", account);
  		map.put("pwd", pwd);
  		System.out.println(account+pwd);
    	return examinationService.teaLogin(map);
    }
	/**
	 * 教师注册
	 * 
	 */
	
	@RequestMapping(value = "/teaRegister.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String teaRegister(@RequestParam("account")String account,@RequestParam("name")String name,@RequestParam("pwd")String pwd,@RequestParam("classid")String classid){		
		Map<String,Object> map=new HashMap<>();
		map.put("account", account);
		map.put("name", name);
		map.put("pwd",pwd);  
		map.put("classid", classid);
		System.out.println(account+name+pwd+classid);		
    	return examinationService.teaRegister(map);
    }
	/**
	 * 获取我的试题列表
	 * 
	 */
	@RequestMapping(value = "/getmyquestionslist.action",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String getMyQuestionsList(@RequestParam("start")int start,@RequestParam("limit")int limit,@RequestParam("keyword")String keyword){			
		//获取前端的传入的参数
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("keyword", keyword);
		System.out.println("start: "+start+" limit: "+limit+" keyword: "+keyword);
		String queryresult = examinationService.getMyQuestionsList(map);
		return queryresult ;
    }
	@RequestMapping(value = "/getquestionslist.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getQuestionList() {
	String result = examinationService.getQuestionsList();
	System.out.println(result);
	return  result;
	}
	/**
	 * 获取试题详情
	 *
	 */
	@RequestMapping(value = "/getquestiondetail.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getQuestionDetail(@RequestParam("uuid")String uuid){			
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		 String result = examinationService.getQuestionDetail(map);
		 return result;
    }
	/**
	 * 删除试题
	 * 
	 */
	
	@RequestMapping(value = "/delquestion.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delQuestion(@RequestParam("uuid")String uuid){			
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		return examinationService.delQuestion(map);
    }
	/**
	 * 获取试卷列表
	 * 
	 */
	@RequestMapping(value = "/getmypaperlist.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMyPaperList(@RequestParam("start")int start,@RequestParam("limit")int limit,@RequestParam("keyword")String keyword,@RequestParam("istotal")Boolean istotal ){		
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("keyword", keyword);
		map.put("istotal", istotal);
		String result = examinationService.getMyPaperList(map);
		return  result;
    }
	/**
	 * 布置作业 试卷的下拉列表
	 * 
	 */
	@RequestMapping(value = "/getmypaperlist2.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getOnlyPaperList(@RequestParam("istotal")Boolean istotal ){		
		Map<String,Object> map=new HashMap<>();
		map.put("istotal", istotal);
		String result = examinationService.getOnlyPaperList(map);
		return  result;
    }
	/**
	 * 获取试卷详情
	 * 
	 */
	@RequestMapping(value = "/getpaperdetail.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getPaperDetail(@RequestParam("uuid")String uuid){	
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		String result = examinationService.getPaperDetail(map);
		return result;
    }
	
	/**
	 * 删除试卷
	 * 
	 */
	@RequestMapping(value = "/delpaper.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delPaper(@RequestParam("uuid")String uuid){		
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		return examinationService.delPaper(map);
    }
	/**
	 * 获取我的试题类型
	 */
	@RequestMapping(value = "/gettypes.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getTypes(){			
		String types = examinationService.getTypes();
		return types ;
    }
	/**
	 * 编辑试题里根据类型去获取试题
	 * 
	 */
	@RequestMapping(value = "/getquestionsbytype.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getQuestionsByType(@RequestParam("type")String type,@RequestParam("limit")int limit,@RequestParam("start")int start,@RequestParam("keyword")String keyword){			
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("limit", limit);
		map.put("start", start);
		map.put("keyword", keyword);
		return examinationService.getQuestionsByType(map);
    }
	/**
	 * 编辑试题里提交新增试卷
	 * 
	 */
	@RequestMapping(value = "/addpaper.action",produces="text/html;charset=UTF-8")
    @ResponseBody
	public String addPaper(@RequestParam("uuids[]")String[] uuids,@RequestParam("type")String type,@RequestParam("name")String name){			
		Map<String,Object> map=new HashMap<>();
		map.put("uuids[]", uuids);
		map.put("type", type);
		map.put("name", name);
		return examinationService.addPaper(map);
    }
	/**
	 * 获取学期列表
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/getterms.action",produces="text/html;charset=UTF-8")
    public String getTerms(){			
		String result = examinationService.getTerms();
		return result;
    }
   /**
    * 获取作业列表
    * 
    */
	@RequestMapping(value = "/getarrangelist.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getArrangeList(@RequestParam("limit")int limit, @RequestParam("start")int start){			
		Map<String,Object> map=new HashMap<>();
		map.put("limit", limit);
		map.put("start", start);
		return examinationService.getArrangeList(map);
    }
	/**
	 * 获取班级信息
	 * 
	 */
	
	@RequestMapping(value = "/getmyclass.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getMyClass(){			
		return examinationService.getMyClass();
    }
	
	/*
	*布置作业提交 
	* 安排考试或作业
	*/
	@RequestMapping(value = "/arrange.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String arrange(@RequestParam("name")String name,@RequestParam("paper_uuid")String paper_uuid,@RequestParam("classid")String classid,@RequestParam("type")String type,@RequestParam("info")String info){			
		Map<String,Object> map=new HashMap<>();
		map.put("name", name);
		map.put("paper_uuid", paper_uuid);
		map.put("classid", classid);
		map.put("type",type );
		map.put("info", info);
		return examinationService.arrange(map);
    }
	/**
	 * 根据班级classid 查询班级学生名单
	 * 
	 */
	@RequestMapping(value = "/getclassstuinfo.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getclassstuinfo(@RequestParam("classid")String classid){
		return examinationService.getClassStuInfo(classid);
    }
	/**
	 * 
	 * 作业详情根据作业id
	 */
	@RequestMapping(value = "/getpaperstatistics.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String getPaperStatistics(@RequestParam("id")int id){
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		return examinationService.getPaperStatistics(map);
    }

	/**
	 * 学期统计详情
	 * 
	 * 
	 */
	@RequestMapping(value = "/gettermstatistics.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getTermStatistics(@RequestParam("classid")int classid,@RequestParam("sdate")String sdate,@RequestParam("edate")String edate){			
		Map<String,Object> map=new HashMap<>();
		map.put("classid", classid);
		map.put("sdate", sdate);
		map.put("edate", edate);
		return examinationService.getTermStatistics(map);
    }
	/**
	 * 
	 * 教师获取学生错题列表
	 */
	@RequestMapping(value = "/getstumistakeslist.action",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getStuMistakeList(@RequestParam("limit")int limit,@RequestParam("start")int start,@RequestParam("keyword")String keyword){			
		Map<String,Object> map=new HashMap<>();
		map.put("limit", limit);
		map.put("start", start);
		map.put("keyword", keyword);
		return examinationService.getStuMistakeList(map);
    }
} 
