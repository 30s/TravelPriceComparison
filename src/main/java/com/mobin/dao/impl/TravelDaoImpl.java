package com.mobin.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.mobin.dao.TravelDao;
import com.mobin.domain.Travel;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

public class TravelDaoImpl implements TravelDao {

    private JdbcTemplate jdbcTemplate;

    private static String rowKey = " ";

    public Boolean login(String user, String password) {

        if (password.equals("154283") && user.equals("mobin@qq.com"))
            return true;
         else
            return false;
    }

    public List findPageRecords(int currentPageNum, int pageSize, String ST, String SP, String EP) {
        //得到每种不同条件查询的起始值
        int startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELS WHERE ST >= ? AND ROWKEY LIKE ? LIMIT 1", Integer.class, ST, SP + EP + "%")+(currentPageNum - 1) * pageSize;
        String sql = "SELECT * FROM TRAVELS where PAGEID > ? AND ST >= ? AND SP=? AND EP=? limit 8";
        List<Travel> travels = commonSort(sql,startkey,ST,SP,EP);
        return travels;
    }

    //管理员页面的数据分页
    public List findPageRecords(int currentPageNum, int pageSize, String SP) {
        List<Travel> travels = null;
        int startkey = 0;
        String sql = null;
       if(SP.equals("all")){
           startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELS  LIMIT 1",Integer.class);
           sql = "SELECT * FROM TRAVELS where PAGEID > ? limit 8";
       }else{
           startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELS WHERE SP=? LIMIT 1",Integer.class,SP);
           sql = "SELECT * FROM TRAVELS where PAGEID > ?  AND SP=? limit 8";

       }
        travels = jdbcTemplate.query(sql,
                new RowMapper<Travel>() {
                    public Travel mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Travel travel = new Travel();
                        travel.setSP(rs.getString("SP"));
                        travel.setEP(rs.getString("EP"));
                        return travel;
                    }

                }, startkey + (currentPageNum - 1) * pageSize, SP);

        return travels;
    }

    public int getTotalRecords(String ST, String SP, String EP) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELS WHERE ST >= ? AND ROWKEY LIKE ?", Integer.class, ST, SP + EP + "%");
    }

    public int getTotalRecords(String SP) {
        if(SP.equals("all")){
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVEL",Integer.class);
        }else{
           return  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVEL WHERE SP=?",Integer.class,SP);
        }
    }

    //按价格排序
    public List<Travel> sortByPrice(String flagHighORLow,int currentPageNum, int pageSize, String ST, String SP, String EP){
        String sql= null;
        int startkey = 0;
        if(flagHighORLow.equals("DESC")){
            sql = "SELECT * FROM TRAVELSORTPRICES WHERE PAGEID <= ? AND ST >= ? AND SP=? AND EP=? ORDER BY TOTALPRICE DESC limit 8";
            startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELSORTPRICES WHERE SP=? AND EP=? ORDER BY PAGEID DESC LIMIT 1", Integer.class,SP,EP)-(currentPageNum - 1) * pageSize;
        }else{
            sql = "SELECT * FROM TRAVELSORTPRICES WHERE PAGEID >= ? AND ST >= ? AND SP=? AND EP=?  limit 8";
            startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELSORTPRICES WHERE SP=? AND EP=? LIMIT 1", Integer.class,SP,EP)+(currentPageNum - 1) * pageSize;
        }
        System.out.println(sql);
       List<Travel> travels = commonSort(sql,startkey,ST,SP,EP);
        return travels;
    }

    //估算酒店星性价比
    public List<Travel> sortByHotel(int hotellevel,int currentPageNum, int pageSize, String ST, String SP, String EP){
        String sql= null;
        int startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELSORTHOTELS WHERE SP=? AND EP=? AND ST=? AND HOTELLEVEL=? LIMIT 1", Integer.class,SP,EP,ST,hotellevel)+(currentPageNum - 1) * pageSize;;
        sql = "SELECT * FROM TRAVELSORTHOTELS WHERE PAGEID >= ? AND ST>=? AND  SP=? AND EP=?  AND HOTELLEVEL="+hotellevel+" limit 8";
        List<Travel> travels = commonSort(sql,startkey,ST,SP,EP);
        return travels;
    }

    public Boolean deleteRecordsByPlace(String place) {
        return  false;
    }

    public Boolean deleteRecordsAll() {

        return false;
    }

    //分页查询结果共用部分
    public List<Travel> commonSort(String sql,int startkey,String ST, String SP, String EP){
        System.out.println(startkey);
        System.out.println(ST);
        System.out.println(SP);
        System.out.println(sql);
        List<Travel> travels = jdbcTemplate.query(sql,
                new RowMapper<Travel>() {
                    public Travel mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                       return  packingProerty(rs,rowNum);
                    }

                }, startkey, ST, SP, EP);
        return  travels;
    }

    //包装对象属性
    public Travel packingProerty(ResultSet rs,int rowNum){
        Travel travel = new Travel();
        try {
            travel.setROWKEY(rs.getString("ROWKEY"));
            travel.setURL(rs.getString("URL"));
            travel.setSP(rs.getString("SP"));
            travel.setEP(rs.getString("EP"));
            travel.setST(rs.getString("ST"));
            System.out.println(rs.getString("ST")+"ddddddddddddddddddddddddddd");
            travel.setET(rs.getString("ET"));
            travel.setSIGHTS(rs.getString("SIGHTS"));
            travel.setALLDATE(rs.getString("ALLDATE"));
            travel.setHOTEL(rs.getString("HOTEL"));
            travel.setTOTALPRICE(rs.getString("TOTALPRICE"));
            travel.setTRAFFIC(rs.getString("TRAFFIC"));
            travel.setIMAGE(rs.getString("IMAGE"));
            travel.setTRAVELTYPE(rs.getString("TRAVELTYPE"));
            travel.setSUPPLIER(rs.getString("SUPPLIER"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travel;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
