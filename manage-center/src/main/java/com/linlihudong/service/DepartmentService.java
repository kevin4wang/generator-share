package com.linlihudong.service;

import com.linlihudong.dao.DaoSupport;
import com.linlihudong.entity.Page;
import com.linlihudong.entity.PageData;
import com.linlihudong.bean.Dept;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

  @Resource(name = "daoSupport")
  private DaoSupport dao;

  /*
   * 新增
   */
  public Integer save(Dept dept) throws Exception {
    Integer result = (Integer) dao.save("DepartmentMapper.insert", dept);
    return result;
  }

  /*
   * 修改
   */
  public Integer edit(Dept dept) throws Exception {
    dao.update("DepartmentMapper.edit", dept);
    return dept.getDeptId();
  }

  public List<Dept> list() throws Exception {
    CopyOnWriteArrayList<Dept> copyList = new CopyOnWriteArrayList();
    List<Dept> result = (List<Dept>) dao.findForList("DepartmentMapper.getList", 0);
    Dept dept = result.get(0);
    List<Dept> list = getDepartmentList(dept, copyList);
    return list;
  }

  //	递归处理部门
  public List<Dept> getDepartmentList(Dept dept, List<Dept> list) throws Exception {
    List<Dept> result = (List<Dept>) dao.findForList("DepartmentMapper.getList", dept.getDeptId());
    if (dept.getDeptId() == 1 || result.size() > 0) {
      dept.setChildDept(result);
      if (dept.getDeptId() == 1) {
        list.add(dept);
      }
    }
    if (null != result && result.size() > 0) {
      for (int i = 0; i < result.size(); i++) {
        Dept department = result.get(i);
        getDepartmentList(department, list);
      }
    }
    return list;
  }

  /**
   * 删除部门
   */
  public void delete(int deptId) throws Exception {
//        找到下属所有子节点全部删除
    List<Dept> list = null;
    list = (List<Dept>) dao.findForList("DepartmentMapper.getDepartmentList", deptId);
    if (list != null && list.size() > 0) {
      for (Dept dept : list) {
        dao.delete("DepartmentMapper.delete", dept.getDeptId());
      }
    }
  }

  /*
   * 通过id获取数据
   */
  public Dept findById(int id) throws Exception {
    return (Dept) dao.findForObject("DepartmentMapper.findById", id);
  }

  public Integer saveOrEdit(Dept dept) throws Exception {
    Integer deptId = dept.getDeptId();
    int result = 0;
    if (0 != deptId) {
      Dept newDept = findById(deptId);
      result = edit(newDept);
    } else {
      result = save(dept);
    }
    return result;

  }

  public List<PageData> detailList(Page page) throws Exception {
    return (List<PageData>) dao.findForList("DepartmentMapper.departmentListPage", page);
  }

}
