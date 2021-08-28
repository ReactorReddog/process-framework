package org.processframework.tools.test;

import com.cn.processframework.tools.IOUtils;
import com.cn.processframework.tools.qrcode.QrcodeGenerator;
import com.cn.processframework.tools.qrcode.QreyesFormat;
import com.cn.processframework.tools.qrcode.SimpleQrcodeGenerator;
import org.gradle.internal.impldep.org.junit.Assert;
import org.gradle.internal.impldep.org.junit.Before;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class TestQrGen {

	private static final String content = "https://baike.baidu.com/item/%E5%97%B7%E5%A4%A7%E5%96%B5/19817560?fr=aladdin";

	private QrcodeGenerator generator = new SimpleQrcodeGenerator();

	private String localLogoPath;

	@Before
	public void init() {
		URL url = this.getClass().getClassLoader().getResource("mates/AodaCat-1.png");
		this.localLogoPath = url.getFile();
	}

	@Test
	public void testDefault() throws IOException {
		Assert.assertTrue(generator.generate(content).toStream(new FileOutputStream("./build/test.jpg")));
		testLocalLogo();
		testRemoteLogo();
		testCustomConfig();
		testCustomCodeEyes();
	}

	/**
	 * 添加本地logo
	 * @throws IOException
	 */
	@Test
	public void testLocalLogo() throws IOException {
		boolean success = generator.setLogo(this.localLogoPath).generate(content).toStream(new FileOutputStream("./build/test.jpg"));
		Assert.assertTrue(success);
	}

	/**
	 * 添加在线logo
	 * @throws IOException
	 */
	@Test
	public void testRemoteLogo() throws IOException {
		generator.setRemoteLogo("http://www.demlution.com/site_media/media/photos/2014/11/06/3JmYoueyyxS4q4FcxcavgJ.jpg");
		Assert.assertTrue(generator.generate("https://www.apple.com/cn/").toStream(new FileOutputStream("./build/test.jpg")));
	}

	/**
	 * 自定义二维码配置
	 * @throws IOException
	 */
	@Test
	public void testCustomConfig() throws IOException {
		generator.getQrcodeConfig()
			.setBorderSize(2)
			.setPadding(10)
			.setMasterColor("#00BFFF")
			.setLogoBorderColor("#B0C4DE");
		Assert.assertTrue(generator.setLogo(this.localLogoPath).generate(content).toStream(new FileOutputStream("./build/test.jpg")));
	}
	
	/**
	 * 自定义二维码码眼颜色
	 * @throws IOException
	 */
	@Test
	public void testCustomCodeEyes() throws IOException {
		generator.getQrcodeConfig()
			.setMasterColor("#778899")
			.setLogoBorderColor("#778899")
			.setCodeEyesPointColor("#BC8F8F")
			.setCodeEyesFormat(QreyesFormat.DR2_BORDER_R_POINT);
		Assert.assertTrue(generator.setLogo(this.localLogoPath).generate(content).toStream(new FileOutputStream("./build/test.jpg")));
	}
	
	/**
	 * 写入输出流
	 * @throws IOException
	 */
	@Test
	public void testWriteToStream() throws IOException {
		OutputStream ous = null;
		try {
			ous = new FileOutputStream("./build/test.jpg");
			Assert.assertTrue(generator.generate(content).toStream(ous));
		} finally {
			IOUtils.closeQuietly(ous);
		}
	}
	
	@Test
	public void testGetImage() throws IOException {
		BufferedImage image = generator.generate(content).getImage();
		ImageIO.write(image, "png", new FileOutputStream("./build/test.jpg"));
	}

}
