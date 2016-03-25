package com.mobin.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


public class TravelAction extends ActionSupport implements ModelDriven<Travel>{
 
	private Travel travel=new Travel();
	
	private TravelServiceDao travelServicedao;
			
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

	public String findPageRecords() throws UnsupportedEncodingException{
	  String num=null;
	  String ST;
	  String SP;
	  String EP;
		String placelevel = null;
		String hotellevel = null;
		Page page = null;
	  
	  String flag = ServletActionContext.getRequest().getParameter("num");
	  
	  if(flag == null){
		  ST=travel.getST();
		  SP = travel.getSP();
		  EP=travel.getEP();
	  }else {
		  num=ServletActionContext.getRequest().getParameter("num");
		  placelevel = ServletActionContext.getRequest().getParameter("placelevel");
		  System.out.println(placelevel+"3333333");
          hotellevel = ServletActionContext.getRequest().getParameter("hotellevel");
		  ST=ServletActionContext.getRequest().getParameter("ST");
		  
		  SP =ServletActionContext.getRequest().getParameter("SP");
		 // SP= new String(SP.getBytes("iso8859-1"),"UTF-8");
		  EP=ServletActionContext.getRequest().getParameter("EP");
		 // EP= new String(EP.getBytes("iso8859-1"),"UTF-8");

	}

		System.out.println(num+"num");
		System.out.println(ST+"num");
		System.out.println(SP+"num");
		System.out.println(EP+"num");
	  if(placelevel != null && !"".equals(placelevel)){
		  System.out.println(placelevel+"999999999999");
		  page= travelServicedao.sortByPrice(placelevel,num,ST,SP,EP);
		  page.setPlacelevel(placelevel);
	  }else if(hotellevel != null && !"".equals(hotellevel)&& !hotellevel.equals("all")){
		  page = travelServicedao.sortByHotel(Integer.valueOf(hotellevel),num,ST,SP,EP);
		  page.setHotellevel(hotellevel);
	  }else {
		  page= travelServicedao.findPage(num,ST,SP,EP);
		  page.setPlacelevel("");
		  page.setHotellevel("");
	  }

	  page.setUri("travelAction.action");
	  ServletActionContext.getRequest().setAttribute("page", page);
	  
	  return SUCCESS;
  }


	public Travel getModel() {
		// TODO Auto-generated method stub
		return travel;
	}

}
