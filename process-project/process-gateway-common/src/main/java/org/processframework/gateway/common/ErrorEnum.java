package org.processframework.gateway.common;

/**
 * @author apple
 * @desc 网关错误定义
 * @since 1.0.0.RELEASE
 */
public enum ErrorEnum {
    /******************100000 成功******************/
    //成功
    SUCCESS(Codes.CODE_SUCCESS,""),

    /****************20000 服务不可用****************/
    //服务暂不可用（业务系统不可用） 稍后重试
    ISP_UNKNOWN_ERROR(Codes.CODE_UNKNOWN,"isp.unknown-error"),
    //服务暂不可用（网关自身的未知错误） 稍后重试
    AOP_UNKNOWN_ERROR(Codes.CODE_UNKNOWN,"aop.unknown-error"),
    //响应式超时
    ISP_GATEWAY_RESPONSE_TIMEOUT(Codes.CODE_UNKNOWN, "isp.gateway-response-timeout"),

    /*************20001授权权限不足************/
    //无效的访问令牌 请刷新授权令牌或重新授权获取新的令牌
    AOP_INVALID_AUTH_TOKEN(Codes.CODE_AUTH,"aop.invalid-auth-token"),
    //访问令牌已过期 请刷新授权令牌或重新授权获取新的令牌
    AOP_AUTH_TOKEN_TIME_OUT(Codes.CODE_AUTH,"aop.auth-token-time-out"),
    //无效的应用授权令牌 请刷新应用授权令牌或重新授权获取新的令牌
    AOP_INVALID_APP_AUTH_TOKEN(Codes.CODE_AUTH,"aop.invalid-app-auth-token"),
    //商户未授权当前接口 请重新授权获取新的应用授权令牌
    AOP_INVALID_APP_AUTH_TOKEN_NO_API(Codes.CODE_AUTH,"aop.invalid-app-auth-token-no-api"),
    //应用授权令牌已过期 请刷新应用授权令牌或重新授权获取新的令牌
    AOP_APP_AUTH_TOKEN_TIME_OUT(Codes.CODE_AUTH,"aop.app-auth-token-time-out"),
    //商户未签约任何产品 ISV代理调用的场景，请上线商户的服务窗
    AOP_NO_PRODUCT_REG_BY_PARTNER(Codes.CODE_AUTH,"aop.no-product-reg-by-partner"),

    /*****************40001 缺少必选参数***************/
    //缺少方法名参数 请求参数里面必须要有method参数
    ISV_MISSING_METHOD(Codes.CODE_MISSING,"isv.missing-method"),
    //缺少签名参数 检查请求参数，缺少sign参数
    ISV_MISSING_SIGNATURE(Codes.CODE_MISSING,"isv.missing-signature"),
    //缺少签名类型参数 检查请求参数，缺少sign_type参数
    ISV_MISSING_SIGNATURE_TYPE(Codes.CODE_MISSING,"isv.missing-signature-type"),
    //缺少签名配置 未上传公钥配置
    ISV_MISSING_SIGNATURE_KEY(Codes.CODE_MISSING,"isv.missing-signature-key"),
    //缺少appId参数 检查请求参数，缺少app_id参数
    ISV_MISSING_APP_ID(Codes.CODE_MISSING,"isv.missing-app-id"),
    //缺少时间戳参数 检查请求参数，缺少timestamp参数
    ISV_MISSING_TIMESTAMP(Codes.CODE_MISSING,"isv.missing-timestamp"),
    //缺少版本参数 检查请求参数，缺少version参数
    ISV_MISSING_VERSION(Codes.CODE_MISSING,"isv.missing-version"),
    //解密出错, 未指定加密算法 检查参数，缺少encrypt_type参数
    ISV_DECRYPTION_ERROR_MISSING_ENCRYPT_TYPE(Codes.CODE_MISSING,"isv.decryption-error-missing-encrypt-type"),

