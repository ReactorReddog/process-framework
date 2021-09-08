package org.processframework.tools.test;

import com.cn.processframework.tools.qrcode.qrcode.v2.ImageLoadUtil;
import com.cn.processframework.tools.qrcode.qrcode.v2.QrCodeGenWrapper;
import com.cn.processframework.tools.qrcode.qrcode.v2.QrCodeOptions;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author apple
 * @package org.processframework.tools.test
 * @desc <p></p>
 * @since
 */
public class a1 {

    String path = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be";
    @Test
    public void aa() throws IOException, WriterException {
        boolean b = QrCodeGenWrapper.of(path)
                .setW(1340)
                .setH(1340)
                .setFtStartX(100)
                .setFtStartY(130)
                .setLogo(path)
                .setDrawEnableScale(true)
                .setQrText("黄开梅是傻逼")
                .setDetectSpecial().setDrawStyle(QrCodeOptions.DrawStyle.TXT)
//                .setFtImg("ft/ft_1.png")
//                .setDetectImg("eye3.png")
//                .setDrawStyle(QrCodeOptions.DrawStyle.OCTAGON)
                .setFtFillColor(Color.WHITE)
                .toStream(new FileOutputStream("./build/test.jpg"));
    }

    @Test
    public void bb() throws IOException, WriterException {
        boolean b = QrCodeGenWrapper
                .of(path)
                .setDetectImg("eye3.png")
                .setW(1140)
                .setH(1140)
                .setFtImg("ft/ft_1.png")
                .setFtFillColor(Color.WHITE)
                .setFtStartX(100)

                .setFtStartY(130)
                .toStream(new FileOutputStream("./build/test01.jpg"));
    }
}
