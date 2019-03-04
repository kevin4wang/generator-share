package com.linlihudong.generator;

import com.linlihudong.generator.domain.ConnectInfo;
import com.linlihudong.generator.domain.DoMain;
import com.linlihudong.generator.domain.DoMainProperty;
import com.linlihudong.generator.domain.FileInfo;
import com.linlihudong.generator.util.JDBCUtil;
import com.linlihudong.generator.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class Generator {
  ///////////////////////////////////// 一般只需修改下面两个参数/////////////////////////////////////////////
  /**
   * 包名.
   */
  private static final String PACKAGE = "addressBook";

  /**
   * 表名，mysql小写 ，oracle大写，不能大小写共存
   */
  private static final String TABLENAME = "address_book";
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  // sql1获取表信息： 第一列：表名，第二列：表注释
  private static String sqlTable;
  // sql2获取字段信息： 第一列：字段名称，第二列：字段注释，第三列：字段类型，第四列：varchar长度，第五列：是否可空
  private static String sqlFields;
  // 本地工程目录,如果不设置，则根据默认的target目录，来生成
  private static String projectPath;
  // 允许的数据库类型及映射关系
  private static Map<String, String[]> dataTypeMap;

  static {
    // 有序
    dataTypeMap = new LinkedHashMap<>();
    // mysql
    dataTypeMap.put("varchar", new String[] {"String", "VARCHAR"});
    dataTypeMap.put("datetime", new String[] {"Date", "TIMESTAMP"});
    dataTypeMap.put("double", new String[] {"Double", "DOUBLE"});
    dataTypeMap.put("int", new String[] {"Integer", "INTEGER"});
    dataTypeMap.put("bigint", new String[] {"Long", "BIGINT"});
    dataTypeMap.put("tinyint", new String[] {"Short", "TINYINT"});
    dataTypeMap.put("text", new String[] {"String", "VARCHAR"});
    dataTypeMap.put("char", new String[] {"char", "CHAR"});

    // oracle
    dataTypeMap.put("VARCHAR2", new String[] {"String", "VARCHAR"});
    dataTypeMap.put("TIMESTAMP(3)3", new String[] {"Date", "TIMESTAMP"});
    // 先设置为Double 类型，满足后面条件的会覆盖
    dataTypeMap.put("NUMBER", new String[] {"Double", "DOUBLE"});
    // 长度不是0，比例为0
    dataTypeMap.put("NUMBER0", new String[] {"Integer", "INTEGER"});
    // 3、30~39 的长度，比例为0
    dataTypeMap.put("NUMBER03", new String[] {"Long", "BIGINT"});
    // dataTypeMap.put("DATE", new String[]{"Date", "TIMESTAMP"});
  }


  public static void main(String[] args) throws Exception {

    DoMain doMain = new DoMain();
    JDBCUtil jdbcUtil = new JDBCUtil(genConnectInfo(doMain));
    List<List<String>> executeSQL;
    // 第一次查询表信息
    executeSQL = jdbcUtil.executeSQL(sqlTable);

    for (List<String> list : executeSQL) {
      // 0 名称
      doMain.setClassName(StringUtil.getClassName(list.get(0)));
      doMain.setNickName(StringUtil.makeFirstCharLow(doMain.getClassName()));
      // 1 注释
      String tableNote = list.get(1);
      if (StringUtils.isEmpty(tableNote)) {
        throw new Exception("表注释不能为空");
      }
      doMain.setClassNote(tableNote);
      doMain.setTableName(TABLENAME);
    }
    // 第二次查询字段信息
    executeSQL = jdbcUtil.executeSQL(sqlFields);
    jdbcUtil.closeConnection();

    // 检查主键
    // StringUtil.checkPrimaryKey(executeSQL.get(0), doMain.getOracle());
    // 获取主键类型
    doMain.setIdJavaType(StringUtil.getPrimaryKeyType(executeSQL.get(0), doMain.getOracle()));

    List<DoMainProperty> doMainProperties = new ArrayList<>();
    for (List<String> list : executeSQL) {
      DoMainProperty doMainProperty = new DoMainProperty();
      // 0 名称
      String colName = list.get(0);
      doMainProperty.setColumnName(colName);
      doMainProperty.setPropertyName(StringUtil.initCap(colName));
      // 1 注释
      String columnNote = list.get(1);
      if (StringUtils.isEmpty(columnNote)) {
        throw new Exception(colName + "的字段注释不能为空");
      }
      doMainProperty.setColumnNote(columnNote);
      doMainProperty.setTerProperty(StringUtil.makeFirstCharUp(doMainProperty.getPropertyName()));
      // 2 类型
      String columnType = list.get(2);
      for (String key : dataTypeMap.keySet()) {
        if (columnType.startsWith(key)) {
          doMainProperty.setJavaType(dataTypeMap.get(key)[0]);
          doMainProperty.setJdbcType(dataTypeMap.get(key)[1]);
        }
      }
      if (StringUtils.isEmpty(doMainProperty.getJavaType())
          && StringUtils.isEmpty(doMainProperty.getJdbcType())) {
        throw new Exception(colName + "的数据库类型错误，如有疑问请咨询工具开发者，开发者邮箱为m18815285643@163.com");
      }
      // 3字符串长度
      String stringMaxLength = list.get(3);
      if (StringUtils.isNotEmpty(stringMaxLength)) {
        doMainProperty.setStringMaxLength(Integer.valueOf(stringMaxLength));
      }
      // 4是否可空
      doMainProperty.setNullAble(true);
      if (list.get(4).startsWith("N")) {
        doMainProperty.setNullAble(false);
      }
      // //3字符串长度
      // String numberMaxLength = list.get(5);
      // if (StringUtils.isNotEmpty(numberMaxLength)) {
      //// todo 暂时把Number类型的字段长度放在String类型上
      // doMainProperty.setStringMaxLength(Integer.valueOf(numberMaxLength));
      // }

      doMainProperties.add(doMainProperty);
    }
    doMain.setDoMainProperties(doMainProperties);

    if (StringUtils.isEmpty(projectPath)) {
      projectPath = new File(Generator.class.getResource("/").toURI()).getParentFile()
          .getParentFile().getPath();
    }
    genFiles(doMain, projectPath);

    System.exit(0);
  }

  /**
   * 生成连接信息
   *
   * @param doMain doMain
   * @return ConnectInfo
   * @throws Exception Exception
   */
  private static ConnectInfo genConnectInfo(DoMain doMain) throws Exception {
    String flag = StringUtil.getConfigFromDEV("generator.oracle.flag");
    doMain.setOracle(Boolean.valueOf(flag));
    // 各种包名
    StringBuilder daoPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.dao.package"));
    StringBuilder mapperPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.mapper.package"));
    StringBuilder domainPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.bean.package"));
    StringBuilder servicePackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.service.package"));
    StringBuilder controllerPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.controller.package"));
    StringBuilder inBeanPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.inBean.package"));
    StringBuilder outBeanPackage =
        new StringBuilder(StringUtil.getConfigFromDEV("generator.outBean.package"));
    if (StringUtils.isEmpty(PACKAGE)) {
      throw new Exception("包名不能为空");
    }
    ConnectInfo connectInfo = new ConnectInfo();
    if (!doMain.getOracle()) {
      String url = StringUtil.getConfigFromDEV("spring.datasource.url");
      // 数据库名称
      String dBName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
      // 查询准备
      connectInfo.setUrl(url);
      connectInfo.setClassPath(StringUtil.getConfigFromDEV("generator.connect-info.class-path"));
      connectInfo
          .setDriverClass(StringUtil.getConfigFromDEV("spring.datasource.driver-class-name"));
      connectInfo.setUserName(StringUtil.getConfigFromDEV("spring.datasource.username"));
      connectInfo.setPassword(StringUtil.getConfigFromDEV("spring.datasource.password"));

      sqlTable = "select table_name,table_comment from information_schema.TABLES "
          + "where table_schema = '" + dBName + "' and table_name='" + TABLENAME + "'";
      sqlFields =
          "select column_name,column_comment,column_type,character_maximum_length,is_nullable "
              + "from information_schema.COLUMNS where table_schema = '" + dBName
              + "' and table_name='" + TABLENAME + "'";
    } else {
      // 设置oracle
      mapperPackage = new StringBuilder("mapper");
      // 查询准备
      connectInfo.setClassPath(StringUtil.getConfigFromDEV("generator.connect-info.class-path"));
      connectInfo
          .setDriverClass(StringUtil.getConfigFromDEV("spring.datasource.driver-class-name"));
      connectInfo.setUrl(StringUtil.getConfigFromDEV("spring.datasource.url"));
      connectInfo.setUserName(StringUtil.getConfigFromDEV("spring.datasource.username"));
      connectInfo.setPassword(StringUtil.getConfigFromDEV("spring.datasource.password"));

      sqlTable =
          "SELECT TABLE_NAME,COMMENTS FROM USER_TAB_COMMENTS WHERE TABLE_NAME='" + TABLENAME + "'";
      sqlFields = "SELECT COLS.COLUMN_NAME,NOTS.COMMENTS,COLS.DATA_TYPE,"
          + "COLS.CHAR_COL_DECL_LENGTH,COLS.NULLABLE,COLS.DATA_PRECISION FROM (SELECT * FROM USER_TAB_COLUMNS "
          + "WHERE TABLE_NAME = '" + TABLENAME
          + "' ORDER BY COLUMN_ID)COLS  LEFT JOIN (SELECT * FROM "
          + "USER_COL_COMMENTS WHERE TABLE_NAME ='" + TABLENAME
          + "' )NOTS ON NOTS.COLUMN_NAME = COLS.COLUMN_NAME";
    }
    // 设置对应包名
    // daoPackage.append(".").append(PACKAGE);
    // mapperPackage.append(".").append(PACKAGE);
    // domainPackage.append(".").append(PACKAGE);
    // servicePackage.append(".").append(PACKAGE);
    // controllerPackage.append(".").append(PACKAGE);

    doMain.setPackageName(domainPackage.toString());
    doMain.setSimplePackageName(PACKAGE);
    doMain.setDaoPackageName(daoPackage.toString());
    doMain.setMapperPackageName(mapperPackage.toString());
    doMain.setServicePackageName(servicePackage.toString());
    doMain.setControllerPackageName(controllerPackage.toString());
    doMain.setInBeanPackageName(inBeanPackage.toString());
    doMain.setOutBeanPackageName(outBeanPackage.toString());
    return connectInfo;
  }

  /**
   * 生成对应的文件
   *
   * @param doMain vm参数信息
   * @param projectPath 工程目录
   * @throws Exception Exception
   */
  private static void genFiles(DoMain doMain, String projectPath) throws Exception {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("doMain", doMain);

    // 实体类
    FileInfo fileInfo = new FileInfo();
    fileInfo.setTargetProject(projectPath + "/src/main/java");
    fileInfo.setTargetPackage(doMain.getPackageName());

    StringUtil.genTemplateFile("DoMain.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + ".java", true);

    // 入参
    fileInfo.setTargetPackage(doMain.getInBeanPackageName());
    StringUtil.genTemplateFile("DoMainInDTO.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + "InDTO.java", true);
    // 出参
    fileInfo.setTargetPackage(doMain.getOutBeanPackageName());
    StringUtil.genTemplateFile("DoMainOutDTO.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + "OutDTO.java", true);
    // DAO接口
    fileInfo.setTargetProject(projectPath + "/src/main/java");
    fileInfo.setTargetPackage(doMain.getDaoPackageName());

    // StringUtil.genTemplateFile("IDao.vm", paramMap,fileInfo.toString() + "/I" +
    // doMain.getClassName() + "Dao.java");

    // BaseDAO impl
    // StringUtil.genTemplateFile("IDaoImpl.vm", paramMap,fileInfo.toString() + "/impl/" +
    // doMain.getClassName() + "DaoImpl.java", true);

    // mapper
    fileInfo.setTargetProject(projectPath + "/src/main/resources");
    fileInfo.setTargetPackage(doMain.getMapperPackageName());

    StringUtil.genTemplateFile("Mapper.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + "Mapper.xml", true);

    // Service
    fileInfo.setTargetProject(projectPath + "/src/main/java");
    fileInfo.setTargetPackage(doMain.getServicePackageName());

    StringUtil.genTemplateFile("IService.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + "Service.java");

    // Service impl
    StringUtil.genTemplateFile("IServiceImpl.vm", paramMap,
        fileInfo.toString() + "/impl/" + doMain.getClassName() + "ServiceImpl.java");

    // Controller
    fileInfo.setTargetProject(projectPath + "/src/main/java");
    fileInfo.setTargetPackage(doMain.getControllerPackageName());
    StringUtil.genTemplateFile("Controller.vm", paramMap,
        fileInfo.toString() + "/" + doMain.getClassName() + "Controller.java");


  }
}