    /***************40002 非法的参数****************/
    //参数无效 检查参数，格式不对、非法值、越界等
    ISV_INVALID_PARAMETER(Codes.CODE_INVALID, "isv.invalid-parameter"),
    //文件上传失败 文件写入失败，重试
    ISV_UPLOAD_FAIL(Codes.CODE_INVALID,"isv.upload-fail"),
    //文件扩展名无效 检查传入的文件扩展名称，目前支持格式：csv,txt,zip,rar,gz,doc,docx,xls,xlsx,pdf,bmp,gif,jpg,jpeg,png
    ISV_INVALID_FIL_EXTENSION(Codes.CODE_INVALID,"isv.invalid-file-extension"),
    //文件大小无效 检查文件大小，目前支持最大为：50MB
    ISV_INVALID_FILE_SIZE(Codes.CODE_INVALID,"isv.invalid-file-size"),
    //不存在的方法名 检查入参method是否正确
    ISV_INVALID_METHOD(Codes.CODE_INVALID,"isv.invalid-method"),
    //无效的数据格式 检查入参format，目前只支持json和xml 2种格式
    ISV_INVALID_FORMAT(Codes.CODE_INVALID,"isv.invalid-format"),
    //无效的签名类型 检查入参sign_type,目前只支持RSA,RSA2,HMAC_SHA1
    ISV_INVALID_SIGNATURE_TYPE(Codes.CODE_INVALID,"isv.invalid-signature-type"),
    //无效签名
    //1.公私钥是否是一对 2.检查公钥上传是否与私钥匹配 3.存在中文需要做urlencode 4.签名算法是否无误
    ISV_INVALID_SIGNATURE(Codes.CODE_INVALID,"isv.invalid-signature"),
    //无效令牌 auth_token 为无效的令牌，请确认令牌有效
    ISV_INVALID_TOKEN(Codes.CODE_INVALID,"isv.invalid-token"),
    //无效的加密类型 检查入参encrypt_type，目前只支持AES
    ISV_INVALID_ENCRYPT_TYPE(Codes.CODE_INVALID,"isv.invalid-encrypt-type"),
    //解密异常 重试
    ISV_INVALID_ENCRYPT(Codes.CODE_INVALID,"isv.invalid-encrypt"),
    //无效的appId参数 检查入参app_id，app_id不存在，或者未上线
    ISV_INVALID_APP_ID(Codes.CODE_INVALID,"isv.invalid-app-id"),
    //非法的时间戳参数 时间戳参数timestamp非法，请检查格式需要为"yyyy-MM-dd HH:mm:ss"
    ISV_INVALID_TIMESTAMP(Codes.CODE_INVALID,"isv.invalid-timestamp"),
    //字符集错误 请求参数charset错误，目前支持格式：GBK,UTF-8
    ISV_INVALID_CHARSET(Codes.CODE_INVALID,"isv.invalid-charset"),
    //摘要错误 检查请求参数，文件摘要参数必填
    ISV_INVALID_DIGEST(Codes.CODE_INVALID,"isv.invalid-digest"),
    //解密出错，不支持的加密算法 检查入参encrypt_type，目前只支持AES
    ISV_DECRYPTION_ERROR_NOT_VALID_ENCRYPT_TYPE(Codes.CODE_INVALID,"isv.decryption-error-not-valid-encrypt-type"),
    //解密出错, 未配置加密密钥或加密密钥格式错误 没有配置加密密钥
    ISV_DECRYPTION_ERROR_NOT_VALID_ENCRYPT_KEY(Codes.CODE_INVALID,"isv.decryption-error-not-valid-encrypt-key"),
    //解密出错，未知异常 重试
    ISV_DECRYPTION_ERROR_UNKNOWN(Codes.CODE_INVALID,"isv.decryption-error-unknown"),
    //验签出错, 未配置对应签名算法的公钥或者证书 没有配置应用公钥
    ISV_MISSING_SIGNATURE_CONFIG(Codes.CODE_INVALID,"isv.missing-signature-config"),
    //本接口不支持第三方代理调用 本接口不支持第三方代理调用
    ISV_NOT_SUPPORT_APP_AUTH(Codes.CODE_INVALID,"isv.not-support-app-auth"),
    //可疑的攻击请求 参考 https://opendocs.alipay.com/open/59/103663 特殊说明第3点，商户的请求参数中，所有的 key（支付宝关键 key 或者商户自己的 key），其对应的 value 中都不应该出现支付宝关键key，如 body、subject、service、out_trade_no、seller_id、total_fee 等，否则该类请求将可能被支付宝拦截，请更换相应参数名称。
    ISV_SUSPECTED_ATTACK(Codes.CODE_INVALID,"isv.suspected-attack"),
    //接口被禁用	接口已被禁用，请查看接口说明
    ISV_FORBIDDEN_API(Codes.CODE_INVALID,"isv.forbidden-api"),
    /************************40004业务处理失败*************************/

    /***********************40006权限不足********************/
    //ISV权限不足 请检查配置的账户是否有当前接口权限。请在 开发者中心 > 我的应用 找到对应的应用，在其 功能列表 中添加缺少的功能包、签约或申请必须的信息。详见 添加功能。
    ISV_INSUFFICIENT_ISV_PERMISSIONS(Codes.CODE_ISV_PERM,"isv.insufficient-isv-permissions"),
    //用户权限不足 代理的商户没有当前接口权限，ISV 可以选择让商户的应用自主添加功能后签约；或选择 代商户签约。
    ISV_INSUFFICIENT_USER_PERMISSIONS(Codes.CODE_ISV_PERM,"isv.insufficient-user-permissions"),
    /** 禁止IP访问 */
    ISV_IP_FORBIDDEN(Codes.CODE_ISV_PERM, "isv.ip-forbidden"),

    /****************************40005Call Limited（调用频次超限）**************************/
    //应用调用次数超限，包含调用频率超限 降低请求并发量
    ISV_APP_CALL_LIMITED(Codes.CODE_CALL_LIMITED,"isv.app-call-limited"),
    //API调用次数超限，包含调用频率超限 降低请求并发量
    ISV_METHOD_CALL_LIMITED(Codes.CODE_CALL_LIMITED,"isv.method-call-limited")
    ;
    private final ErrorMeta errorMeta;

    /**
     * 构造方法构造错误对象
     * @param code 返回码
     * @param sub_code 明细返回码
     */
    ErrorEnum(String code,String sub_code){
        this.errorMeta = new ErrorMeta("open.error_",code,sub_code);
    }

    /**
     * 获取错误对象
     * @return 错误对象
     */
    public ErrorMeta getErrorMeta(){
        return errorMeta;
    }

    private static class Codes{
        //接口调用成功
        public static final String CODE_SUCCESS = "10000";
        //服务不可用
        public static final String CODE_UNKNOWN = "20000";
        //授权权限不足
        public static final String CODE_AUTH = "20001";
        //缺少必选参数
        public static final String CODE_MISSING = "40001";
        //非法的参数
        public static final String CODE_INVALID = "40002";
        //业务处理失败
        public static final String CODE_BIZ = "40004";
        //调用频次
        public static final String CODE_CALL_LIMITED = "40005";
        //ISV权限不足
        public static final String CODE_ISV_PERM = "40006";

    }
}
