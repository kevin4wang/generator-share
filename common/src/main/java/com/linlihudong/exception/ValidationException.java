package com.linlihudong.exception;

public class ValidationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationException(){
		super("数据校验异常");
	}
	public ValidationException(String msg){
		super(msg);
	}

}
