package com.mobin.serviceDao;

import com.mobin.domain.Travel;

import java.util.List;

/**
 * Created by root on 2/9/16.
 */
public interface TravelServiceDao {
    public List<Travel> query(String SP, String EP, String ST);
}
