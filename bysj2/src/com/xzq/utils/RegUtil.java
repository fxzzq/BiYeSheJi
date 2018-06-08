package com.xzq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {

	/**
	 * 用于缓存已经编译的正则表达式
	 */
	private static final Map<String, Pattern> patterns = new HashMap<String, Pattern>();
	
	/**
	 * 判断某个字符串是否满足正则表达式
	 * @param exp		正则表达式
	 * @param message	需要验证的字符串
	 * @return
	 */
	public static boolean match(String exp, String message){
		if(exp == null || "".equals(exp.trim())){return false;}
		if(message == null){message = "";}
		Pattern pattern = patterns.get(exp);
		//Pattern.compile(exp, Pattern.DOTALL),中的Pattern.DOTALL模式,保证'.'可以匹配所有的字符串，包括结束符和换行符
		if(pattern == null){pattern = Pattern.compile(exp, Pattern.DOTALL);patterns.put(exp, pattern);}
		Matcher matcher = pattern.matcher(message);
		return matcher.matches();
	}
	
	public static boolean isIntegerOrLong(String message){
		return match("^[1-9][0-9]*$", message);
	}

	/**
	 * 从字符串中查找满足条件的子串
	 * @param exp
	 * @param message
	 * @return
	 */
	public static List<String> find(String exp, String message){
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile(exp);
		Matcher matcher = pattern.matcher(message);
		while(matcher.find()){
			list.add(matcher.group(1));
		}
		return list;
	}
	
	/**
	 * 截取满足正则表达式之后的子串
	 * @param message		需要截取的字符串
	 * @param exp			正则表达式
	 * @param count			满足第几个
	 * @return
	 */
	public static String regSubString(String message, String exp, int count){
		String result = "";
		for(int i = 0; i < count; i++){
			result = message.substring(0, message.indexOf(exp, exp.length() * i + result.length()));
		}		
		return result;
	}
	
	/**
	 * 是否为数字
	 * @return
	 */
	public boolean isNumber(String str){
		if(str == null){return false;}
		try{
			Double.parseDouble(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	
}
