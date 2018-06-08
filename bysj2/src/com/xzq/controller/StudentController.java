package com.xzq.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xzq.service.StudentService;


@Controller
@RequestMapping("/proj/student")
public class StudentController {
      @Autowired	
	  StudentService studentservice;
     /**
      * 学生登录 
      * 
      */
  	@RequestMapping(value = "/stuLogin.action",produces="text/html;charset=UTF-8")
  	@ResponseBody  
  	public String stuLogin(@RequestParam("account")String account,@RequestParam("pwd")String pwd){		
  		Map<String, Object> map =new  HashMap<>();
  		map.put("account", account);
  		map.put("pwd", pwd);
  		System.out.println(account+pwd);
  		return studentservice.stuLogin(map);
     }
  	/**
  	 * 
  	 *学生注册
  	 */
	@RequestMapping(value = "/stuRegister.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String stuRegister(@RequestParam("account")String account,@RequestParam("name")String name,@RequestParam("pwd")String pwd,@RequestParam("classid")String classid){		
		Map<String,Object> map=new HashMap<>();
		map.put("account", account);
		map.put("name", name);
		map.put("pwd",pwd);  
		map.put("classid", classid);
		System.out.println(account+name+pwd+classid);
		return studentservice.stuRegister(map);
    }
	/**
	 * 练习列表
	 *
	 */
	@RequestMapping(value = "/practicelist.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String practiceList(@RequestParam("start")int start,@RequestParam("limit")int limit,@RequestParam("keyword")String keyword){			
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("keyword", keyword);
		System.out.println("start: "+start+" limit: "+limit+" keyword: "+keyword);
		String result = studentservice.practiceList(map);
		return result;
    }
	
	/**
	 *加载做练习页面 Answer.html
	 * 
	 */
	@RequestMapping(value = "/practicedetail.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String practiceDetail(@RequestParam("id")int id) {
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		return studentservice.practiceDetail(map);
    }
	/**
	 *做练习完提交答案 
	 *
	 */
	@RequestMapping(value = "/dopractice.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String doPractice(@RequestParam("data")int data,@RequestParam("id")int id){	
		Map<String,Object> map=new HashMap<>();
		map.put("data", data);
		map.put("id", id);
		return studentservice.doPractice(map);
    }
	
	/**
	 * 
	 * 练习阅卷结果展示
	 */
	@RequestMapping(value = "/finishpractice.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String finishPractice(@RequestParam("id")int id){
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		return studentservice.finishPractice(map);
    }
	
	/**
	 * 
	 *课后作业列表
	 */
	@RequestMapping(value = "/worklist.action",produces="text/html;charset=UTF-8")
	@ResponseBody
    public String workList(@RequestParam("start")int start,@RequestParam("limit")int limit,@RequestParam("keyword")String keyword){			
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		map.put("keyword", keyword);
		System.out.println("start: "+start+" limit: "+limit+" keyword: "+keyword);
		return studentservice.workList(map);
    }

	/**
	 * 获取错题集类型(错题的课程名)
	 * 
	 */
	@RequestMapping(value = "/getmistaketype.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMistakeType(){			
		return studentservice.getMistakeType();
    }
	/**
	 *开始错题练习答题
	 * 
	 */
	@RequestMapping(value = "/getmistakes.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getMistakes(@RequestParam("type")String type,@RequestParam("condition")String condition,@RequestParam("taccount")String taccount){			
		Map<String,Object> map=new HashMap<>();
		map.put("type", type);
		map.put("condition", condition);
		map.put("taccount", taccount);
		return studentservice.getMistakes(map);
    }
	/**
	 * 错题集首页列表
	 *
	 */
	@RequestMapping(value = "/mistakelist.action",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String mistakeList(@RequestParam("start")String start,@RequestParam("limit")String limit){			
		Map<String,Object> map=new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		return studentservice.mistakeList(map);
    }
	/**
	 * 
	 * 删除错题集里试题，不再练习
	 *
	 */
	@RequestMapping(value = "/delmistake.action",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delMistake(@RequestParam("uuid")String uuid){	
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		return studentservice.deleteMistake(map);
    }
	
	/**
	 * 查看错题列表的错题详情（试题详情）
	 * 
	 */
	@RequestMapping(value = "/getmistakedetail.action",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String getMistakeDetail(@RequestParam("uuid")String uuid){
		Map<String,Object> map=new HashMap<>();
		map.put("uuid", uuid);
		return studentservice.getMistakeDetail(map);
    }
	
	/**
	 * 
	 *同步错题练习
	 *同步作答结果
	 */
	@RequestMapping(value = "/asyncmistakes.action",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String asyncMistakes(@RequestParam("data")String data,@RequestParam("taccount")String taccount,@RequestParam("type")String type){			
		Map<String,Object> map=new HashMap<>();
		map.put("data", data);
		map.put("taccount", taccount);
		map.put("type", type);
		return studentservice.asyncMistakes(map);
    }
	

}

