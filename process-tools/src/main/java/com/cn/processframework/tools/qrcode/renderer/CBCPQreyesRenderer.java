package com.cn.processframework.tools.qrcode.renderer;

import com.cn.processframework.tools.qrcode.QreyesFormat;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Deprecated reason: low recognition rate
 *
 * @author Bosco.Liao
 * @since 1.3.0
 */
@Deprecated
public class CBCPQreyesRenderer extends CBRPQreyesRenderer {

	@Override
	public void checkFormat(QreyesFormat format) {
		if (QreyesFormat.C_BORDER_C_POINT != format) {
			throw new IllegalArgumentException("Can only render C_BORDER_C_POINT, but got " + format);
		}
	}

	@Override
	public Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Ellipse2D.Double(x, y, w, h);
	}

}
