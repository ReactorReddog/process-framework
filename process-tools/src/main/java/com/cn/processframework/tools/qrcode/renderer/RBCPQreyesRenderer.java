package com.cn.processframework.tools.qrcode.renderer;

import com.cn.processframework.tools.qrcode.QreyesFormat;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RBCPQreyesRenderer extends RBRPQreyesRenderer {

	@Override
	public void checkFormat(QreyesFormat format) {
		if (QreyesFormat.R_BORDER_C_POINT != format) {
			throw new IllegalArgumentException("Can only render R_BORDER_C_POINT, but got " + format);
		}
	}

	@Override
	public Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Ellipse2D.Double(x, y, w, h);
	}

}
