package com.xzq.utils;

public class Result {
	private Object data;
	private Integer status;
	//操作是否成功
	private boolean success;
	//操作的错误码
	private String ecode;
	//操作的错误信息
	private String emsg;
	//自定义其他参数结果
	private Object custom;  //试题导入时使用 一定个对象
   
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getEmsg() {
		return emsg;
	}

	public void setEmsg(String emsg) {
		this.emsg = emsg;
	}
     
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getCustom() {
		return custom;
	}

	public void setCustom(Object custom) {
		this.custom = custom;
	}

	@Override
	public String toString() {
		return "Result [data=" + data + ", status=" + status + ", success=" + success + ", ecode=" + ecode + ", emsg="
				+ emsg + ", custom=" + custom + "]";
	}
   
	

}
