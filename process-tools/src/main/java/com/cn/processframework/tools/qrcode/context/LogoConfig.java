package com.cn.processframework.tools.qrcode.context;

import com.cn.processframework.tools.qrcode.Codectx;

import java.io.Serializable;

/**
 * @author apple
 * @desc logo配置
 * @since 1.0.0.RELEASE
 */
public class LogoConfig implements Serializable {
	/**
	 * logo圆角度
	 */
	private int ratio = Codectx.DEFAULT_LOGO_RATIO;
	/**
	 * logo宽度
	 */
	private int arcWidth = Codectx.DEFAULT_LOGO_ARCWIDTH;
	/**
	 * logo高度
	 */
	private int arcHeight = Codectx.DEFAULT_LOGO_ARCWIDTH;
	/**
	 * logo边框厚度
	 */
	private int borderSize = Codectx.DEFAULT_LOGO_BORDER_SIZE;
	/**
	 * 距离内容度
	 */
	private int padding = Codectx.DEFAULT_LOGO_PADDING;
	/**
	 * 边框色
	 */
	private String borderColor = Codectx.DEFAULT_LOGO_BORDER_COLOR;
	/**
	 * 背景色
	 */
	private String backgroundColor = Codectx.DEFAULT_LOGO_BACKGROUND_COLOR;
	/**
	 * 内容撑开度
	 */
	private int margin = Codectx.DEFAULT_LOGO_MARGIN;
	/**
	 *
	 */
	private String panelColor = Codectx.DEFAULT_LOGO_PANEL_COLOR;
	/**
	 *
	 */
	private int panelArcWidth = Codectx.DEFAULT_LOGO_PANEL_RADIUS;
	/**
	 *
	 */
	private int panelArcHeight = Codectx.DEFAULT_LOGO_PANEL_RADIUS;

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public int getArcWidth() {
		return arcWidth;
	}

	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
	}

	public int getArcHeight() {
		return arcHeight;
	}

	public void setArcHeight(int arcHeight) {
		this.arcHeight = arcHeight;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public String getPanelColor() {
		return panelColor;
	}

	public void setPanelColor(String panelColor) {
		this.panelColor = panelColor;
	}

	public int getPanelArcWidth() {
		return panelArcWidth;
	}

	public void setPanelArcWidth(int panelArcWidth) {
		this.panelArcWidth = panelArcWidth;
	}

	public int getPanelArcHeight() {
		return panelArcHeight;
	}

	public void setPanelArcHeight(int panelArcHeight) {
		this.panelArcHeight = panelArcHeight;
	}

}
