package com.cn.processframework.tools.qrcode.context;


import com.cn.processframework.tools.qrcode.renderer.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author apple
 * @desc
 * @since
 */
public final class MultiFormatQreyesRenderer implements QreyesRenderer {

	@Override
	public void render(BufferedImage image, QreyesFormat format, QreyesPosition position, Color slave, Color border,
			Color point) {

		QreyesRenderer renderer = switch (format) {
			case R_BORDER_R_POINT -> new RBRPQreyesRenderer();
			case R_BORDER_C_POINT -> new RBCPQreyesRenderer();
			case C_BORDER_R_POINT -> new CBRPQreyesRenderer();
			case C_BORDER_C_POINT -> new CBCPQreyesRenderer();
			case R2_BORDER_R_POINT -> new R2BRPQreyesRenderer();
			case R2_BORDER_C_POINT -> new R2BCPQreyesRenderer();
			case DR2_BORDER_R_POINT -> new DR2BRPQreyesRenderer();
			case DR2_BORDER_C_POINT -> new DR2BCPQreyesRenderer();
		};

		renderer.render(image, format, position, slave, border, point);
	}

}
