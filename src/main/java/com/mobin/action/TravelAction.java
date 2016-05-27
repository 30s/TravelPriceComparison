package com.mobin.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.hadoop.mapreduce.JobRunner;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TravelAction extends ActionSupport implements ModelDriven<Travel>{
 
	private Travel travel=new Travel();
	
	private TravelServiceDao travelServicedao;
	private String jsonString;
	private String pagejson;
	private String day;
	private String hotel;
	private String datepicker;
	private String citySelect;
	private String citySelect1;
	private  String pageNum;
	private String priceSection;
	private String traffic;

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String findPageRecords() throws IOException {
		System.out.println("llllll");
		Page page = null;
		String firstPrice = null;
		String secondPrice = null;

		//这三个传过来的值可能是null或者"",为避免DAO中少写判断条件，先在些统一将不合法的值转为null
		if("".equals(hotel))
			hotel = null;

		if("".equals(day))
			day = null;

		if("".equals(price))
			price = null;
		if("".equals(traffic))
			traffic = null;
		if(priceSection != null && !"".equals(priceSection)){
			String[] pricesection = priceSection.split("-");
			firstPrice = pricesection[0];
			secondPrice = pricesection[1];
		}
		page = travelServicedao.findPage(pageNum, datepicker, PinyinHelper.convertToPinyinString(new String(citySelect.getBytes("iso8859-1"),"UTF-8"),"",
				PinyinFormat.WITHOUT_TONE), PinyinHelper.convertToPinyinString(new String(citySelect1.getBytes("iso8859-1"),"UTF-8"),"",
				PinyinFormat.WITHOUT_TONE),day,hotel,price,firstPrice,secondPrice,traffic);
		page.setUri("travelAction.action");
		jsonString = JSON.toJSONString(page);
		String result = callback+"("+jsonString+")";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(result);

	  return null;
  }

	public Travel getModel() {
		return travel;
	}

	public String getPriceSection() {
		return priceSection;
	}

	public void setPriceSection(String priceSection) {
		this.priceSection = priceSection;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getPagejson() {
		return pagejson;
	}

	public void setPagejson(String pagejson) {
		this.pagejson = pagejson;
	}

	public String getDatepicker() {
		return datepicker;
	}

	public void setDatepicker(String datepicker) {
		this.datepicker = datepicker;
	}

	public String getCitySelect() {
		return citySelect;
	}

	public void setCitySelect(String citySelect) {
		this.citySelect = citySelect;
	}

	public String getCitySelect1() {
		return citySelect1;
	}

	public void setCitySelect1(String citySelect1) {
		this.citySelect1 = citySelect1;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	private String callback;

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	private String price;

	public Travel getTravel() {
		return travel;
	}

	public void setTravel(Travel travel) {
		this.travel = travel;
	}

	public TravelServiceDao getTravelServicedao() {
		return travelServicedao;
	}

	public void setTravelServicedao(TravelServiceDao travelServicedao) {
		this.travelServicedao = travelServicedao;
	}


}
