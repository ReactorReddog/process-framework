package com.cn.processframework.tools.qrcode.context;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author apple
 * @desc 二维码参数
 * @since 1.0.0.RELEASE
 */
@Data
public class Qrcode implements Serializable {
	/**
	 * 二维码
	 */
	private BufferedImage image;
	/**
	 * 二维码logo
	 */
	private Logo logo;

	@Data
	@AllArgsConstructor
	public static final class Logo {
		/**
		 * logo路径
		 */
		private String path;

		private boolean remote;
	}

}
