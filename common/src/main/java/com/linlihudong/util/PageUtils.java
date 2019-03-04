package com.linlihudong.util;

import com.linlihudong.entity.PageData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class PageUtils implements Serializable {

  private static final long serialVersionUID = 1L;
  //总记录数
  private int totalCount;
  //每页记录数
  private int pageSize;
  //总页数
  private int totalPage;
  //当前页数
  private int currPage;
  //列表数据
  private List<?> list;

  /**
   * 分页
   *
   * @param list 列表数据
   * @param totalCount 总记录数
   * @param pageSize 每页记录数
   * @param currPage 当前页数
   */
  public PageUtils(List<PageData> list, int totalCount, int pageSize, int currPage) {
    list = formatHumpNameForList(list);
    this.list = list;
    this.totalCount = totalCount;
    this.pageSize = pageSize;
    this.currPage = currPage;
    this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getCurrPage() {
    return currPage;
  }

  public void setCurrPage(int currPage) {
    this.currPage = currPage;
  }

  public List<?> getList() {
    return list;
  }

  public void setList(List<?> list) {
    this.list = list;
  }

  public boolean needPageNext() {

    return (totalCount - (currPage - 1) * pageSize) > pageSize;
  }

  public boolean needPagePrev() {
    return (currPage - 1) * pageSize > 0;
  }


  public static void doPageTurnNxt(int pageNo, PageTurn helper) {
    if (pageNo < 0) {
      return;
    }
    PageUtils page = helper.getData(pageNo);
    helper.handle(page);
    if (page.needPageNext()) {
      page = null;
      doPageTurnNxt(pageNo + 1, helper);
    }
  }


  public static void doPageTurnPre(int pageNo, PageTurn helper) {
    if (pageNo < 0) {
      return;
    }
    PageUtils page = helper.getData(pageNo);
    helper.handle(page);
    if (page.needPagePrev()) {
      page = null;
      doPageTurnPre(pageNo - 1, helper);
    }
  }


  public static void main(String[] args) {
    PageUtils.doPageTurnNxt(1, new PageTurn() {


      @Override
      public PageUtils getData(int pageNo) {
        return new PageUtils(new ArrayList(10), 999, 100, pageNo);
      }

      @Override
      public void handle(PageUtils data) {
        System.out
            .println(data.getCurrPage() + " " + ((data.getCurrPage() - 1) * data.getPageSize()));

      }
    });
  }

  /**
   * 将Map中的key由下划线转换为驼峰
   */
  public static PageData formatHumpName(PageData map) {
    PageData pd = new PageData();
    Iterator it = map.entrySet().iterator();
    while (it.hasNext()) {
      Entry<String, Object> entry = (Entry<String, Object>) it.next();
      String key = entry.getKey();
      String newKey = toFormatCol(key);
      pd.put(newKey, entry.getValue());
    }
    return pd;
  }

  public static String toFormatCol(String colName) {
    StringBuilder sb = new StringBuilder();
    String[] str = colName.toLowerCase().split("_");
    int i = 0;
    for (String s : str) {
      if (s.length() == 1) {
        s = s.toUpperCase();
      }
      i++;
      if (i == 1) {
        sb.append(s);
        continue;
      }
      if (s.length() > 0) {
        sb.append(s.substring(0, 1).toUpperCase());
        sb.append(s.substring(1));
      }
    }
    return sb.toString();
  }

  /**
   * 将List中map的key值命名方式格式化为驼峰
   */
  public static List<PageData> formatHumpNameForList(List<PageData> list) {
    List<PageData> newList = new ArrayList<PageData>();
    for (PageData o : list) {
      newList.add(formatHumpName(o));
    }
    return newList;
  }
}
