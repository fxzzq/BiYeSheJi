package com.xzq.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 * Excel工具类
 * @author Administrator
 *
 */
public class ExcelUtil {
	
	/**
	 * 读取excel文件中的数据
	 * @param stream	数据流
	 * @return
	 * @throws IOException 
	 */
	public static List<Map<String, String>> readExcel(InputStream stream,String fileType) throws IOException{		
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		Sheet sheet1 = wb.getSheetAt(0);
		List<String> titles = new ArrayList<String>();
		int rowIndex = 0;
		for (Row row : sheet1) {			
			Map<String, String> mp = new HashMap<String, String>();	
			boolean isBlank = true;
			int columnIndex = 0;
			for (Cell cell : row) {
				if(cell.getStringCellValue() != null && !"".equals(cell.getStringCellValue().toLowerCase())){
					isBlank = false;
				}
				if(rowIndex == 0){
					titles.add(cell.getStringCellValue());
				}else{
					if(titles.size() <= columnIndex){columnIndex++;continue;}
					mp.put(titles.get(columnIndex), cell.getStringCellValue());
					columnIndex++;
				}
			}			
			if(rowIndex > 0 && !isBlank){
				result.add(mp);
			}
			rowIndex++;
		}
		return result;
	}

}
