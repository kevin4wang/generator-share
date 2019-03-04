package com.linlihudong.config.annotation;

/**
 * Created by kevin on 2018/12/5.
 */
public class Prop {

    @DesensitizationField(name=DesensitizationProp.CHINESENAME)
     private String chineseName;
    //    身份证号码(显示前4位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.IDCARD)
     private String idCard;
    //    电话号码(显示前4位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.TELEPHONE)
     private String telePhone;
    //    手机号码(显示前4位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.PHONE)
     private String phone;
    //    中文地址(保留5位，中间用6个*代替)
    @DesensitizationField(name=DesensitizationProp.CHINESEADDRESS)
     private String chineseAddress;
    //    电子邮件(@前小等于5位的，隐藏前2位；大于5位的，保留前3位，其余用* 代替，如:13954789652@139.com->139*******@139.com)
    @DesensitizationField(name=DesensitizationProp.EMAIL)
     private String email;
    //    邮政编码(前两位字母+*号+@后缀)
    @DesensitizationField(name=DesensitizationProp.POSTCODE)
     private String postcode;
    //    企业名称(*号+”公司”(后两个或四个字))
    @DesensitizationField(name=DesensitizationProp.COMPANYNAME)
     private String companyName;
    //    银行卡号(显示前6位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.CARDNUMBER)
     private String cardNumber;
    //    医保卡号(显示前6位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.MEDICALINSURANCECARD)
     private String medicalInsuranceCard;
    //    社会保障卡号(显示前4位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.SOCIALCARD)
     private String socialCard;
    //    工商注册号(显示前4位+*号+后4位)
    @DesensitizationField(name=DesensitizationProp.BUSINESSNO)
     private String businessNo;

    public static void main(String[] args) throws Exception {
        Prop prop = new Prop();
        prop.setChineseName("王全明");
        prop.setIdCard("520421199107180010");
        prop.setTelePhone("18910494250");
        prop.setPhone("18910494250");
        prop.setChineseAddress("广东省广州市越秀区中山二路211号");
        prop.setEmail("m18815285643@163.com");
        prop.setPostcode("510000");
        prop.setCompanyName("北明软件有限公司");
        prop.setCardNumber("6214830123846486");
        prop.setMedicalInsuranceCard("150403007130005858");
        prop.setSocialCard("150403007130005858");
        prop.setBusinessNo("888458545754822");
        AnnotationUtil.hardlerDesensitization(prop);
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getChineseAddress() {
        return chineseAddress;
    }

    public void setChineseAddress(String chineseAddress) {
        this.chineseAddress = chineseAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMedicalInsuranceCard() {
        return medicalInsuranceCard;
    }

    public void setMedicalInsuranceCard(String medicalInsuranceCard) {
        this.medicalInsuranceCard = medicalInsuranceCard;
    }

    public String getSocialCard() {
        return socialCard;
    }

    public void setSocialCard(String socialCard) {
        this.socialCard = socialCard;
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
}
