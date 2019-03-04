package com.linlihudong.bean;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author kevin wang
 * @version : 0.0.1
 * @date 2018/8/710:22
 * 部门表
 */
public class Dept {
    @ApiModelProperty(value="部门编号",name="deptId",required=true)
    private int deptId;
    @ApiModelProperty(value="部门名称",name="deptName",required=false)
    private String deptName;
    @ApiModelProperty(value="父部门编号",name="parentId",required=false)
    private int pId;//pid
    @ApiModelProperty(value="是否是高院",name="isOut",required=false)
    private String isOut;
    private List<Dept> childDept = Lists.newArrayList();
    public List<Dept> getChildDept() {
        return childDept;
    }

    public void setChildDept(List<Dept> childDept) {
        this.childDept = childDept;
    }

    public String getIsOut() {
        return isOut;
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut;
    }

    public void addChild(Dept dept) {
        childDept.add(dept);
    }

    public void sortChildren() {
        if (childDept.size() != 0) {
            Collections.sort(childDept, new Comparator<Dept>() {
                @Override
                public int compare(Dept o1, Dept o2) {
                    return o1.getDeptId()-o2.getDeptId();
                }
            });

            for (int i = 0; i < childDept.size(); i++) {
                ((Dept) childDept.get(i)).sortChildren();
            }
        }
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }



    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }
}
