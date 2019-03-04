package com.linlihudong.util;

import java.util.HashMap;
import java.util.Map;

public class DataResult extends HashMap<String, Object>{
	private static final long serialVersionUID = 1L;
	public DataResult() {
		put("status", "1");
	}
	public static DataResult errorCoderAndData(int code,String data){
		DataResult r = new DataResult();
		r.put("status", code);
		r.put("data", data);
		return r;
	}
	public static DataResult error() {
		return error(500, "未知异常，请联系管理员");
	}
	public static DataResult nologinError() {
		return error(401, "没有登录");
	}
	
	public static DataResult noAuthError() {
		return error(403, "没有权限");
	}
	
	public static DataResult error(String msg) {
		return error(500, msg);
	}
	public static DataResult error(int code, String msg) {
		DataResult r = new DataResult();
		r.put("status", code);
		r.put("msg", msg);
		return r;
	}
	public static DataResult ok(String msg) {
		DataResult r = new DataResult();
		r.put("msg", msg);
		return r;
	}
	
	public static DataResult ok(Map<String, Object> map) {
		DataResult r = new DataResult();
		r.putAll(map);
		return r;
	}
	
	public static DataResult ok() {
		return new DataResult();
	}

	public DataResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
