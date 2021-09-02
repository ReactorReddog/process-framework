package com.cn.processframework.tools.qrcode.context;

import com.cn.processframework.tools.qrcode.Codectx;
import lombok.Data;

import java.io.Serializable;

/**
 * @author apple
 * @desc logo配置
 * @since 1.0.0.RELEASE
 */
@Data
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
}
