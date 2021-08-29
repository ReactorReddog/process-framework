package com.cn.processframework.tools.qrcode.context;

/**
 * @author apple
 * @desc 二维码码眼样式控制
 * @since 1.0.0.RELEASE
 */
public enum QreyesFormat {

	/**
	 * 含码眼及其内部图形 矩形
	 */
	R_BORDER_R_POINT,

	/**
	 * 码眼矩形 内部圆形
	 */
	R_BORDER_C_POINT,

	/**
	 * 码眼外部 圆形边框 内部默认
	 */
	C_BORDER_R_POINT,

	/**
	 * 码眼外部及内部图形 圆
	 */
	C_BORDER_C_POINT,

	/**
	 * 码眼及内部图形 圆角
	 */
	R2_BORDER_R_POINT,

	/**
	 * 码眼圆角 内部圆
	 */
	R2_BORDER_C_POINT,

	/**
	 * 码眼叶形 内部圆角
	 */
	DR2_BORDER_R_POINT,

	/**
	 * 码眼叶形 内部圆
	 */
	DR2_BORDER_C_POINT;

}
