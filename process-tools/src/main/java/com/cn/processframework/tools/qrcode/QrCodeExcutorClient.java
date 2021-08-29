package com.cn.processframework.tools.qrcode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author apple
 * @package com.cn.processframework.tools
 * @desc <p>二维码生成接口类</p>
 * @since 1.0.0.RELEASE
 */
public interface QrCodeExcutorClient {
    /**
     * 生成带内容的二维码
     * @param content 内容
     * @return this
     */
    QrCodeExcutorClient generate(String content);

    /**
     * 获取image
     * @return BufferedImage
     */
    BufferedImage getImage();

    /**
     * 导出二维码到文件
     * @param pathName 文件地址
     * @return boolean
     * @throws IOException
     */
    boolean toFile(String pathName) throws IOException;

    /**
     * 导出二维码数据流
     * @param stream Stream文件流地址
     * @return boolean
     * @throws IOException
     */
    boolean toStream(OutputStream stream) throws IOException;

    /**
     * 清空当前二维码
     */
    void clear();

}
