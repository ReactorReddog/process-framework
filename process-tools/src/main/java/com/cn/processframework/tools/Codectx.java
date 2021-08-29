package com.cn.processframework.tools;
/**
 * @author apple
 * @package com.cn.processframework.tools
 * @desc <p>qrCode生成配置abstract类</p>
 * @since 1.0.0.RERLEASE
 */
public final class Codectx {
	/******************************************************************/
	// 二维码相关
	/******************************************************************/

	/**
	 * 二维码默认宽度
	 */
	public static final int DEFAULT_CODE_WIDTH = 250;
	/**
	 * 二维码默认高度
	 */
	public static final int DEFAULT_CODE_HEIGHT = 250;
	/**
	 *
	 */
	public static final int DEFAULT_CODE_MARGIN = 10;

	public static final int DEFAULT_CODE_PADDING = 0;

	public static final int DEFAULT_CODE_BORDER_SIZE = 0;

	public static final int DEFAULT_CODE_BORDER_RADIUS = 0;

	public static final int DEFAULT_CODE_BORDER_DASH_GRANULARITY = 5;
	/**
	 * 二维码默认边框颜色
	 */
	public static final String DEFAULT_CODE_BORDER_COLOR = "#808080";
	/**
	 * 二维码主内容区域颜色
	 */
	public static final String DEFAULT_CODE_MASTER_COLOR = "#000000";
	/**
	 * 二维码背景色
	 */
	public static final String DEFAULT_CODE_SLAVE_COLOR = "#FFFFFF";

	/******************************************************************/
	// logo相关
	/******************************************************************/
	/**
	 * logo圆角度
	 */
	public static final int DEFAULT_LOGO_RATIO = 5;
	/**
	 * logo边框大小
	 */
	public static final int DEFAULT_LOGO_BORDER_SIZE = 2;

	public static final int DEFAULT_LOGO_ARCWIDTH = 10;

	public static final int DEFAULT_LOGO_PADDING = 5;

	public static final int DEFAULT_LOGO_MARGIN = 5;

	public static final int DEFAULT_LOGO_PANEL_RADIUS = 15;
	/**
	 * logo边框颜色
	 */
	public static final String DEFAULT_LOGO_BORDER_COLOR = "#808080";
	/**
	 * logo默认背景色
	 */
	public static final String DEFAULT_LOGO_BACKGROUND_COLOR = "#FFFFFF";

	public static final String DEFAULT_LOGO_PANEL_COLOR = "#FFFFFF";

	/******************************************************************/
	// 写出相关
	/******************************************************************/
	/**
	 * 默认png格式
	 */
	public static final String IMAGE_TYPE = "PNG";

	/**
	 * 边框样式类型
	 */
	public enum BorderStyle {

		SOLID/*实线型*/, DASHED/*虚线型*/;
	}

}