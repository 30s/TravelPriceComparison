package com.mobin.serviceDao.impl;

import com.mobin.dao.TravelDao;
import com.mobin.domain.Page;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.List;

/**
 * Created by root on 2/9/16.
 */
public class TravelServiceDaoImpl implements TravelServiceDao {

    private TravelDao travelDao;

    public Page findPage(String num, String ST, String SP, String EP,String TDATA,String HGRADE,String sort) {
        int pageNum = 1;
        if(num!=null&&!"".equals(num)){
            pageNum = Integer.parseInt(num);
        }
        int totalRecords = travelDao.getTotalRecords(ST,SP,EP);//获取记录的条数
        Page page = new Page(pageNum, totalRecords);
        List records = travelDao.findPageRecords(page.getCurrentPageNum(),page.getPageSize(),ST,SP,EP,TDATA,HGRADE,sort);
        page.setRecords(records);

        return page;
    }

    public Boolean login(String user, String password) {
        return travelDao.login(user, password);
    }

    public TravelDao getTravelDao() {
        return travelDao;
    }

    public void setTravelDao(TravelDao travelDao) {
        this.travelDao = travelDao;
    }

}
