package com.linlihudong.config.annotation;

import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang.StringUtils;

/**
 * Created by kevin on 2018/12/5.
 */
public class AnnotationUtil {

  public static Object hardlerDesensitization(Object object) throws Exception {
    List<Object> t = null;
    boolean flag = false;
//        todo 兼容map link etc
    if (object instanceof List) {
      t = (List<Object>) object;
      flag = true;
    }
    if (flag) {
      for (Object o1 : t) {
        hardlerData(o1);
      }
      return t;
    } else {
      hardlerData(object);
      return object;
    }
  }


  private static void hardlerData(Object o) throws Exception {
    Class cls = o.getClass();
    Field[] fields = cls.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      if (field.isAnnotationPresent(DesensitizationField.class)) {
        field.setAccessible(true);
        String value = (String) field.get(o);
        Integer length = value.length();
        DesensitizationField fieldAnno = field.getAnnotation(DesensitizationField.class);
        String annoName = fieldAnno.name();
        if (StringUtils.isNotBlank(annoName)) {
          StringBuffer stringBuffer = new StringBuffer(value);
          if (annoName.equalsIgnoreCase(DesensitizationProp.CHINESENAME)) {
            stringBuffer.replace(1, length, DesensitizationProp.REPLACESHORTSTR);
            String newValue = stringBuffer.toString();
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.IDCARD) ||
              annoName.equalsIgnoreCase(DesensitizationProp.TELEPHONE) ||
              annoName.equalsIgnoreCase(DesensitizationProp.PHONE)
              ) {
            assert length > 4;
            String newValue = stringBuffer.replace(4, length - 4, DesensitizationProp.REPLACESTR)
                .toString();
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.CHINESEADDRESS)) {
//                         TODO: 2018/12/5
            assert length > 6;
            int start = stringBuffer.indexOf("区");
            String newValue = "";
            if (start <= 6) {
              newValue = stringBuffer.replace(6, length - 4, DesensitizationProp.REPLACESTR)
                  .toString();
            } else if (start > 6) {
              newValue = stringBuffer.replace(start, length - 4, DesensitizationProp.REPLACESTR)
                  .toString();
            }
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.EMAIL)) {
//                            电子邮件(@前小等于5位的，隐藏前2位；大于5位的，保留前3位，其余用* 代替，如:13954789652@139.com->139*******@139.com)
            assert length > 5;
            String[] sp = value.split("@");
            String before = sp[0];
            String after = sp[1];
            StringBuffer sb = new StringBuffer(before);
            String newValue = "";
            int beforeLength = before.length();
            if (beforeLength <= 5) {
              newValue = sb.replace(0, 2, DesensitizationProp.REPLACESTR).toString() + after;
            } else {
              newValue =
                  sb.replace(3, beforeLength, DesensitizationProp.REPLACESTR).toString() + after;
            }
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.POSTCODE)) {
            String newValue = stringBuffer.replace(2, length, DesensitizationProp.REPLACESTR)
                .toString();
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.COMPANYNAME)) {
            int start = stringBuffer.indexOf("有限公司");
            int startAgain = stringBuffer.indexOf("公司");
            String newValue = "";
            if (start > 0) {
              newValue = stringBuffer.replace(2, start, DesensitizationProp.REPLACESTR).toString();
            } else if (startAgain > 0) {
              newValue = stringBuffer.replace(2, startAgain, DesensitizationProp.REPLACESTR)
                  .toString();
            }
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.CARDNUMBER) ||
              annoName.equalsIgnoreCase(DesensitizationProp.MEDICALINSURANCECARD)
              ) {
            assert length < 10;
            String newValue = stringBuffer.replace(6, length - 4, DesensitizationProp.REPLACESTR)
                .toString();
            field.set(o, newValue);
          } else if (annoName.equalsIgnoreCase(DesensitizationProp.SOCIALCARD) ||
              annoName.equalsIgnoreCase(DesensitizationProp.BUSINESSNO)
              ) {
            assert length < 8;
            String newValue = stringBuffer.replace(4, length - 4, DesensitizationProp.REPLACESTR)
                .toString();
            field.set(o, newValue);
          }

        }
      }
    }
  }
}
