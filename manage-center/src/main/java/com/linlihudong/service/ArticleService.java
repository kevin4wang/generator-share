package com.linlihudong.service;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.linlihudong.dao.DaoSupport;
import com.linlihudong.entity.Page;
import com.linlihudong.entity.PageData;

@Service("articleService")
public class ArticleService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/*
	* 新增
	*/
	public void save(Map map)throws Exception{
		dao.save("ArticleMapper.insertArticle", map);
	}
	/*
	* 修改
	*/
	public void edit(Map map)throws Exception{
		dao.update("ArticleMapper.editArticle", map);
	}
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ArticleMapper.articlelistPage", page);
	}
	/*
	* 通过id获取数据
	*/
	public PageData findById(int id)throws Exception{
		return (PageData)dao.findForObject("ArticleMapper.findById", id);
	}
	/*
	* 删除
	*/
	public void delete(int id)throws Exception{
		dao.delete("ArticleMapper.deleteArticle", id);
	}
	public List<PageData> findByfunction(PageData pd)throws Exception{
		PageData pagedata=new PageData();
		List<PageData> list=(List<PageData>)dao.findForList("ArticleMapper.findByfunction", pd);
		/*ResultSet rs=(ResultSet) dao.findForObject("ArticleMapper.findByfunction", pd);
		ResultSetMetaData data = rs.getMetaData();
		while (rs.next()) {   
            for (int i = 1; i <= data.getColumnCount(); i++) {// 数据库里从 1 开始  
  
                String c = data.getColumnName(i);  
                String v = rs.getString(c);  
                System.out.println(c + ":" + v + "\t");  
                pagedata.put(c, v);  
            }  
            System.out.println("======================");  
        }  */
		return list;
	}
}
