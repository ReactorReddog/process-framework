package com.cn.processframework.tools.qrcode;

/**
 * @author apple
 * @desc http连接异常处理
 * @since 1.0.0.RELEASE
 */
public class HttpConnectionException extends RuntimeException {

	private static final long serialVersionUID = -4357540105881789619L;

	public HttpConnectionException() {
		super();

	}

	public HttpConnectionException(String message) {
		super(message);
	}

	public HttpConnectionException(Throwable cause) {
		super(cause);
	}

	public HttpConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
