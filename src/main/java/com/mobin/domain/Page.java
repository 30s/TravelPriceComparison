package com.mobin.domain;

import java.util.List;

public class Page {
	private List<Travel> records;//存放分页记录

	private int currentPageNum;
	private int totalPage;
	private final static  int pageSize = 8;

	private int totalRecords;
	private int startIndex;

	private int prePageNum;
	private int nextPageNum;


	private int startPage;
	private int endPage;

	private String uri;

	private String placelevel;
	private String hotellevel;

	public Page(){}


	public Page(int currentPageNum,int totalRecords){
		this.currentPageNum = currentPageNum;
		this.totalRecords = totalRecords;
		//计算总页数
		totalPage = totalRecords%pageSize==0?totalRecords/pageSize:totalRecords/pageSize+1;
		//计算每页开始记录的索引
		startIndex = (currentPageNum-1)*pageSize;

		//计算开始页码和结束页码：与当前页码有关

		/*
		 当前页码		应该显示的样子
		默认			1 2 3 4 5
		2			1 2 3 4 5
		4			2 3 4 5 6
		7			5 6 7 8 9
		9			6 7 8 9 10
		 */
		if(totalPage>5){
			//超出5页
			startPage = currentPageNum-2;
			endPage = currentPageNum+2;

			if(startPage<1){
				startPage = 1;
				endPage = 5;
			}

			if(endPage>totalPage){
				endPage = totalPage;
				startPage = totalPage -4;
			}

		}else{
			//总共没有5页
			startPage = 1;
			endPage = totalPage;
		}

	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public List<Travel> getRecords() {
		return records;
	}

	public void setRecords(List<Travel> records) {
		this.records = records;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}


	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public static  int getPageSize() {
		return pageSize;
	}



	public int getTotalRecords() {
		return totalRecords;
	}


	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}


	public int getStartIndex() {
		return startIndex;
	}


	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getPlacelevel() {
		return placelevel;
	}

	public void setPlacelevel(String placelevel) {
		this.placelevel = placelevel;
	}

	public String getHotellevel() {
		return hotellevel;
	}

	public void setHotellevel(String hotellevel) {
		this.hotellevel = hotellevel;
	}

	public int getPrePageNum() {
		prePageNum = currentPageNum-1;
		if(prePageNum<1)
			prePageNum = 1;
		return prePageNum;
	}


	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}


	public int getNextPageNum() {
		nextPageNum = currentPageNum+1;
		nextPageNum = currentPageNum+1;
		if(nextPageNum>totalPage)
			nextPageNum = totalPage;
		return nextPageNum;
	}


	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}


	@Override
	public String toString() {
		return "Page{" +
				"records=" + records +
				", currentPageNum=" + currentPageNum +
				", totalPage=" + totalPage +
				", totalRecords=" + totalRecords +
				", startIndex=" + startIndex +
				", prePageNum=" + prePageNum +
				", nextPageNum=" + nextPageNum +
				", startPage=" + startPage +
				", endPage=" + endPage +
				", uri='" + uri + '\'' +
				", placelevel='" + placelevel + '\'' +
				", hotellevel='" + hotellevel + '\'' +
				'}';
	}
}

