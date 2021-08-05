package com.cn.processframework.oss;

import lombok.Data;

/**
 * @author apple
 * @desc 文件基本信息
 * @since
 */
@Data
public class ProcessFileInfo {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 域名地址
	 */
	private String domain;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 初始文件名
	 */
	private String originalName;
	/**
	 * 附件表ID
	 */
	private Long attachId;
}
