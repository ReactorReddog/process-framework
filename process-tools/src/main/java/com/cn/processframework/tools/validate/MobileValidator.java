package com.cn.processframework.tools.validate;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author apple
 * @desc 电话号码工具集中管理
 * @since
 */
public class MobileValidator {

    private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

    private static Pattern phoneReg = Pattern.compile("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$");

    private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
    /**
     * 根据国家代码和手机号判断手机号是否有效
     * @param mobile 电话号码
     * @param countryCode 城市编码
     * @return boolean
     */
    public static boolean checkPhoneNumber(int countryCode ,Long mobile){
        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
        phoneNumber.setCountryCode(countryCode).setNationalNumber(mobile);
        return phoneNumberUtil.isValidNumber(phoneNumber);
    }

    /**
     * 获取号主信息
     * @param mobile 电话号码
     * @return
     */
    public static JSONObject getMobileInfo(String mobile){
        // 正则校验
        Matcher matcher = phoneReg.matcher(mobile);
        if (!matcher.find()) {
            throw new RuntimeException("The phone number maybe like:" + "+[National Number][Phone number], but got " + mobile);
        }

        Phonenumber.PhoneNumber referencePhonenumber;
        try {
            String language = "CN";
            referencePhonenumber = phoneNumberUtil.parse(mobile, language);
        } catch (NumberParseException e) {
            throw new RuntimeException(e.getMessage());
        }
        String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(referencePhonenumber);

        if (regionCodeForNumber == null) {
            throw new RuntimeException("Missing region code by phone number " + mobile);
        }

        boolean checkSuccess = MobileValidator.checkPhoneNumber(referencePhonenumber.getCountryCode(),referencePhonenumber.getNationalNumber());
        if (!checkSuccess) {
            throw new RuntimeException("Not an active number:" + mobile);
        }

        String description = geocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);

        int countryCode = referencePhonenumber.getCountryCode();
        long nationalNumber = referencePhonenumber.getNationalNumber();
        //获取运营商
        String _carrier = carrierMapper.getNameForNumber(referencePhonenumber, Locale.CHINA);


        JSONObject resultObject = new JSONObject();
        // 区域编码 Locale : HK, US, CN ...
        resultObject.put("regionCode", regionCodeForNumber);
        // 国号: 86, 1, 852 ... @link: https://blog.csdn.net/wzygis/article/details/45073327
        resultObject.put("countryCode", countryCode);
        // 去掉+号 和 国号/区号 后的实际号码
        resultObject.put("nationalNumber", nationalNumber);
        // 所在地区描述信息
        resultObject.put("description", description);
        // 去掉+号后的号码 (用于阿里云发送短信)
        resultObject.put("number", String.valueOf(countryCode) + nationalNumber);
        resultObject.put("fullNumber", mobile);
        resultObject.put("carrier",_carrier);

        return resultObject;

    }

    /**
     * 获取所有国家的国际区号
     * @return
     */
    public static Set<Map<Object,Object>> getSupportRegions(){
        return phoneNumberUtil
                .getSupportedRegions()
                .stream()
                .map(st-> MapUtil.builder()
                        .put("region",st)
                        .put("regionName",new Locale(st).getDisplayLanguage())
                        .put("regionCode",phoneNumberUtil.getCountryCodeForRegion(st))
                        .build())
                .collect(Collectors.toSet());
    }
}
