package com.cn.processframework.tools.qrcode.renderer;

import com.cn.processframework.tools.qrcode.ReflectionUtils;
import com.cn.processframework.tools.qrcode.context.QreyesFormat;
import com.cn.processframework.tools.qrcode.context.QreyesPosition;
import com.cn.processframework.tools.qrcode.context.QreyesRenderer;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class DR2BRPQreyesRenderer implements QreyesRenderer {

	private static final int ARC = 13;

	@Override
	public void render(BufferedImage image, QreyesFormat format, QreyesPosition position, Color slave, Color border,
					   Color point) {

		checkFormat(format);

		int width = image.getWidth(), height = image.getHeight();
		int borderSize = position.getBorderSize(width);

		final String[] directions = { "topLeft", "topRight", "bottomLeft" };

		Graphics2D graphics = image.createGraphics();
		graphics.setBackground(slave);
		for (String direction : directions) {

			// clear area by slave color
			int[] rect = (int[]) ReflectionUtils.invokeMethod(position, direction + "Rect");
			graphics.clearRect(rect[0], rect[1], rect[2], rect[3]);

			// draw code-eyes border
			int x = rect[0] + borderSize / 2, y = rect[1] + borderSize / 2;
			Shape shape = new RoundRectangle2D.Double(x, y, rect[2] - borderSize, rect[3] - borderSize, ARC, ARC);
			graphics.setColor(slave);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.fill(shape);
			graphics.setStroke(new BasicStroke(borderSize));
			graphics.setColor(border);
			graphics.draw(shape);

			// draw right-angle
			Graphics graphicsX = graphics.create();
			if (directions[0].equals(direction)) {
				graphicsX.drawLine(x, y, x, y + ARC);
				graphicsX.drawLine(x, y, x + ARC, y);
				x = position.getLeftEndX() - borderSize / 2 - 1;
				y = position.getTopEndY() - borderSize / 2 - 1;
				graphicsX.drawLine(x, y, x, y - ARC);
				graphicsX.drawLine(x, y, x - ARC, y);
			} else if (directions[1].equals(direction)) {
				x = position.getRightStartX() + borderSize / 2;
				y = position.getTopEndY() - borderSize / 2 - 1;
				graphicsX.drawLine(x, y, x, y - ARC);
				graphicsX.drawLine(x, y, x + ARC, y);
				x = position.getRightEndX() - borderSize / 2 - 1;
				y = position.getTopStartY() + borderSize / 2;
				graphicsX.drawLine(x, y, x, y + ARC);
				graphicsX.drawLine(x, y, x - ARC, y);
			} else {
				x = position.getLeftStartX() + borderSize / 2;
				y = position.getBottomEndY() - borderSize / 2 - 1;
				graphicsX.drawLine(x, y, x, y - ARC);
				graphicsX.drawLine(x, y, x + ARC, y);
				x = position.getLeftEndX() - borderSize / 2 - 1;
				y = position.getBottomStartY() + borderSize / 2;
				graphicsX.drawLine(x, y, x, y + ARC);
				graphicsX.drawLine(x, y, x - ARC, y);
			}

			// draw code-eyes point
			rect = (int[]) ReflectionUtils.invokeMethod(position.focusPoint(width, height), direction + "Point");
			shape = getPointShape(rect[0], rect[1], rect[2], rect[3], 5, 5);
			graphics.setColor(point);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.fill(shape);

			// reset border setting
			graphics.setStroke(new BasicStroke(0));
			graphics.setColor(point);
			graphics.draw(shape);
		}

		graphics.dispose();
		image.flush();
	}

	@Override
	public Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new RoundRectangle2D.Double(x, y, w, h, arcw, arch);
	}

	@Override
	public void checkFormat(QreyesFormat format) {
		if (QreyesFormat.DR2_BORDER_R_POINT != format) {
			throw new IllegalArgumentException("Can only render DR2_BORDER_R_POINT, but got " + format);
		}
	}

}
