package com.xzq.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

import com.xzq.dao.ImportRepository;
import com.xzq.service.ImportService;
import com.xzq.utils.ExcelUtil;
import com.xzq.utils.JSONUtil;
import com.xzq.utils.RegUtil;
import com.xzq.utils.Result;

@Service
public class ImportServiceImpl implements ImportService {

	@Autowired
	ImportRepository importrepository;

	/**
	 * 导入的是xls/xlsx
	 */
	@Override
	public String importDocFile(MultipartFile file, String account) throws IOException {
		// String account = "admin";
		// account=CURRENT_LOGIN_ACCOUNT_TEA
		Result result = new Result();
		result.setSuccess(false);
		String filename = file.getOriginalFilename();
		String suff = filename.substring(filename.lastIndexOf(".") + 1);
		InputStream input = file.getInputStream();
		List<Map<String, String>> data = ExcelUtil.readExcel(input, suff);
		List<Object[]> params = new ArrayList<Object[]>();
		for (int i = 0; i < data.size(); i++) {
			System.out.println("------------data.size()--------" + data.size());
			Map<String, String> oneData = data.get(i);
			List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
			System.out.println("--------contentList--------" + contentList);
			TreeSet<String> keys = new TreeSet<String>(oneData.keySet());
			System.out.println("--------keys--------" + keys);
			Iterator<String> iterator = keys.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> option = new HashMap<String, Object>();
				String key = iterator.next();
				String value = oneData.get(key);
				if (key.matches("[a-zA-Z]")) {
					option.put("name", key.toUpperCase());
					option.put("value", value);
					option.put("type", "charactor");
					contentList.add(option);
				}
				System.out.println("--------contentList2--------" + contentList);
			}

			result = validateQuertion(null, account, filename, oneData, contentList, i + 1);
			if (!result.isSuccess()) {
				return JSON.toJSONString(result);
			}
			System.out.println("------result.getCustom()------------" + result);
			params.add((Object[]) result.getCustom());
		}
		// 调用dao 试题导入到数据库
		String sql = "insert into examquestions(`uuid`,`creator`,`type`,`stem`,`content`,`analysis`,`answer`,`multi`,`total`,`wrongnum`,`rightnum`,`rate`) values(UUID(),?,?,?,?,?,?,?,?,?,?,?)";
		importrepository.saveExaminations(sql, params);
		result.setEmsg("试题导入数据库成功");
		result.setSuccess(true);
		return JSON.toJSONString(result);
	}

	/**
	 * 导入的是zip
	 * 
	 */
	@Override
	public String importZipFile(MultipartFile file, String account) throws IOException {
		Result result = new Result();
		boolean hasFile = false;
		// String account = "admin";
		// account=CURRENT_LOGIN_ACCOUNT_TEA
		ZipFile zipFile = null;
		File f = null;
		List<Object[]> params = new ArrayList<Object[]>();
		try {
			// UUID使用
			// System.getProperty("java.io.tmpdir") 获取操作系统的缓存临时目录
			// System.getProperty("file.separator") 文件分隔符
			f = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")
					+ UUID.randomUUID().toString());
			file.transferTo(f);
			// Windows系统压缩的文件默认编码为GBK
			zipFile = new ZipFile(f, Charset.forName("GBK"));
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
			if (zipEntries.hasMoreElements()) {
				try {
					ZipEntry entry = zipEntries.nextElement();
					zipEntries = zipFile.entries();
				} catch (IllegalArgumentException e) {
					// Linux系统压缩的文件编码为UTF-8
					zipFile = new ZipFile(f);
					zipEntries = zipFile.entries();
				}
			}
			while (zipEntries.hasMoreElements()) {
				ZipEntry entry = zipEntries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				String fileName = entry.getName();
				if (fileName.endsWith("xls") || fileName.endsWith("xlsx")) {
					hasFile = true;
					String suff = fileName.substring(fileName.lastIndexOf(".") + 1);
					InputStream input = zipFile.getInputStream(entry);
					List<Map<String, String>> data = ExcelUtil.readExcel(input, suff);

					for (int i = 0; i < data.size(); i++) {
						result = new Result();
						result.setStatus(500);
						Map<String, String> oneData = data.get(i);
						List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();
						TreeSet<String> keys = new TreeSet<String>(oneData.keySet());
						Iterator<String> iterator = keys.iterator();
						while (iterator.hasNext()) {
							Map<String, Object> option = new HashMap<String, Object>();
							String key = iterator.next();
							String value = oneData.get(key);
							if (value == "") {
								result.setEmsg("文件：" + fileName + "\n第" + (i + 1) + "行，选项" + key + "错误，选项不能为空!");
								return JSON.toJSONString(result);
							}
							// 替换题干

							if (key.matches("[a-zA-Z]")) {
								option.put("name", key.toUpperCase());
								if (value.matches(".*\\$\\{.*}.*")) {// $替换符，表示该选项为图片
									List<String> strs = RegUtil.find("\\$\\{([\\w\\u4e00-\\u9fa5\\\\.\\/]+)\\}", value);
									String src = value;
									for (String string : strs) {
										ZipEntry imgEntry = zipFile.getEntry(string);
										if (string == "" || imgEntry == null) {
											result.setEmsg("文件：" + fileName + "\n第" + (i + 1) + "行，选项" + key
													+ "错误，选项对应图片不存在!");
											return JSON.toJSONString(result);
										}
										String imgSuff = imgEntry.getName()
												.substring(imgEntry.getName().lastIndexOf(".") + 1);
										InputStream img = zipFile.getInputStream(imgEntry);
										Base64 base64 = new Base64();
										byte bt[] = new byte[img.available()];
										img.read(bt);
										String code = base64.encodeToString(bt);
										src = src.replace("${" + string + "}",
												"<img src='data:image/" + imgSuff + ";base64," + code + "'>");
									}
									option.put("value", src);
									option.put("type", "image");
								} else {
									option.put("value", value);
									option.put("type", "charactor");
								}
								contentList.add(option);
							}

						}

						result = validateQuertion(zipFile, account, fileName, oneData, contentList, i + 1);
						if (!result.isSuccess()) {
							result.setCustom(null);
							return JSON.toJSONString(result);
						}
						params.add((Object[]) result.getCustom());
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
			f.delete();
		}

		result.setSuccess(hasFile);
		if (hasFile) {
			String sql = "insert into examquestions(`uuid`,`creator`,`type`,`stem`,`content`,`analysis`,`answer`,`multi`,`total`,`wrongnum`,`rightnum`,`rate`) values(UUID(),?,?,?,?,?,?,?,?,?,?,?)";
			// 调用dao 试题导入到数据库
			importrepository.saveExaminations(sql, params);
			result.setStatus(200);
			result.setSuccess(true);
			result.setEmsg("试题导入成功!");
			return JSON.toJSONString(result);
		} else {
			result.setEmsg("文件中未包含Excel文件，导入失败!");
			return JSON.toJSONString(result);
		}

	}

	/**
	 * 为上面2个导入校验的方法
	 * 
	 * @param zipFile
	 * @param account
	 * @param fileName
	 * @param oneData
	 * @param contentList
	 * @param i
	 * @return
	 * @throws IOException
	 */
	@Override
	public Result validateQuertion(ZipFile zipFile, String account, String fileName, Map<String, String> oneData,
			List<Map<String, Object>> contentList, int i) throws IOException {
		System.out.println("--------validateQuertion-----------");
		Result result = new Result();
		result.setSuccess(true);
		String stem = oneData.get("题干");
		result.setStatus(500);
		if (stem == "") {
			result.setEmsg("文件：" + fileName + "\n第" + i + "行错误，题干不能为空!");
			return result;
		}
		List<String> strs = RegUtil.find("\\$\\{([\\w\\u4e00-\\u9fa5\\\\.\\/]+)\\}", stem);
		if (!strs.isEmpty()) {
			String src = stem;
			for (String string : strs) {
				if (zipFile == null) {
					break;
				}
				ZipEntry imgEntry = zipFile.getEntry(string);
				if (string == "" || imgEntry == null) {
					result.setSuccess(false);
					result.setEmsg("文件：" + fileName + "\n第" + (i) + "行错误，题干对应图片" + "${" + string + "}不存在!");
					return result;
				}
				String imgSuff = imgEntry.getName().substring(imgEntry.getName().lastIndexOf(".") + 1);
				InputStream img = zipFile.getInputStream(imgEntry);
				Base64 base64 = new Base64();
				byte bt[] = new byte[img.available()];
				img.read(bt);
				String code = base64.encodeToString(bt);
				src = src.replace("${" + string + "}", "<img src='data:image/" + imgSuff + ";base64," + code + "'>");
			}
			stem = src;
		}

		if (contentList.size() == 0) {
			result.setSuccess(false);
			result.setEmsg("第" + i + "行错误，选项不能为空!");
			return result;
		}
		String type = oneData.get("类型");
		if (type == "") {
			result.setSuccess(false);
			result.setEmsg("文件：" + fileName + "\n第" + i + "行错误，类型不能为空!");
			return result;
		}
		String ani = oneData.get("解析");
		String answer = oneData.get("答案");
		if (answer == "") {
			result.setSuccess(false);
			result.setEmsg("文件：" + fileName + "\n第" + i + "行错误，答案不能为空!");
			return result;
		}
		if (!answer.matches("[a-zA-z]+")) {
			result.setSuccess(false);
			result.setEmsg("文件：" + fileName + "\n第" + i + "行错误，答案格式错误!");
			return result;
		}
		int multi = answer.length() > 1 ? 1 : 0;
		if (multi == 1) {
			stem = "(多选)" + stem;
		} else {
			stem = "(单选)" + stem;
		}
		int total = 0;
		int wrongnum = 0;
		int rightnum = 0;
		double rate = 0;
		// abcd选项-------contentList
		Object[] param = new Object[] { account, type, stem, JSONUtil.toJson(contentList), ani, answer, multi, total,
				wrongnum, rightnum, rate };
		// 放到自定义的参数里面
		result.setCustom(param);
		result.setStatus(200);
		return result;
	}

	@Override
	public String getOptionType(String value) {

		return null;
	}

}
