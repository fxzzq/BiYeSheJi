package com.xzq.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 
 * 用于处理JSON格式化的处理类
 * 
 */
public class JSONUtil {
	
	private static JsonConfig jsonConfig4LongInJavaScript;
	
	static {
		jsonConfig4LongInJavaScript = new JsonConfig();
		jsonConfig4LongInJavaScript.registerJsonValueProcessor(Long.class, new JsonValueProcessor() {			
			public Object processObjectValue(String name, Object value, JsonConfig config) {
				if(value != null && value instanceof Long){
					Long _long = (Long)value;
					if(_long > 9007199254740992L){
						return String.valueOf(value);
					}					
				}
				return value;
			}
			public Object processArrayValue(Object value, JsonConfig arg1) {
				if(value != null && value instanceof Long){
					Long _long = (Long)value;
					if(_long > 9007199254740992L){
						return String.valueOf(value);
					}					
				}
				return value;
			}
		});
	}

	/**
	 * 将long的list转换成mongodb的类型
	 * @param list
	 * @return
	 */
	public static String mongo4listToNumberLongJson(List<Long> list){
		StringBuffer json = new StringBuffer("[");
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Long one = list.get(i);
				if(i == 0){
					json.append("NumberLong('"+one.longValue()+"')");
				}else{
					json.append(",NumberLong('"+one.longValue()+"')");
				}
			}
		}		
		json.append("]");
		return json.toString();
	}
	
	/**
	 * 将json转化为bean
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(String json, Class<T> clazz){
		return toBean(json, clazz, null);	
	}
	
	public static <T> T toBean(String json, Class<T> clazz, Map<String, Class<?>> classMapping){
		if(json == null || clazz == null){return null;}		
		if(AbstractList.class.isAssignableFrom(clazz)){
			return (T)JSONArray.toList(JSONArray.fromObject(json), clazz, classMapping);
		}else{
			return (T)JSONObject.toBean(JSONObject.fromObject(json),clazz, classMapping);		
		}		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> parseJson2List(String jsonStr){
		if(jsonStr == null){return null;}
		JSONArray json = JSONArray.fromObject(jsonStr);		
		return json;
	}
	
	public static String toJson(Object obj){
//		Map<String, Object> tempMap = new HashMap<String, Object>(1);
//		tempMap.put("data", obj);
//		//首先将数据转换为Map
//		Map<String, Object> mp = parseJson2Map(toJsonWithoutJavascriptLong(tempMap));		
//		String message = toJsonWithoutJavascriptLong(mp.get("data"));
		
		return toJsonWithoutJavascriptLong(obj);
	}
	
	
	public static String toJsonWithoutJavascriptLong(Object obj) {
		if (obj == null) {
			return null;
		}		
		if (obj instanceof Collection<?> || obj instanceof Object[] ||
				obj instanceof short[] || obj instanceof char[] ||
				obj instanceof int[] || obj instanceof float[] ||
				obj instanceof long[] || obj instanceof double[]) {
			return JSONArray.fromObject(obj,jsonConfig4LongInJavaScript).toString();
		}				
		return JSONObject.fromObject(obj,jsonConfig4LongInJavaScript).toString();
	}
  
	/**
	 * 
	 * jsonStr转化成Map
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseJson2Map(String jsonStr) {
		if(jsonStr == null){return null;}
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Object> list = new ArrayList<Object>();
				Iterator<Object> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					Object _obj = it.next();
					if(_obj instanceof JSONObject){
						list.add(parseJson2Map(((JSONObject)_obj).toString()));
					}else if(_obj instanceof JSONArray){
						
					}else{
						list.add(_obj);
					}					
				}
				map.put(k.toString(), list);
			} else {
				if(v instanceof JSONObject){
					if(((JSONObject)v).isNullObject()){
						map.put(k.toString(), null);
						continue;
					}
				}
				map.put(k.toString(), v);
			}
		}

		return map;

	}
	
	/**
	 * 格式化
	 * @param jsonStr
	 * @return
	 */
	public static String formatJson(String jsonStr) {
		if (null == jsonStr || "".equals(jsonStr)){return "";}
		jsonStr = jsonStr.replace(" ", "");
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		boolean isInQuotationMarks = false;
		for (int i = 0; i < jsonStr.length(); i++) {
			last = current;
			current = jsonStr.charAt(i);
			switch (current) {
			case '"':
                                if (last != '\\'){
				    isInQuotationMarks = !isInQuotationMarks;
                                }
				sb.append(current);
				break;
			case '{':
			case '[':
				sb.append(current);
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent++;
					addIndentBlank(sb, indent);
				}
				break;
			case '}':
			case ']':
				if (!isInQuotationMarks) {
					sb.append('\n');
					indent--;
					addIndentBlank(sb, indent);
				}
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\' && !isInQuotationMarks) {
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}

		return sb.toString();
	}

	/**
	 * 添加space
	 * 
	 * @param sb
	 * @param indent
	 */
	private static void addIndentBlank(StringBuilder sb, int indent) {
		for (int i = 0; i < indent; i++) {
			sb.append("  ");
		}
	}

}
