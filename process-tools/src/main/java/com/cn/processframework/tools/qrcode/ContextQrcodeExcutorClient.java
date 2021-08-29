package com.cn.processframework.tools.qrcode;

import com.cn.processframework.tools.QrCodeExcutorClient;

import java.awt.image.BufferedImage;

/**
 * @author apple
 * @desc 二维码生成类
 * @since 1.0.0.RELEASE
 */
public interface ContextQrcodeExcutorClient extends QrCodeExcutorClient {
	/**
	 * 获取二维码配置信息
	 * @return QrcodeConfig
	 */
	QrcodeConfig getQrcodeConfig();

	/**
	 * 二维码生成
	 * @param content 内容
	 * @param logoPath logo地址
	 * @return this
	 */
	ContextQrcodeExcutorClient generate(String content, String logoPath);

	/**
	 * 设置logo
	 * @param path logo地址
	 * @param remote
	 * @return this
	 */
	ContextQrcodeExcutorClient setLogo(String path, boolean remote);

	/**
	 * 获取二维码
	 * @param clear 是否获取后删除
	 * @return 图片流
	 */
	BufferedImage getImage(boolean clear);

	default ContextQrcodeExcutorClient setLogo(String path) {
		return setLogo(path, false);
	}

	default ContextQrcodeExcutorClient setRemoteLogo(String path) {
		return setLogo(path, true);
	}

	@Override
	default BufferedImage getImage() {
		return getImage(true);
	}

}
