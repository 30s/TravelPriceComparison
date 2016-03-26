package com.mobin.serviceDao;

import com.mobin.domain.Page;
import com.mobin.domain.Travel;

import java.util.List;

/**
 * Created by root on 2/9/16.
 */
public interface TravelServiceDao {
    public Page findPage(String num, String ST, String SP, String EP);
    public Page findPage(String num,String SP);
    public Boolean login(String user,String password);
    public Boolean deleteRecordsByPlace(String place);
    public Boolean deleteRecordsAll();
    public Page sortByPrice(String flagHighORLow,String num, String ST, String SP, String EP);
    public Page sortByHotel(int hotellevel,String num, String ST, String SP, String EP);

}
