package com.xzq.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.xzq.service.ImportService;
import com.xzq.utils.Configuration;
import com.xzq.utils.Result;


@Controller
@RequestMapping("/proj/examinations")
public class ImportController {
	
	@Autowired
	ImportService importService;
	
	@Autowired
	private Configuration configuration;
	
	@RequestMapping(value = "/import.do",produces="text/html;charset=UTF-8")
	
	public String fileImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String account = request.getParameter("account");
		if (account == null || "".equals(account)) {
			return "<html><body>获取帐号失败!</body></html>";
		}
    	response.setCharacterEncoding("UTF-8");
    	return "/Teacher/fileimport";
	}
	/**
	 * 试题导入
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/import.action",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String importAction(@RequestParam MultipartFile file,HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		System.out.println("------后端接收到account----"+account);
		if (account == null || "".equals(account)) {
			Result result = new Result();
			result.setStatus(500);
			result.setEmsg("参数传递失败，请重新打开页面!");
			return JSON.toJSONString(result);
		}
	//	SecurityContext.CURRENT_LOGIN_USER_ACCOUNT.set(account);
		String fileName = file.getOriginalFilename();                  //获取文件的名字
		String type = fileName.substring(fileName.lastIndexOf(".")+1); //获取文件的类型xls/zip
		if (type.equalsIgnoreCase("zip")) {
			return importService.importZipFile(file,account);
		}
		else {
			System.out.println("------xlsx----");
			return importService.importDocFile(file,account);
		}
	}
	/**
	 * 下载zip模板
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadzip.action",produces="application/zip")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = configuration.getZipTemplate();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
		if (is == null) {
			response.getWriter().println("<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> </header><body>服务器文件不存在!</body></html>");
			return;
		}
		String fileName = path.substring(path.lastIndexOf("/")+1);
		byte[]data = new byte[is.available()];
		is.read(data);
		fileName = URLEncoder.encode(fileName, "UTF-8");  
		response.reset();  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	    response.setContentType("application/octet-stream;charset=UTF-8");  
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	}
	/**
	 * 下载Excel模板
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadexcel.action",produces="application/x-xls")
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String path = configuration.getExcelTemplate();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
		if (is == null) {
			response.getWriter().println("<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> </header><body>服务器文件不存在!</body></html>");
			return;
		}
		String fileName = path.substring(path.lastIndexOf("/")+1);
		byte[]data = new byte[is.available()];
		is.read(data);
		fileName = URLEncoder.encode(fileName, "UTF-8");  
		response.reset();  
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
	    response.addHeader("Content-Length", "" + data.length);  
	    response.setContentType("application/octet-stream;charset=UTF-8");  
	    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
	    outputStream.write(data);  
	    outputStream.flush();  
	    outputStream.close();
	}

}
