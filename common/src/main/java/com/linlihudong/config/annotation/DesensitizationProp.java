package com.linlihudong.config.annotation;

/**
 * Created by kevin on 2018/12/5.
 */
public class DesensitizationProp {
    //    中文姓名(姓+**（或某某）) 完颜，令狐
    public static final String CHINESENAME = "chineseName";
    //    身份证号码(显示前4位+*号+后4位)
    public static final String IDCARD = "idCard";
    //    电话号码(显示前4位+*号+后4位)
    public static final String TELEPHONE = "telePhone";
    //    手机号码(显示前4位+*号+后4位)
    public static final String PHONE = "phone";
    //    中文地址(保留5位，中间用6个*代替)
    public static final String CHINESEADDRESS = "chineseAddress";
    //    电子邮件(@前小等于5位的，隐藏前2位；大于5位的，保留前3位，其余用* 代替，如:13954789652@139.com->139*******@139.com)
    public static final String EMAIL = "email";
    //    邮政编码(前两位字母+*号+@后缀)
    public static final String POSTCODE = "postcode";
    //    企业名称(*号+”公司”(后两个或四个字))
    public static final String COMPANYNAME = "companyName";
    //    银行卡号(显示前6位+*号+后4位)
    public static final String CARDNUMBER = "cardNumber";
    //    医保卡号(显示前6位+*号+后4位)
    public static final String MEDICALINSURANCECARD = "medicalInsuranceCard";
    //    社会保障卡号(显示前4位+*号+后4位)
    public static final String SOCIALCARD = "socialCard";
    //    工商注册号(显示前4位+*号+后4位)
    public static final String BUSINESSNO = "businessNo";
    //    替换字符
    public static final String REPLACESTR = "******";
    public static final String REPLACESHORTSTR = "**";

}
