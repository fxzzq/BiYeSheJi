package com.xzq.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xzq.utils.JSONUtil;

@Service
public class TermService {
	private Long interval;   //学期查询间隔（毫秒） 86400000=1天
	private Long lastUpTime; //当前时间的时间戳
	private String sdate;  // 学期开始时间
	private String edate;  // 学期结束时间
	
	
	@Autowired
	private TeacherService teacherService;
	
	public Long getInterval() {
		return interval;
	}
	public void setInterval(Long interval) {
		this.interval = interval;
	}
	public Long getLastUpTime() {
		return lastUpTime;
	}
	private void setLastUpTime(Long lastUpTime) {
		this.lastUpTime = lastUpTime;
	}
	public String getSdate() {
		return sdate;
	}
	private void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	private void setEdate(String edate) {
		this.edate = edate;
	}
	
	/**
	 * 是否需要更新
	 * @return
	 */
	public Boolean needUpdate() {
		System.out.println("-------是否需要更新当前学期----------"+getInterval()+getLastUpTime());
		if (lastUpTime == null || lastUpTime == 01) {
			return true;
		}
		Long now = new Date().getTime();
		setInterval(Long.parseLong("86400000"));
		 if (now - lastUpTime >getInterval() ) {
			return true;
		}
		else {
			return false;
		}
	}
	/**
	 * 学期更新
	 * 
	 */
	public void update() {
		//调用teacherService
		String webresource = teacherService.getTerms();
		Map<String,Object> listm = JSONUtil.parseJson2Map(webresource);
		List list = (List) listm.get("data");
		for (Object object : list) {
			if(Boolean.parseBoolean(((Map)object).get("iscurrent").toString())) {
				setSdate(((Map)object).get("sdate").toString());
				setEdate(((Map)object).get("edate").toString());
				setLastUpTime(new Date().getTime());
				System.out.println("当前学期开始时间:"+getSdate()+"当前学期结束时间:"+getEdate());
				break;
			}
		}
	}
}
