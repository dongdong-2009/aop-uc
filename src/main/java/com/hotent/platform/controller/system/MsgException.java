/**
 * 2017年11月7日MsgException.java上午10:20:13aop-uc-internetMsgExceptionTODO
 */
package com.hotent.platform.controller.system;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author Administrator
 *MsgException$
 */
public class MsgException extends Exception {

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return super.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		// TODO Auto-generated method stub
		return super.getCause();
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		// TODO Auto-generated method stub
		return super.initCause(cause);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		// TODO Auto-generated method stub
		return super.fillInStackTrace();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return super.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		// TODO Auto-generated method stub
		super.setStackTrace(stackTrace);
	}

	public MsgException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MsgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MsgException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MsgException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MsgException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
