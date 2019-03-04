package com.linlihudong.generator.util;


import com.linlihudong.generator.domain.ConnectInfo;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class JDBCUtil {

  private Connection connection;

  public JDBCUtil(ConnectInfo connectInfo) throws Exception {
    connection = getConnection(connectInfo);
  }

  private Connection getConnection(ConnectInfo connectInfo) throws Exception {
    ClassLoader loader =
        new URLClassLoader(new URL[] {new File(connectInfo.getClassPath()).toURI().toURL()});
    Class<?> loadClass = loader.loadClass(connectInfo.getDriverClass());
    Properties info = new Properties();
    info.put("user", connectInfo.getUserName());
    info.put("password", connectInfo.getPassword());
    Driver driver = (Driver) loadClass.newInstance();
    Method connectMethod = null;
    // 本类中没有找到方法，去父类中找
    while (loadClass != null) {
      try {
        connectMethod = loadClass.getDeclaredMethod("connect", String.class, Properties.class);
        connectMethod.setAccessible(true);
        break;
      } catch (NoSuchMethodException e) {
        loadClass = loadClass.getSuperclass();
      }
    }
    assert connectMethod != null;
    return (Connection) connectMethod.invoke(driver, connectInfo.getUrl(), info);
  }

  public List<List<String>> executeSQL(String sql) throws Exception {
    PreparedStatement prepareStatement = connection.prepareStatement(sql);
    ResultSet resultSet = prepareStatement.executeQuery();
    List<List<String>> listList = new ArrayList<>();
    while (resultSet.next()) {
      List<String> list = new ArrayList<>();
      // 特殊： 从1 开始
      for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
        list.add(resultSet.getString(i));
      }
      listList.add(list);
    }
    if (listList.size() < 1) {
      throw new Exception("查询结果为空，请检查表是否存在");
    }
    return listList;
  }

  public void closeConnection() throws SQLException {
    connection.close();
  }

}
