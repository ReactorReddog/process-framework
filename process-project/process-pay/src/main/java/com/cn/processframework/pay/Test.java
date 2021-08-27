package com.cn.processframework.pay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author apple
 * @package com.cn.processframework.pay
 * @desc <p></p>
 * @since
 */
public class Test {
    public static void main(String[] args) throws IOException {
        BufferedImage bufferedImage = MatrixToImageWriter.writeInfoToJpgBuff("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201412%2F04%2F20141204150752_vWUrc.jpeg&refer=http%3A%2F%2Fimg5.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632666458&t=7891a631e45c713b10246995379572be");
        ImageIO.write(bufferedImage,"JPG",new FileOutputStream("./build/test.jpg"));
    }
}
