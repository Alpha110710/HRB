package com.hrb.biz.exception;

import com.hrb.utils.android.LogUtil;



public class ZYException extends Exception {

	private static final long serialVersionUID = 153504232797165313L;

	public ZYException(){
		super();
	}
	
	public ZYException(Throwable e){
		super(e);
		LogUtil.e(e);
	}
	
	public ZYException(String message){
		super(message);
	}
	
	public ZYException(String message, Throwable e){
		super(message, e);
		LogUtil.e(e, message);
	}
}
