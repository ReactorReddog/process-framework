package com.cn.processframework.tools.qrcode;

import java.io.Serializable;

/**
 * @author apple
 * @desc 二维码生成配置
 * @since 1.0.0.RELEASE
 */
public abstract class GenericCodeConfig implements Serializable {
	/**
	 * 宽度
	 */
	private int width;
	/**
	 * 高度
	 */
	private int height;
	/**
	 * 二维码内容区域颜色
	 */
	private String masterColor = Codectx.DEFAULT_CODE_MASTER_COLOR;
	/**
	 * 二维码背景颜色
	 */
	private String slaveColor = Codectx.DEFAULT_CODE_SLAVE_COLOR;

	/**
	 * 构造函数
	 * @param width 宽度
	 * @param height 高度
	 */
	public GenericCodeConfig(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 构造参数
	 * @param width 宽度
	 * @param height 高度
	 * @param masterColor 主内容区域颜色
	 * @param slaveColor 背景色
	 */
	public GenericCodeConfig(int width, int height, String masterColor, String slaveColor) {
		this(width, height);
		this.masterColor = masterColor;
		this.slaveColor = slaveColor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getMasterColor() {
		return masterColor;
	}

	public String getSlaveColor() {
		return slaveColor;
	}

	public GenericCodeConfig setWidth(int width) {
		this.width = width;
		return this;
	}

	public GenericCodeConfig setHeight(int height) {
		this.height = height;
		return this;
	}

	public GenericCodeConfig setSlaveColor(String slaveColor) {
		this.slaveColor = slaveColor;
		return this;
	}

	public GenericCodeConfig setMasterColor(String masterColor) {
		this.masterColor = masterColor;
		return this;
	}

}
