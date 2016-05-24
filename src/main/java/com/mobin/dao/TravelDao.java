package com.mobin.dao;

import java.util.List;

import com.mobin.domain.Travel;

public interface TravelDao {

	public List findPageRecords();
	public void getXkey(int currentPageNum, int pageSize,String ST,String SP,String EP,String TDATA,String HGRADE,String sort,String firstPrice,String secondPrice,String TRAFFIC);
	public int getTotalRecords();
	public Boolean login(String user,String password);
}
