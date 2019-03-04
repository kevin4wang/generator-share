package com.linlihudong.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonAutoDetect()
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	@JsonProperty("DataCount")
	private int DataCount;
	//每页记录数
	@JsonProperty("PageSize")
	private int PageSize;
	//总页数
	@JsonProperty("PageCount")
	private int PageCount;
	//当前页数
	@JsonProperty("PageIndex")
	private int PageIndex;
	//列表数据
	@JsonProperty("PageData")
	private List<?> PageData;
	
	/**
	 * 分页
	 * @param PageData        列表数据
	 * @param DataCount  总记录数
	 * @param pageSize    每页记录数
	 * @param PageIndex    当前页数
	 */
	public PageUtil(List<?> list, int DataCount, int pageSize, int PageIndex) {
		this.PageData = list;
		this.DataCount = DataCount;
		this.PageSize = pageSize;
		this.PageIndex = PageIndex;
		this.PageCount = (int)Math.ceil((double)DataCount/pageSize);
	}

	public PageUtil(List<?> list, int DataCount, Map<String , Object> data) {
		this.PageData = list;
		this.DataCount = DataCount;
		this.PageSize = Integer.parseInt(data.get("limit").toString());
		this.PageIndex = Integer.parseInt(data.get("page").toString());
		this.PageCount = (int)Math.ceil((double)DataCount/Integer.parseInt(data.get("limit").toString()));
	}
	@JSONField(name="DataCount")
	public int getDataCount() {
		return DataCount;
	}


	
	public void setDataCount(int DataCount) {
		DataCount = DataCount;
	}


	@JSONField(name="PageSize")
	public int getPageSize() {
		return PageSize;
	}


	@JsonIgnore
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}


	@JSONField(name="PageCount")
	public int getPageCount() {
		return PageCount;
	}


	@JsonIgnore
	public void setPageCount(int PageCount) {
		PageCount = PageCount;
	}


	@JSONField(name="PageIndex")
	public int getPageIndex() {
		return PageIndex;
	}


	@JsonIgnore
	public void setPageIndex(int PageIndex) {
		PageIndex = PageIndex;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@JSONField(name="PageData")
	public List<?> getPageData() {
		return PageData;
	}

	@JsonIgnore
	public void setPageData(List<?> PageData) {
		this.PageData = PageData;
	}
	
	public boolean needPageNext() {
		
		return ( DataCount - (PageIndex - 1) * PageSize ) > PageSize;
	}
	
	public boolean needPagePrev() {
		return (PageIndex - 1) * PageSize  > 0;
	}
	
	
	public static void doPageTurnNxt(int pageNo,PageTurn helper){
		if(pageNo<0){
			return;
		}
		PageUtils page = helper.getData(pageNo);
		helper.handle(page);
		if (page.needPageNext()) {
			page = null;
			doPageTurnNxt(pageNo+1,helper);
		}
	}
	
	
	public static void doPageTurnPre(int pageNo,PageTurn helper){
		if(pageNo<0){
			return;
		}
		PageUtils page = helper.getData(pageNo);
		helper.handle(page);
		if (page.needPagePrev()) {
			page = null;
			doPageTurnPre(pageNo-1,helper);
		}
	}
	
	
	@Override
	public String toString() {
		return "PageUtil [DataCount=" + DataCount + ", PageSize=" + PageSize
				+ ", PageCount=" + PageCount + ", PageIndex=" + PageIndex
				+ ", PageData=" + PageData + "]";
	}


	public static void main(String[] args) {
	}
	
	
}
