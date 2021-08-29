package com.cn.processframework.tools.qrcode.renderer;

import com.cn.processframework.tools.qrcode.context.QreyesFormat;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class R2BCPQreyesRenderer extends R2BRPQreyesRenderer {

	@Override
	public void checkFormat(QreyesFormat format) {
		if (QreyesFormat.R2_BORDER_C_POINT != format) {
			throw new IllegalArgumentException("Can only render R2_BORDER_C_POINT, but got " + format);
		}
	}

	@Override
	public Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Ellipse2D.Double(x, y, w, h);
	}

}
