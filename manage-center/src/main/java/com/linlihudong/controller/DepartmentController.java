package com.linlihudong.controller;

import com.linlihudong.controller.base.BaseController;
import com.linlihudong.entity.Page;
import com.linlihudong.entity.PageData;
import com.linlihudong.util.DataResult;
import com.linlihudong.util.PageUtils;
import com.linlihudong.util.ParamsUtil;
import com.google.common.collect.Lists;
import com.linlihudong.bean.Dept;
import com.linlihudong.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author quanming wang
 * @version : 0.0.1
 * @date 2018/8/710:28
 */
@RequestMapping("dept")
@RestController
@Api(value = "/dept", tags = "部门接口")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ApiOperation(value = "部门列表", notes = "展示部门信息")
    public DataResult list() {
        logBefore(logger, "部门列表");
        List<Dept> deptList = Lists.newArrayList();
        try {
            deptList = departmentService.list();
            return DataResult.ok().put("deptTree", deptList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            e.printStackTrace();
            return DataResult.error("查询异常");
        }
    }


    @RequestMapping(value = "/detailList",method = RequestMethod.GET)
    @ApiOperation(value = "部门详情列表")
    public DataResult detailList(Map<String, Object> params) {
        logBefore(logger, "部门详情列表");
        List<PageData> deptList = Lists.newArrayList();
        Page page=new Page();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            Map<String, Object> data = ParamsUtil.jsonStrToMap(params);
            Integer deptId = (Integer) data.get("deptId");
            if(deptId!=0){
                pd.put("deptId", deptId);
            }
            Object currentpage=data.get("PageIndex");
            int PagIndex=   currentpage==null?1:Integer.parseInt(currentpage.toString());
            Object showcount=data.get("PageSize");
            int PageSize=showcount==null?1:Integer.parseInt(showcount.toString());
            page.setCurrentPage(PagIndex);
            page.setShowCount(PageSize);
            page.setPagedata(pd);
            deptList = departmentService.detailList(page);
            PageUtils pageUtil = new PageUtils(deptList, page.getTotalResult(), page.getShowCount(), page.getCurrentPage());
            return DataResult.ok().put("page", pageUtil);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            e.printStackTrace();
            return DataResult.error("查询异常");
        }
    }

    /**
     * 保存或者修改
     *
     * @param dept 部门实体
     * @return
     */
    @RequestMapping(value = "/saveOrEdit",method = RequestMethod.POST)
    @ApiOperation(value = "添加或修改部门")
    public DataResult saveOrEdit(@RequestParam Dept dept) {
        logBefore(logger, "添加/修改部门");
        try {
            Integer result = departmentService.saveOrEdit(dept);
            return DataResult.ok().put("saveOrEdit", result);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            e.printStackTrace();
            return DataResult.error("添加/修改异常");
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @ApiOperation(value = "删除部门")
    public DataResult delete(@RequestParam Integer deptId) {
        logBefore(logger, "删除部门");
        try {
            departmentService.delete(deptId);
            return DataResult.ok().put("delete", 200);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            e.printStackTrace();
            return DataResult.error("删除部门异常");
        }
    }

}
