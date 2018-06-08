package com.xzq.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;
import com.xzq.utils.Result;

public interface ImportService {
	/**
	 * 处理导入的xls/xlsx
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String importDocFile(MultipartFile file,String account) throws IOException;
	/**
	 * 处理导入的zip
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String importZipFile(MultipartFile file,String account) throws IOException;
	/**
	 * 为上面2个导入服务校验
	 * @param zipFile
	 * @param account
	 * @param fileName
	 * @param oneData
	 * @param contentList
	 * @param i
	 * @return
	 * @throws IOException
	 */
	public Result validateQuertion(ZipFile zipFile,String account,String fileName,Map<String, String> oneData,List<Map<String, Object>> contentList, int i) throws IOException;
	
	public String getOptionType(String value);
}
