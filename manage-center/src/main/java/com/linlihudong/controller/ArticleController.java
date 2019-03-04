package com.linlihudong.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource; 
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linlihudong.controller.base.BaseController;
import com.linlihudong.entity.Page;
import com.linlihudong.entity.PageData;
import com.linlihudong.service.ArticleService;
import com.linlihudong.util.DataResult;
import com.linlihudong.util.PageUtils;
import com.linlihudong.util.ParamsUtil;

@RequestMapping("article")
@RestController
public class ArticleController extends BaseController {
	/*
	 * swaggers参考:
	 * https://www.cnblogs.com/softidea/p/6251249.html
	 * https://gumutianqi1.gitbooks.io/specification-doc/content/tools-doc/spring-boot-swagger2-guide.html
	 */
	@Resource(name="articleService")
	private ArticleService articleService;
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public DataResult list(@RequestParam Map<String, Object> params){
		logBefore(logger, "文章列表");
		List<PageData>	articleList=new ArrayList<PageData>();
		Page page=new Page();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			Map<String, Object> data = ParamsUtil.jsonStrToMap(params);
			String articlename = (String) data.get("articlename");
			if(null != articlename && !"".equals(articlename)){
				articlename = articlename.trim();
				pd.put("articlename", articlename);
			}
			String arttype = (String) data.get("arttype");
			if(null != arttype && !"".equals(arttype)&& !arttype.equalsIgnoreCase("all")){
				pd.put("arttype", arttype);
			}
			Object currentpage=data.get("PageIndex"); 
			int PagIndex=   currentpage==null?1:Integer.parseInt(currentpage.toString());
			Object showcount=data.get("PageSize"); 
			int PageSize=showcount==null?1:Integer.parseInt(showcount.toString());
			page.setCurrentPage(PagIndex);
			page.setShowCount(PageSize);
			page.setPagedata(pd);
			articleList = articleService.list(page);	//列出文章列表
			//System.out.println("总行数:"+page.getTotalResult()+",当前页："+page.getCurrentPage()+",每页页数："+page.getShowCount());
			PageUtils pageUtil = new PageUtils(articleList, page.getTotalResult(), page.getShowCount(), page.getCurrentPage());
			return DataResult.ok().put("page", pageUtil);
		} catch(Exception e){
			logger.error(e.toString(), e);
			e.printStackTrace();
			return DataResult.error("分页查询异常");
		}
		
	}

	@GetMapping("/getartile/{id}")
	public DataResult getartile(@PathVariable String id){
		logBefore(logger, "去文章页面");
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			pd.put("id",id);
			pd =articleService.findById(Integer.valueOf(id));
			return DataResult.ok(pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
			return DataResult.error("取该记录异常");
		} 
		
	}

	/**
	 * 保存文章信息
	 * @return
	 */
	@RequestMapping(value="/update")
	public DataResult update(@RequestParam Map<String, Object> params){
		logBefore(logger, "保存文章信息");
		try{
			Map<String, Object> data = ParamsUtil.jsonToMap(params);
			
				String id = (String) data.get("id");
				if (id==null ||id.equals("")) {
					data.put("logopath", "");
					articleService.save(data);
				} else {
					articleService.edit(data);
				}
				return DataResult.ok();
		} catch(Exception e){
			logger.error(e.toString(), e);
			return DataResult.error("插入或更新文章异常");
		}
	}
	/**
	 * 删除文章
	 */
	@RequestMapping(value="/delete")
	public DataResult delete(@RequestParam Integer id){
		logBefore(logger, "删除文章");
		try{
			articleService.delete(id);
			return DataResult.ok();
		} catch(Exception e){
			logger.error(e.toString(), e);
			return DataResult.error("删除文章异常");
		}
	}
	@RequestMapping(value="/count")
	public DataResult count(@RequestParam Map<String, Object> params){
		logBefore(logger, "测试文章");
		try{
			Map<String, Object> data = ParamsUtil.jsonToMap(params);
			PageData pd = new PageData();
			String aa=(String) data.get("\"articlename\"");
			aa=aa==null?"三":aa;
			pd.put("articlename",aa);
			List<PageData> list=articleService.findByfunction(pd);
			return DataResult.ok();
		} catch(Exception e){
			logger.error(e.toString(), e);
			return DataResult.error("测试文章异常");
		}
	}
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
