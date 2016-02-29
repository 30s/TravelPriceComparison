package com.mobin.serviceDao.impl;

import com.mobin.dao.TravelDao;
import com.mobin.domain.Travel;
import com.mobin.serviceDao.TravelServiceDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by root on 2/9/16.
 */
public class TravelServiceDaoImpl implements TravelServiceDao {

    private TravelDao travelDao;


    public List<Travel> query(String SP, String EP, String ST) {

        return travelDao.query(SP, EP, ST);
    }

    public TravelDao getTravelDao() {
        return travelDao;
    }

    public void setTravelDao(TravelDao travelDao) {
        this.travelDao = travelDao;
    }
}
