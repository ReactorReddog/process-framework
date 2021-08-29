package org.processframework.tools.test;

import com.cn.processframework.tools.qrcode.Codectx;
import com.cn.processframework.tools.qrcode.IOUtils;
import com.cn.processframework.tools.qrcode.context.ContextQrcodeExcutorClient;
import com.cn.processframework.tools.qrcode.context.QreyesFormat;
import com.cn.processframework.tools.qrcode.context.SimpleQrcodeExcutorClient;
import org.gradle.internal.impldep.org.junit.Assert;
import org.gradle.internal.impldep.org.junit.Before;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class TestQrGen {

	private static final String content = "https://www.aliyun.com/product/yunxiao/";

	private ContextQrcodeExcutorClient generator = new SimpleQrcodeExcutorClient();

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
		generator.setRemoteLogo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be");
		Assert.assertTrue(generator.generate("https://www.apple.com/cn/").toStream(new FileOutputStream("./build/test.jpg")));
	}

	/**
	 * 自定义二维码配置
	 * @throws IOException
	 */
	@Test
	public void testCustomConfig() throws IOException {
		generator.getQrcodeConfig()
			.setMasterColor("#00BFFF")
			.setLogoBorderColor("#B0C4DE");
		Assert.assertTrue(generator.setRemoteLogo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be").generate(content).toStream(new FileOutputStream("./build/test.jpg")));
	}
	
	/**
	 * 自定义二维码码眼颜色
	 * @throws IOException
	 */
	@Test
	public void testCustomCodeEyes() throws IOException {
		generator.getQrcodeConfig()
			.setMasterColor("#778899")
				.setBorderSize(1)
				.setBorderStyle(Codectx.BorderStyle.DASHED)
				.setCodeEyesFormat(QreyesFormat.R2_BORDER_R_POINT)
				.setBorderRadius(10);
		Assert.assertTrue(generator.setRemoteLogo("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be").generate(content).toStream(new FileOutputStream("./build/test.jpg")));
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
