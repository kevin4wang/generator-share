package com.linlihudong.util;

public interface PageTurn {
	PageUtils getData(int pageNo);
	void handle(PageUtils  data);
}
