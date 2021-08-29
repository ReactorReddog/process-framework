package com.cn.processframework.tools.qrcode.context;


import java.awt.image.BufferedImage;

/**
 * @author apple
 * @desc
 * @since 1.0.0.RELEASE
 */
public class ThreadQrcode extends ThreadLocal<Qrcode> {

	@Override
	protected Qrcode initialValue() {
		return new Qrcode();
	}

	public void setLogo(String path, boolean remote) {
		get().setLogo(new Qrcode.Logo(path, remote));
	}

	public void setImage(BufferedImage image) {
		get().setImage(image);
	}

	public BufferedImage getImage() {
		return get().getImage();
	}

	public Qrcode.Logo getLogo() {
		return get().getLogo();
	}

}
