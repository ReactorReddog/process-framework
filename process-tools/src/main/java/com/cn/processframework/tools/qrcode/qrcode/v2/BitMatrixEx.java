package com.cn.processframework.tools.qrcode.qrcode.v2;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;

import java.io.Serializable;

/**
 * @author apple 
 * @desc 扩展二维码矩阵信息 新增三个位置探测图形的判定
 * @since 1.0.0.RELEASE
 */
@Data
public class BitMatrixEx implements Serializable {
    /**
     * 实际生成二维码的宽度
     */
    private Integer width;
    /**
     * 实际生成二维码的高
     */
    private Integer height;
    /**
     * 左白边大小
     */
    private Integer leftPadding;
    /**
     * 上百边大小
     */
    private Integer topPadding;
    /**
     *
     * 矩阵信息缩放比例
     */
    private Integer multiple;
    /**
     * 原始二维码信息
     */
    private ByteMatrix byteMatrix;
}
