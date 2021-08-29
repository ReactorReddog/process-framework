package com.cn.processframework.tools.qrcode.context;

import com.cn.processframework.tools.qrcode.Codectx;
import com.cn.processframework.tools.qrcode.GenericCodeConfig;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author apple
 * @desc 二维码配置
 */
public class QrcodeConfig extends GenericCodeConfig {

	private static ConcurrentHashMap<EncodeHintType, Object> hints = null;

	static {
		hints = new ConcurrentHashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, Codectx.DEFAULT_CODE_PADDING);
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	}

	/**
	 * 二维码背景层大小
	 */
	private int margin = Codectx.DEFAULT_CODE_MARGIN;
	/**
	 * 二维码padding参数
	 */
	@Deprecated
	private int padding = Codectx.DEFAULT_CODE_PADDING;
	/**
	 * 二维码内容外区域添加边框 厚度
	 */
	private int borderSize = Codectx.DEFAULT_CODE_BORDER_SIZE;
	/**
	 * 边框圆角度
	 */
	private int borderRadius = Codectx.DEFAULT_CODE_BORDER_RADIUS;
	/**
	 * 边框背景色
	 */
	private String borderColor = Codectx.DEFAULT_CODE_BORDER_COLOR;
	/**
	 * 边框的类型 属于虚线型还是实现型
	 */
	private Codectx.BorderStyle borderStyle = Codectx.BorderStyle.DASHED;
	/**
	 * 二维码背景虚线密集度
	 */
	private int borderDashGranularity = Codectx.DEFAULT_CODE_BORDER_DASH_GRANULARITY;
	/**
	 * 码眼颜色 不包含内部
	 */
	private String codeEyesBorderColor = Codectx.DEFAULT_CODE_MASTER_COLOR;

	private String codeEyesPointColor = Codectx.DEFAULT_CODE_MASTER_COLOR;
	/**
	 * 码眼及内部图形类型  默认：含码眼及其内部图形 矩形
	 */
	private QreyesFormat codeEyesFormat = QreyesFormat.R_BORDER_R_POINT;
	/**
	 * logo配置
	 */
	private final LogoConfig logoConfig = new LogoConfig();

	public QrcodeConfig() {
		this(Codectx.DEFAULT_CODE_WIDTH, Codectx.DEFAULT_CODE_HEIGHT);
	}

	public QrcodeConfig(int width, int height) {
		super(width, height);
	}

	public int getPadding() {
		return padding;
	}

	public QrcodeConfig setPadding(int padding) {
		this.padding = padding;
		// added in v1.3.0
		addHint(EncodeHintType.MARGIN, padding);
		return this;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public QrcodeConfig setBorderSize(int borderSize) {
		this.borderSize = borderSize;
		return this;
	}

	public int getBorderRadius() {
		return borderRadius;
	}

	public QrcodeConfig setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
		return this;
	}

	public Codectx.BorderStyle getBorderStyle() {
		return borderStyle;
	}

	public QrcodeConfig setBorderStyle(Codectx.BorderStyle style) {
		this.borderStyle = style;
		return this;
	}

	public int getBorderDashGranularity() {
		return borderDashGranularity;
	}

	/**
	 * This setting does not work, if the style is not BorderStyle.DASHED.
	 */
	public QrcodeConfig setBorderDashGranularity(int granularity) {
		this.borderDashGranularity = granularity;
		return this;
	}

	public String getCodeEyesBorderColor() {
		return codeEyesBorderColor;
	}

	public QrcodeConfig setCodeEyesBorderColor(String codeEyesBorderColor) {
		this.codeEyesBorderColor = codeEyesBorderColor;
		return this;
	}

	public String getCodeEyesPointColor() {
		return codeEyesPointColor;
	}

	public QrcodeConfig setCodeEyesPointColor(String codeEyesPointColor) {
		this.codeEyesPointColor = codeEyesPointColor;
		return this;
	}

	public QreyesFormat getCodeEyesFormat() {
		return codeEyesFormat;
	}

	public QrcodeConfig setCodeEyesFormat(QreyesFormat codeEyesFormat) {
		this.codeEyesFormat = codeEyesFormat;
		return this;
	}

	public LogoConfig getLogoConfig() {
		return logoConfig;
	}

	public int getMargin() {
		return margin;
	}

	public QrcodeConfig setMargin(int margin) {
		this.margin = margin;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public QrcodeConfig setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	@Override
	public QrcodeConfig setWidth(int width) {
		return (QrcodeConfig) super.setWidth(width);
	}

	@Override
	public QrcodeConfig setHeight(int height) {
		return (QrcodeConfig) super.setHeight(height);
	}

	@Override
	public QrcodeConfig setMasterColor(String masterColor) {
		super.setMasterColor(masterColor);
		this.setCodeEyesBorderColor(masterColor);
		this.setCodeEyesPointColor(masterColor);
		return this;
	}

	@Override
	public QrcodeConfig setSlaveColor(String slaveColor) {
		super.setSlaveColor(slaveColor);
		return this;
	}

	public QrcodeConfig setLogoRatio(int ratio) {
		getLogoConfig().setRatio(ratio);
		return this;
	}

	public QrcodeConfig setLogoBorderSize(int borderSize) {
		getLogoConfig().setBorderSize(borderSize);
		return this;
	}

	public QrcodeConfig setLogoPadding(int padding) {
		getLogoConfig().setPadding(padding);
		return this;
	}

	public QrcodeConfig setLogoBorderColor(String borderColor) {
		getLogoConfig().setBorderColor(borderColor);
		return this;
	}

	public QrcodeConfig setLogoBackgroundColor(String backgroundColor) {
		getLogoConfig().setBackgroundColor(backgroundColor);
		return this;
	}

	public QrcodeConfig setLogoMargin(int margin) {
		getLogoConfig().setMargin(margin);
		return this;
	}

	public QrcodeConfig setLogoPanelArcWidth(int arcWidth) {
		getLogoConfig().setPanelArcWidth(arcWidth);
		return this;
	}

	public QrcodeConfig setLogoPanelArcHeight(int arcHeight) {
		getLogoConfig().setPanelArcHeight(arcHeight);
		return this;
	}

	public QrcodeConfig setPanelColor(String panelColor) {
		getLogoConfig().setPanelColor(panelColor);
		return this;
	}

	public Map<EncodeHintType, Object> getHints() {
		return hints;
	}

	public Map<EncodeHintType, Object> addHint(EncodeHintType type, Object value) {
		Map<EncodeHintType, Object> hints = getHints();
		hints.put(type, value);
		return hints;
	}

	public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
		addHint(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
	}

}
