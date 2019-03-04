package com.linlihudong.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  /**
   * Example: subString("abcd","a","c")="b"
   *
   * @param start null while start from index=0
   * @param to null while to index=src.length
   */
  public static String subString(String src, String start, String to) {
    int indexFrom = start == null ? 0 : src.indexOf(start);
    int indexTo = to == null ? src.length() : src.indexOf(to);
    if (indexFrom < 0 || indexTo < 0 || indexFrom > indexTo) {
      return null;
    }

    if (null != start) {
      indexFrom += start.length();
    }

    return src.substring(indexFrom, indexTo);

  }


  /**
   * Example: substr_count("abcda","a")=2
   * <p>
   * 统计字符串中 指定的字符出现的次数     *
   *
   * @param haystack The string to search in
   * @param needle The substring to search for
   */
  public static int substr_count(String haystack, String needle) {
    int count = 0;
    Pattern p = Pattern.compile(needle);
    Matcher m = p.matcher(haystack);
    while (m.find()) {
      count++;
    }

    return count;
  }


  /**
   * Example: list_search("a",list)=0
   * <p>
   * 查询数组中是否存在 指定的元素 。 并返回第一次出现的响应的key     *
   *
   * @param list The List.
   * @param needle The searched value.
   */
  public static int list_search(String needle, List<String> list) {
    int key = 0;

    String[] strArray = list.toArray(new String[]{});

    for (int i = 0; i < strArray.length; i++) {

      if (needle.equals(strArray[i])) {
        key = i;
      }
    }

    return key;
  }

  /**
   * Example: list_search_int("a",list)=0
   * <p>
   * 查询数组中是否存在 指定的元素 。 并返回第一次出现的响应的key     *
   *
   * @param list The List.
   * @param needle The searched value.
   */
  public static int list_search_int(Integer needle, List<Integer> list) {
    int key = 0;

    Integer[] strArray = list.toArray(new Integer[]{});

    for (int i = 0; i < strArray.length; i++) {

      if (needle.equals(strArray[i])) {
        key = i;
      }
    }

    return key;
  }


  public static void main(String[] args) {
//        System.out.println(substr_count("","/"));
//        List<String> list = new ArrayList();
//
//        list.add("1");
//        list.add("6");
//        list.add("31");
//        list.add("53");
//        list.add("101");
//        list.add("125");
//        list.add("139");
//        list.add("208");
//        list.add("228");
//        list.add("232");
//        list.add("244");
//        System.out.println(list_search("31", list));

    List<Integer> list = new ArrayList();

    list.add(1);
    list.add(6);
    list.add(31);
    list.add(53);
    list.add(101);
    list.add(125);
    list.add(139);
    list.add(208);
    list.add(228);
    list.add(232);
    list.add(244);
    System.out.println(list_search_int(31, list));
  }


}
