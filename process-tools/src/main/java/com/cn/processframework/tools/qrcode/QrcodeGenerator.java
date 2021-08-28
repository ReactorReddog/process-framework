package com.cn.processframework.tools.qrcode;

import com.cn.processframework.tools.QrCodeExcutorClient;

import java.awt.image.BufferedImage;

public interface QrcodeGenerator extends QrCodeExcutorClient {

	QrcodeConfig getQrcodeConfig();

	QrcodeGenerator generate(String content, String logoPath);

	QrcodeGenerator setLogo(String path, boolean remote);

	BufferedImage getImage(boolean clear);

	default QrcodeGenerator setLogo(String path) {
		return setLogo(path, false);
	}

	default QrcodeGenerator setRemoteLogo(String path) {
		return setLogo(path, true);
	}

	@Override
	default BufferedImage getImage() {
		return getImage(true);
	}

}
