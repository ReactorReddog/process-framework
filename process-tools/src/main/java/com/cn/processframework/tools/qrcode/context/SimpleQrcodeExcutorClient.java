package com.cn.processframework.tools.qrcode.context;

import com.cn.processframework.tools.qrcode.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.Version;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author apple
 * @desc 二维码简单实现
 * @since 1.0.0.RELEASE
 */
public class SimpleQrcodeExcutorClient extends AbstractQrCodeContextQrcodeExcutorClient implements ContextQrcodeExcutorClient {

	private static final ThreadQrcode QRCODE = new ThreadQrcode();

	private final QrcodeConfig qrcodeConfig;

	public SimpleQrcodeExcutorClient() {
		this(new QrcodeConfig());
	}

	public SimpleQrcodeExcutorClient(QrcodeConfig qrcodeConfig) {
		super();
		this.qrcodeConfig = qrcodeConfig;
	}

	@Override
	public ContextQrcodeExcutorClient setLogo(String path, boolean remote) {
		QRCODE.setLogo(path, remote);
		return this;
	}

	@Override
	public QrcodeConfig getQrcodeConfig() {
		return this.qrcodeConfig;
	}

	@Override
	public ContextQrcodeExcutorClient generate(String content) {
		QRCODE.setImage(generateQrcode(getQrcodeConfig(), QRCODE.getLogo(), content));
		return this;
	}

	@Override
	public ContextQrcodeExcutorClient generate(String content, String logoPath) {
		return (ContextQrcodeExcutorClient) setLogo(logoPath).generate(content);
	}

	@Override
	public BufferedImage getImage(boolean clear) {
		try {
			return QRCODE.getImage();
		} finally {
			if (clear) {
				clear();
			}
		}
	}

	@Override
	public void clear() {
		QRCODE.remove();
	}

	@Override
	public boolean toFile(String pathname) throws IOException {
		try {
			return ImageIO.write(QRCODE.getImage(), Codectx.IMAGE_TYPE, new File(pathname));
		} finally {
			clear();
		}
	}

	@Override
	public boolean toStream(OutputStream output) throws IOException {
		try {
			return ImageIO.write(QRCODE.getImage(), Codectx.IMAGE_TYPE, output);
		} finally {
			clear();
		}
	}

	/**
	 * 生成二维码图片流
	 * @param config {@link QrcodeConfig} 二维码配置
	 * @param logo {@link Qrcode.Logo} logo地址
	 * @param content qrcode content 二维码内容
	 * @return BufferedImage instance
	 */
	private static BufferedImage generateQrcode(QrcodeConfig config, Qrcode.Logo logo, final String content) {
		try {

			QrcodeWriter.QRCodeBitMatrix m = new QrcodeWriter().encodeX(content, BarcodeFormat.QR_CODE, config.getWidth(),
					config.getHeight(), config.getHints());

			BufferedImage image = toBufferedImage(m, config);

			/**
			 * render image margin
			 */
			image = setRadius(image, config.getBorderRadius(), config.getBorderSize(), config.getBorderColor(),
					config.getBorderStyle(), config.getBorderDashGranularity(), config.getMargin());

			/**
			 * insert logo in the middle of the image
			 */
			if (logo != null && logo.getPath() != null && logo.getPath().length() > 0) {
				byte[] bytes;
				if (logo.isRemote()) {
					bytes = HttpUtils.readStreamToByteArray(logo.getPath());
				} else {
					bytes = FileUtils.readFileToByteArray(new File(logo.getPath()));
				}
				if (bytes.length > 0) {
					addLogo(image, bytes, config.getLogoConfig());
				}
			}
			return image;

		} catch (Exception e) {
			throw new QrCodeException(e);
		}
	}
	
