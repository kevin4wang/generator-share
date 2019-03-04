package  com.linlihudong.generator.util;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

public class StringUtil {

  private static VelocityEngine engine;
  private static Properties globalProperties = new Properties();

  static {
    engine = new VelocityEngine();
    Properties properties = new Properties();
    properties.setProperty(Velocity.RESOURCE_LOADER, "class");
    properties.setProperty("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

    properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
    properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
    // 不生成日志文件
    properties.setProperty(Velocity.RUNTIME_LOG, "");
    try {
      engine.init(properties);
    } catch (Exception e) {
      System.out.println("文件解析错误");
    }
  }

  // private StringUtil() {}

  /**
   * 通过模版生成文件，不覆盖之前的文件
   *
   * @param vmFileName 模版文件名字
   * @param map 参数
   * @param outFileName 输出文件名字
   * @throws Exception 异常
   */
  public static void genTemplateFile(String vmFileName, Map<String, Object> map, String outFileName)
      throws Exception {
    genTemplateFile(vmFileName, map, outFileName, false);
  }

  /**
   * 通过模版生成文件
   *
   * @param vmFileName 模版文件名字
   * @param map 参数
   * @param outFileName 输出文件名字
   * @param override 是否覆盖
   * @throws Exception 异常
   */
  public static void genTemplateFile(String vmFileName, Map<String, Object> map, String outFileName,
      boolean override) throws Exception {
    File file = new File(outFileName);
    // 如果文件父路径不存在
    if (!file.getParentFile().exists()) {
      if (!file.getParentFile().mkdirs()) {
        throw new Exception("创建文件父路径失败，请检查");
      }
    }
    if (file.createNewFile()) {
      System.out.println("创建文件成功" + file);
    } else {
      if (!override) {
        System.out.println(file + "文件已存在，不更改任何内容");
        return;
      }
      System.out.println("覆盖原文件成功" + file);
    }

    Template template = engine.getTemplate("template/" + vmFileName, "utf-8");
    VelocityContext ctx = new VelocityContext();
    for (String key : map.keySet()) {
      ctx.put(key, map.get(key));
    }
    try (FileWriter fileWriter = new FileWriter(file)) {
      template.merge(ctx, fileWriter);
    }
  }

  /**
   * 将带有 _ 的字符串 驼峰规范化
   *
   * @param string 字符串
   * @return 规范化后的字符串
   */
  public static String initCap(String string) {
    String[] split = string.toLowerCase().split("_");
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < split.length; i++) {
      if (i == 0) {
        builder.append(split[i]);
      } else {
        builder.append(makeFirstCharUp(split[i]));
      }
    }
    return builder.toString();
  }

  /**
   * 获取实体类的className
   *
   * @param string 表名
   * @return className
   * @throws Exception 表命名不规范
   */
  public static String getClassName(String string) throws Exception {
    String[] split = string.toLowerCase().split("_");
    StringBuilder builder = new StringBuilder();
    for (String aSplit : split) {
      if ("hj".equalsIgnoreCase(aSplit)) {
        continue;
      }
      builder.append(makeFirstCharUp(aSplit));
    }
    return builder.toString();

  }

  public static String makeFirstCharUp(String string) {
    char[] ch = string.toCharArray();
    if (ch[0] >= 'a' && ch[0] <= 'z') {
      ch[0] = (char) (ch[0] - 32);
    }
    return new String(ch);
  }

  public static String makeFirstCharLow(String string) {
    char[] ch = string.toCharArray();
    if (ch[0] >= 'A' && ch[0] <= 'Z') {
      ch[0] = (char) (ch[0] + 32);
    }
    return new String(ch);
  }

  /**
   * 根据表名判断数据库
   *
   * @param tableName 表名
   */
  public static boolean isMysql(String tableName) throws Exception {
    if (StringUtils.isEmpty(tableName)) {
      throw new Exception("表名不能为空");
    }
    String[] split = tableName.split("_");
    /*
     * if (split.length < 2 || !split[0].equalsIgnoreCase("hj")) { //不是hj开头的 throw new
     * Exception("表名不符合规范"); }
     */
    char[] chars = tableName.replaceAll("_", "").toCharArray();
    boolean flag = false;
    if (Character.isUpperCase(chars[0])) {
      // 判断后面的字符是否和首字母大写一致
      for (char aChar : chars) {
        if (Character.isLowerCase(aChar)) {
          throw new Exception("表名不符合规范");
        }
      }
    } else if (Character.isLowerCase(chars[0])) {
      // 判断后面的字符是否和首字母小写一致
      for (char aChar : chars) {
        if (Character.isUpperCase(aChar)) {
          throw new Exception("表名不符合规范");
        }
      }
      flag = true;
    } else {
      throw new Exception("表名不符合规范");
    }
    return flag;
  }

  /**
   * 检查主键
   *
   * @param firstColumn 查询出的第一列
   * @param oracle 数据库类型
   */
  public static void checkPrimaryKey(List<String> firstColumn, boolean oracle) throws Exception {
    if (!oracle) {
      if (!firstColumn.get(0).equals("id")) {
        throw new Exception("MYSQL表的第一列列名必须是：id");
      }
      if (!(firstColumn.get(2).startsWith("bigint") || firstColumn.get(2).startsWith("varchar"))) {
        throw new Exception("MYSQL表的第一列id类型必须是：bigint或者varchar");
      }
    } else {
      if (!firstColumn.get(0).equals("ID")) {
        throw new Exception("ORACLE表的第一列列名必须是：ID");
      }
      if (!(firstColumn.get(2).startsWith("NUMBER") || firstColumn.get(2).startsWith("VARCHAR2"))) {
        throw new Exception("MYSQL表的第一列id类型必须是：NUMBER或者VARCHAR2");
      }
    }
  }

  /**
   * 获取主键类型
   *
   * @param firstColumn 查询出的第一列
   * @param oracle 数据库类型
   */
  public static String getPrimaryKeyType(List<String> firstColumn, boolean oracle) {
    if (!oracle) {
      if (firstColumn.get(2).startsWith("bigint")) {
        return "Long";
      } else {
        return "String";
      }
    } else {
      if (firstColumn.get(2).startsWith("VARCHAR2")) {
        return "String";
      } else {
        return "Long";
      }
    }
  }


  /**
   * 获取配置信息
   *
   * @param key key
   * @return 配置信息
   * @throws Exception Exception
   */
  public static String getConfigFromDEV(String key) throws Exception {
    if (null == globalProperties || globalProperties.size() == 0) {
      Properties properties = new Properties();
      // properties.load(StringUtil.class.getResourceAsStream("/application-dev.properties"));
      properties.load(StringUtil.class.getResourceAsStream("/generator.properties"));
      globalProperties = properties;
      properties = null;
      return globalProperties.getProperty(key);
    } else {
      return globalProperties.getProperty(key);
    }
  }

  public static String getActiveConfig(String key) throws Exception {
    Properties properties = new Properties();
    properties.load(StringUtil.class.getResourceAsStream("/application.properties"));
    return properties.getProperty(key);
  }

  public static String getConfigFromProd(String key) throws Exception {
    Properties properties = new Properties();
    properties.load(StringUtil.class.getResourceAsStream("/application-prod.properties"));
    return properties.getProperty(key);
  }

}
