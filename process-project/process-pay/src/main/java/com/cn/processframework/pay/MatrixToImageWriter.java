package com.cn.processframework.pay;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author apple
 * @desc 二维码生成工具
 * @since 1.0 17:25
 */
@Slf4j
public class MatrixToImageWriter {
    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static final int QRCODE_SIZE = 300;//设置二维码尺寸

    private static final int LOGO_SIZE = 60;//设置二维码logo尺寸

    private MatrixToImageWriter() {
    }

    /**
     * 根据二维矩阵的碎片 生成对应的二维码图像缓冲
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @return 二维码图像缓冲
     * @see com.google.zxing.common.BitMatrix
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    /**
     * 二维码生成文件
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @param format 格式
     * @param file   保持的文件地址
     * @throws IOException 文件保存异常
     */
    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    /**
     * 二维码生成流
     *
     * @param matrix 二维矩阵的碎片 包含 宽高 行，字节
     * @param format 格式
     * @param stream 保持的文件输出流
     * @throws IOException 文件保存异常
     */
    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }


    /**
     * 二维码信息写成JPG文件
     *
     * @param content 二维码信息
     * @param fileUrl 文件地址
     */
    public static void writeInfoToJpgFile(String content, String fileUrl) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
            File file1 = new File(fileUrl);
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 二维码信息写成JPG BufferedImage
     *
     * @param content 二维码信息
     * @return JPG BufferedImage
     */
    public static BufferedImage writeInfoToJpgBuff(String content) {
        BufferedImage re = null;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
            re = MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return re;
    }

    /**
     * 二维码信息写成JPG BufferedImage
     *
     * @param content 二维码信息
     * @return JPG BufferedImage
     */
    public static BufferedImage writeInfoToJpgBuff(String content,String headProfileUrl) {
        BufferedImage re = null;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
            re = MatrixToImageWriter.toBufferedImage(bitMatrix);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //判断当前地址是否为空 为空则直接返回二维码
        if (StringUtils.isBlank(headProfileUrl)){
            return re;
        }
        //插入logo
        writeInfoToLogo(re,headProfileUrl);

        return re;
    }

    /**
     * 插入logo
     * @param bufferedImage 当前二维码图片
     * @param headProfileUrl 头像地址
     */
    public static void writeInfoToLogo(BufferedImage bufferedImage,String headProfileUrl){
        BufferedImage logoImage = null;
        try {
            logoImage = ImageIO.read(new URL(headProfileUrl));
        } catch (IOException e) {
            log.error("当前图片地址读取异常!");
        }
        //插入logo
        Graphics2D graphics = bufferedImage.createGraphics();
        int x = (QRCODE_SIZE-LOGO_SIZE)/2;
        int y = (QRCODE_SIZE-LOGO_SIZE)/2;
        graphics.drawImage(logoImage,x,y,LOGO_SIZE,LOGO_SIZE,null);
        Shape shape = new RoundRectangle2D.Float(x, y, LOGO_SIZE, LOGO_SIZE, 6, 6);
        graphics.setStroke(new BasicStroke(3f));
        graphics.draw(shape);
        graphics.dispose();
    }
}
