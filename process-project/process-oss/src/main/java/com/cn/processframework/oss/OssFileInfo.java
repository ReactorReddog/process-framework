package com.cn.processframework.oss;

import lombok.Data;

import java.util.Date;

/**
 * @author apple
 * @desc oss文件基本信息
 * @since 1.0.0
 */
@Data
public class OssFileInfo {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 文件hash值
	 */
	public String hash;
	/**
	 * 文件大小
	 */
	private long length;
	/**
	 * 文件上传时间
	 */
	private Date putTime;
	/**
	 * 文件contentType
	 */
	private String contentType;
}