	public static void addLogo(final BufferedImage image, final byte[] logo, final LogoConfig config)
			throws IOException {
		ByteArrayInputStream input = new ByteArrayInputStream(logo);
		BufferedImage srcImage = image, logoImage = null;
		logoImage = ImageIO.read(input);
		if (logoImage == null) {
			return;
		}
		Graphics2D graphics = srcImage.createGraphics();
		/**
		 * get logo width & height
		 */
		final int ratio = config.getRatio();
		int ratioWidthOfCodeImage = srcImage.getWidth() / ratio;
		int ratioHeightOfCodeImage = srcImage.getHeight() / ratio;
		int width = logoImage.getWidth() > ratioWidthOfCodeImage ? ratioWidthOfCodeImage : logoImage.getWidth();
		int height = logoImage.getHeight() > ratioHeightOfCodeImage ? ratioHeightOfCodeImage : logoImage.getHeight();
		/**
		 * get logo panel position
		 */
		int padding = config.getPadding() * 2;
		int margin = config.getMargin() * 2;
		int w = width + padding + margin, h = height + padding + margin;
		int positionX = (srcImage.getWidth() - w) / 2;
		int positionY = (srcImage.getHeight() - h) / 2;
		/**
		 * render panel, 1px offset is added to ensure center position
		 */
		Shape shape = new RoundRectangle2D.Float(positionX, positionY, (float) (w + 1), (float) (h + 1),
				config.getPanelArcWidth(), config.getPanelArcHeight());
		graphics.setColor(getColor(config.getPanelColor()));
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.fill(shape);
		/**
		 * get logo position
		 */
		positionX += config.getMargin();
		positionY += config.getMargin();
		/**
		 * render logo background
		 */
		shape = new RoundRectangle2D.Float(positionX, positionY, width + padding, height + padding,
				config.getArcWidth(), config.getArcHeight());
		graphics.setColor(getColor(config.getBackgroundColor()));
		graphics.fill(shape);
		/**
		 * draw logo
		 */
		positionX += config.getPadding();
		positionY += config.getPadding();
		graphics.drawImage(logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), positionX, positionY, null);
		/**
		 * border
		 */
		graphics.setStroke(new BasicStroke(config.getBorderSize()));
		graphics.setColor(getColor(config.getBorderColor()));
		/**
		 * anti-aliasing
		 */
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.draw(shape);

		/**
		 * flush
		 */
		graphics.dispose();
		logoImage.flush();
		srcImage.flush();
	}
	
	private static BufferedImage toBufferedImage(final QrcodeWriter.QRCodeBitMatrix matrix, final QrcodeConfig config) {

		BitMatrix bitMatrix = matrix.getBitMatrix();

		Version version = matrix.getQrcode().getVersion();

		int width = bitMatrix.getWidth(), height = bitMatrix.getHeight();

		/**
		 * Formula for calculating number of modules on each side: 
		 * (Version - 1) * 4 + 21.
		 */
		int modules = (version.getVersionNumber() - 1) * 4 + 21;
		int[] topLeftOnBit = bitMatrix.getTopLeftOnBit();

		QreyesPosition position = new QreyesPosition(modules, topLeftOnBit);

		int moduleHeight = position.getModuleHeight(height);
		int moduleWidth = position.getModuleWidth(width);

		/**
		 * Calculating codeEyes position.
		 */
		int leftStartX = topLeftOnBit[0] + moduleWidth * QreyesRenderStrategy.POINT_BORDER.getStart();
		int leftEndX = topLeftOnBit[0] + moduleWidth * QreyesRenderStrategy.POINT_BORDER.getEnd();
		int topStartY = topLeftOnBit[1] + moduleHeight * QreyesRenderStrategy.POINT_BORDER.getStart();
		int topEndY = topLeftOnBit[1] + moduleHeight * QreyesRenderStrategy.POINT_BORDER.getEnd();
		int rightStartX = topLeftOnBit[0] + moduleWidth * (modules - QreyesRenderStrategy.POINT_BORDER.getEnd());
		int rightEndX = width - topLeftOnBit[0] - moduleWidth * QreyesRenderStrategy.POINT_BORDER.getStart();
		// correct 1 pixel offset
		int bottomStartY = height - topLeftOnBit[1] - moduleHeight * QreyesRenderStrategy.POINT_BORDER.getEnd() - 1;
		int bottomEndY = height - topLeftOnBit[1] - moduleHeight * QreyesRenderStrategy.POINT_BORDER.getStart() - 1;

		/**
		 * Build image.
		 */
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// custom color.
		int masterColor = getColor(config.getMasterColor()).getRGB();
		int slaveColor = getColor(config.getSlaveColor()).getRGB();

		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				// top left
				if (x >= leftStartX && x < leftEndX && y >= topStartY && y < topEndY) {} 
				// top right
				else if (x >= rightStartX && x < rightEndX && y >= topStartY && y < topEndY) {} 
				// bottom left
				else if (x >= leftStartX && x < leftEndX && y >= bottomStartY && y < bottomEndY) {} 
				// non codeEyes region
				else {image.setRGB(x, y, bitMatrix.get(x, y) ? masterColor : slaveColor);}
			}
		}

		position.setPosition(leftStartX, leftEndX, topStartY, topEndY, rightStartX, rightEndX, bottomStartY,
				bottomEndY);
		Color border = getColor(config.getCodeEyesBorderColor());
		Color point = getColor(config.getCodeEyesPointColor());
		QreyesFormat format = config.getCodeEyesFormat();
		new MultiFormatQreyesRenderer().render(image, format, position, new Color(slaveColor), border, point);

		return image;
	}

}
