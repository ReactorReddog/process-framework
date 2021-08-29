package com.cn.processframework.tools.qrcode;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author apple
 * @desc 二维码参数
 * @since 1.0.0.RELEASE
 */
public class Qrcode implements Serializable {
	/**
	 * 二维码
	 */
	private BufferedImage image;
	/**
	 * 二维码logo
	 */
	private Logo logo;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public static final class Logo {

		private String path;

		private boolean remote;

		public Logo(String path, boolean remote) {
			this.path = path;
			this.remote = remote;
		}

		public String getPath() {
			return path;
		}

		public boolean isRemote() {
			return remote;
		}

	}

}
