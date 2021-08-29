package com.cn.processframework.tools.qrcode;

/**
 * @author apple
 * @package com.cn.processframework.tools
 * @desc <p>二维码异常处理</p>
 * @since 1.0.0.RELEASE
 */
public class QrCodeException extends RuntimeException {
    private static final long serialVersionUID = -3123966199040432229L;

    public QrCodeException() {
        super();
    }

    public QrCodeException(Throwable cause) {
        super(cause);
    }

    public QrCodeException(final String message) {
        super(message);
    }

    public QrCodeException(final String message, Throwable cause) {
        super(message, cause);
    }
}
