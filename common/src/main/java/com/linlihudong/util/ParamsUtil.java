package com.linlihudong.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class ParamsUtil {

  /**
   * @param code 状态码
   * @param PageData 返回对象集合
   * @param DataCount 总记录数
   * @param PageSize 页面大小
   * @param PageIndex 当前页码
   */
  public static Map<String, Object> ok(int code, List<?> PageData, int DataCount,
      int PageSize, int PageIndex) {
    Map<String, Object> data = new HashMap<String, Object>();
    Map<String, Object> returnmap = new HashMap<String, Object>();
    data.put("PageData", PageData);
    data.put("DataCount", DataCount);
    data.put("PageSize", PageSize);
    data.put("PageIndex", PageIndex);
    data.put("PageCount",
        DataCount % PageSize == 0 ? (DataCount / PageSize)
            : (DataCount / PageSize) + 1);
    returnmap.put("Status", code);
    returnmap.put("Data", data);

    return returnmap;

  }

  /**
   * 返回参数
   *
   * @param code 状态码   0失败 1成功
   * @param data 返回对象
   */
  public static Map<String, Object> ok(int code, Object data) {
    Map<String, Object> retuanMap = new HashMap<String, Object>();
    retuanMap.put("Status", code);
    retuanMap.put("Data", data);
    return retuanMap;

  }

  /**
   * 返回参数
   *
   * @param code 状态码   0失败 1成功 -4数据格式错误
   * @param data 返回对象
   */
  public static Map<String, Object> error(int code, Object data) {
    Map<String, Object> retuanMap = new HashMap<String, Object>();
    retuanMap.put("Status", code);
    retuanMap.put("Data", data);
    return retuanMap;

  }


  public static PageUtil getPage(PageUtils pageUtil, Map params) {
    PageUtil page = new PageUtil(pageUtil.getList(), pageUtil.getTotalCount(),
        Integer.parseInt(params.get("PageSize").toString()), pageUtil.getCurrPage());
    return page;
  }

  /**
   * (抽取)传入json生成对象
   */
  public static <T> T jsonToEntity(Map<String, Object> params, Class<T> clazz)
      throws Exception {
    String jsonsubString = StringUtils.subString(params.toString(),
        "data&quot;:",
        "},&quot;token");// 截取{&quot;type&quot;:&quot;&quot;,&quot;name&quot;:&quot;asdfa&quot;,&quot;value&quot;:&quot;&quot;,&quot;remark&quot;:&quot;&quot;,&quot;yydz&quot;:&quot;adsfs&quot;}
//		jsonsubString=new String(jsonsubString.getBytes("iso8859-1"),"UTF-8");
    if (jsonsubString == null) {// 未转义参数
      jsonsubString = StringUtils.subString(params.toString(), "data\":",
          "},\"token");
    }
    if (jsonsubString == null) {// json可转换对象
      jsonsubString = StringUtils.subString(params.toString(),
          "data\":\"", "}}");
    }
    System.out.println(jsonsubString);
    String json = (jsonsubString + "}").replaceAll("&quot;", "\"");
    System.out.println("请求参数：" + json);
    T obj = JSON.parseObject(json, clazz);
//		Gson gson = new GsonBuilder().create();// 创建Gson转换对象
//		T obj = (T) gson.fromJson(json, clazz);// json转对象
    return obj;
  }


  /**
   * 传入json生成Map
   */
  public static Map<String, Object> jsonToMap(Map<String, Object> params)
      throws UnsupportedEncodingException {
    String jsonsubString = StringUtils
        .subString(params.toString(), "data&quot;:{", "},&quot;token");

    if (jsonsubString == null || jsonsubString.equals("")) {// json可转换对象 {}

      jsonsubString = StringUtils.subString(params.toString(), "data={", "}}");
    }
    if (jsonsubString == null || jsonsubString.equals("")) {// 未转义参数
      // data":"{"":""},"token":""
      jsonsubString = StringUtils.subString(params.toString(), "data\":",
          "},\"token");
    }
    jsonsubString = jsonsubString.replaceAll("&quot;", "");
    if (jsonsubString == null || jsonsubString.equals("")) {
      jsonsubString = params.toString();
    }
    System.out.println("请求参数：" + "{" + jsonsubString + "}");
    Map<String, Object> dataMap = new HashMap<>();
    String[] str = new String[2];
    String[] split = jsonsubString.split(",");
    for (String s : split) {
      str = s.split(":");
      if (str[0] == null) {
        str[0] = "";
      }
      if (str.length == 1) {
        dataMap.put(str[0], "");
      } else {
        dataMap.put(str[0], str[1]);
      }
    }
    return dataMap;
  }

  /**
   * 传入json生成Map(不分页)
   */
  public static Map<String, Object> json2Map(Map<String, Object> params)
      throws UnsupportedEncodingException {
    String jsonsubString = StringUtils
        .subString(params.toString(), "data&quot;:{", "},&quot;token");

    if (jsonsubString == null || jsonsubString.equals("")) {// json可转换对象 {}

      jsonsubString = StringUtils.subString(params.toString(), "data={", "}}");
    }
    if (jsonsubString == null || jsonsubString.equals("")) {// 未转义参数
      // data":"{"":""},"token":""
      jsonsubString = StringUtils.subString(params.toString(), "data\":",
          "},\"token");
    }
    jsonsubString = jsonsubString.replaceAll("&quot;", "\"");
    if (jsonsubString == null || jsonsubString.equals("")) {
      jsonsubString = params.toString();
    }
    System.out.println("请求参数：" + "{" + jsonsubString + "}");
    Map<String, Object> parse = (Map<String, Object>) JSONObject.parse("{" + jsonsubString + "}");
    return parse;
  }

  /**
   * json转数组
   */
  public static String[] jsonToStringArr(Map<String, Object> params) {
    // JSONObject jb = new JSONObject(params);
    String jsonsubString = StringUtils.subString(params.toString(),
        "data&quot;:{", "},&quot;token");
    if (jsonsubString.length() == 0 || jsonsubString.equals("")) {// 未转义参数
      // data":"{"":""
      // },"token":""
      jsonsubString = StringUtils.subString(params.toString(), "data\":",
          "},\"token");
    }
    if (jsonsubString.length() == 0 || jsonsubString.equals("")) {// json可转换对象
      // {}
      jsonsubString = params.toString();
    }
    jsonsubString = jsonsubString.replaceAll("&quot;", "");
    System.out.println("请求参数：" + jsonsubString);
    return jsonsubString.split(":")[1].split(",");
  }


  public static String[] strSplit(String[] strArray, String str) {
    if (!str.isEmpty()) {
      strArray = str.split(",");
    }
    return strArray;
  }

  /**
   * 接入参数转实体类
   *
   * @param clazz 目标类
   */
  public static <T> T jsonStrToEntity(Map<String, Object> params,
      Class<T> clazz) throws Exception {
    String str = params.get("data").toString();
    if (str.contains("&quot;") && str.length() > 0) {
      str = str.replaceAll("&quot;", "\"");
    }
    System.out.println("请求参数：" + str);
    T obj = JSON.parseObject(str, clazz);
		/*//反射去空串
		Class objClass =(Class) obj.getClass();
		Field[] fs = objClass.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true);
			Object object = field.get(obj);
			String type=field.getType().toString();
			if (type.endsWith("String")) {
				field.set(object, object.toString().trim());//去除属性值前后空串
			}
		}*/
    return obj;
  }


  /**
   * 接入参数转map
   */
  public static Map<String, Object> jsonStrToMap(Map<String, Object> params)
      throws UnsupportedEncodingException {

    if (params.get("data") == null) {
      return new HashMap<String, Object>();
    }
    String jsonsubString = params.get("data").toString();
		/*jsonsubString=new String(jsonsubString.getBytes("iso8859-1"),"UTF-8");
		System.out.println(jsonsubString);*/
    if (jsonsubString.contains("&quot;") && jsonsubString.length() > 0) {
      jsonsubString = jsonsubString.replaceAll("&quot;", "\"");
    }
    System.out.println("请求参数：" + jsonsubString);
    Map<String, Object> dataMap = JSON.parseObject(jsonsubString);
//		for (String key : dataMap.keySet()) {
//			if (dataMap.get(key) instanceof String) {
//				dataMap.put(key, dataMap.get(key).toString().trim());
//			}
//		}
    return dataMap;
  }


  public static Map<String, Object> mapToPageMap(Map<String, Object> params)
      throws UnsupportedEncodingException {

    if (params.get("data") == null) {
      return new HashMap<String, Object>();
    }
    //转换字符
    String jsonsubString = params.get("data").toString();
		/*jsonsubString=new String(jsonsubString.getBytes("iso8859-1"),"UTF-8");
		System.out.println(jsonsubString);*/
    if (jsonsubString.contains("&quot;") && jsonsubString.length() > 0) {
      jsonsubString = jsonsubString.replaceAll("&quot;", "\"");
    }
    System.out.println("请求参数：" + jsonsubString);
    Map<String, Object> dataMap = JSON.parseObject(jsonsubString);
    //封装分页
    dataMap.put("page", dataMap.get("PageIndex"));
    dataMap.put("limit", dataMap.get("PageSize"));
    dataMap.put("sidx", "");
//		data.put("order", "asc");
    dataMap.put("offset", (Integer.parseInt(dataMap.get("page").toString()) - 1) * Integer
        .parseInt(dataMap.get("limit").toString()));
    return dataMap;
  }

  public static <T> T mapToJavaBean(Map<String, Object> params, Class<T> clazz) throws Exception {
    T parseObject = JSON.parseObject(JSON.toJSONString(params).toString(), clazz);
    //转换字符
    return parseObject;
  }

  public static <T> List<T> mapToJavaBean(List<Map<String, Object>> params,
      Class<T> clazz) {
    List<T> objectList = new ArrayList<T>();
    for (Map<String, Object> map : params) {
      T parseObject = JSON.parseObject(JSON.toJSONString(map).toString(),
          clazz);
      objectList.add(parseObject);
    }
    // 转换字符
    return (List<T>) objectList;
  }


}
