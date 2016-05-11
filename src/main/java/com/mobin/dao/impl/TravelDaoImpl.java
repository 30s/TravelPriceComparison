package com.mobin.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.mobin.dao.TravelDao;
import com.mobin.domain.Travel;

public class TravelDaoImpl implements TravelDao {

    private JdbcTemplate jdbcTemplate;

    public Boolean login(String user, String password) {

        if (password.equals("154283") && user.equals("mobin@qq.com"))
            return true;
         else
            return false;
    }

    //首页的分页
    public List findPageRecords(int currentPageNum, int pageSize, String ST, String SP, String EP,int HGRADE,String TDATA) {
        //得到每种不同条件查询的起始值
        System.out.println(currentPageNum);
        System.out.println(pageSize);
        System.out.println(ST);
        System.out.println(SP);
        System.out.println(EP);
        int startkey;
        int endkey;
        //查询都是基于行键
       // String SQL = "SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? " + ("".equals(HGRADE) ? "" : "AND HGRADE = ?") + ("".equals(TDATA) ? "" : "AND TDATA = ?") + "LIMIT 1";
        if("".equals(HGRADE) && "".equals(TDATA)){
            System.out.println(14);
             startkey= jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? LIMIT 1", Integer.class, SP + EP + "%",ST)+(currentPageNum - 1) * pageSize;
             endkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ?  LIMIT 1", Integer.class,SP + EP + "%", ST )+(currentPageNum) * pageSize;
        }else if("".equals(HGRADE)){
            System.out.println(24);
            startkey= jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND TDATA = ? LIMIT 1", Integer.class, SP + EP + "%",ST,TDATA)+(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND TDATA = ? LIMIT 1", Integer.class,SP + EP + "%", ST,TDATA)+(currentPageNum) * pageSize;
        }else if("".equals(TDATA)){
            System.out.println(34+"   "+HGRADE+"  "+ST);
            startkey= jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND HGRADE = ? LIMIT 1", Integer.class, SP + EP + "%",ST,HGRADE)+(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND HGRADE = ? LIMIT 1", Integer.class,SP + EP + "%", ST,HGRADE)+(currentPageNum) * pageSize;
        }else {
            System.out.println(44);
            startkey= jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND HGRADE = ? AND TDATA = ? LIMIT 1", Integer.class, SP + EP + "%",ST,HGRADE,TDATA)+(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? AND HGRADE = ? AND TDATA = ? LIMIT 1", Integer.class,SP + EP + "%", ST,HGRADE,TDATA)+(currentPageNum) * pageSize;
        }
        //String rowkey = jdbcTemplate.queryForObject("SELECT * FROM TRAVELS WHERE ROWKEY LIKE 'aomenhangzhou%'")
        String sql = "SELECT URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN FROM TRAVEL WHERE ROWKEY LIKE ? AND RECORDID >= ? AND RECORDID < ?";
        List<Travel> travels = commonSort(sql,SP + EP + "%",startkey,endkey);
        return travels;
    }



    //首页的分页
    public List findPageRecords1(int currentPageNum, int pageSize, String ST, String SP, String EP) {
        //得到每种不同条件查询的起始值
        System.out.println(currentPageNum);
        System.out.println(pageSize);
        System.out.println(ST);
        System.out.println(SP);
        System.out.println(EP);
        //查询都是基于行键
        int startkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ? LIMIT 1", Integer.class, SP + EP + "%",ST)+(currentPageNum - 1) * pageSize;
        int endkey = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? AND ST >= ?  LIMIT 1", Integer.class,SP + EP + "%", ST )+(currentPageNum) * pageSize;

        //TODO RENAME TRAVELCOSTPER
        //String rowkey = jdbcTemplate.queryForObject("SELECT * FROM TRAVELS WHERE ROWKEY LIKE 'aomenhangzhou%'")
        String sql = "SELECT URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN FROM TRAVEL WHERE ROWKEY LIKE ? AND RECORDID >= ? AND RECORDID < ?";
        List<Travel> travels = commonSort(sql,SP + EP + "%",startkey,endkey);
        return travels;
    }

    //管理员页面的数据分页
    public List findPageRecords(int currentPageNum, int pageSize, String SP) {
        List<Travel> travels = null;
        int startkey = 0;
        int endkey = 0;
        String sql = null;
       if(SP.equals("all")){
           System.out.println(currentPageNum);
           startkey = jdbcTemplate.queryForInt("SELECT PAGEID FROM MOBINCOSTPER LIMIT 1")+(currentPageNum - 1) * pageSize;
           endkey = jdbcTemplate.queryForInt("SELECT PAGEID FROM MOBINCOSTPER LIMIT 1")+(currentPageNum) * pageSize;
       }else{
            startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINCOSTPER WHERE  ROWKEY LIKE ? LIMIT 1", Integer.class,  SP + "%")+(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINCOSTPER WHERE  ROWKEY LIKE ? LIMIT 1", Integer.class,  SP + "%")+(currentPageNum) * pageSize;
       }
        sql = "SELECT SP,EP,SUPPLIER FROM MOBIN  WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINCOSTPER WHERE PAGEID >= ? AND PAGEID < ?)";
        System.out.println(startkey+"  "+endkey);
        travels = jdbcTemplate.query(sql,
                new RowMapper<Travel>() {
                    public Travel mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        Travel travel = new Travel();
                        travel.setORIGIN(rs.getString("ORIGIN"));
                        travel.setSP(rs.getString("SP"));
                        travel.setEP(rs.getString("EP"));
                        return travel;
                    }
                }, startkey,endkey);
         return travels;
    }

    public int getTotalRecords(String ST, String SP, String EP) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVEL WHERE ST >= ? AND ROWKEY LIKE ?", Integer.class, ST, SP + EP + "%");
    }

    public int getTotalRecords(String SP) {
        if(SP.equals("all")){
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVEL",Integer.class);
        }else{
           return  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVEL WHERE SP=?",Integer.class,SP);
        }
    }

    //按价格排序
    //TODO RENAME TRAVELPRICE
    public List<Travel> sortByPrice(String flagHighORLow,int currentPageNum, int pageSize, String ST, String SP, String EP){
        String rowkeysql= null;
        int startkey = 0;
        int endkey = 0;
        final StringBuffer sb = new StringBuffer();
        if(flagHighORLow.equals("DESC")){
            startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINPRICE WHERE ROWKEYPRICE LIKE ? AND ST=? ORDER BY PAGEID DESC LIMIT 1", Integer.class,SP+EP+"%",ST)-(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINPRICE WHERE ROWKEYPRICE LIKE ? AND  ST=? ORDER BY PAGEID DESC LIMIT 1", Integer.class,SP+EP+"%",ST)-(currentPageNum) * pageSize;
            rowkeysql = " SELECT URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER,IMAGE FROM MOBIN WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINPRICE WHERE PAGEID <= ? AND PAGEID > ?) ORDER BY TOTALPRICE DESC";

        }else{
            startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINPRICE WHERE ROWKEYPRICE LIKE ? AND ST=? LIMIT 1", Integer.class,SP+EP+"%",ST)+(currentPageNum - 1) * pageSize;
            endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINPRICE WHERE ROWKEYPRICE LIKE ? AND ST=? LIMIT 1", Integer.class,SP+EP+"%",ST)+(currentPageNum) * pageSize;
            rowkeysql = " SELECT URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER,IMAGE FROM MOBIN WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINPRICE WHERE PAGEID >= ? AND PAGEID < ?) ORDER BY TOTALPRICE ASC";
        }
        System.out.println(rowkeysql);
        System.out.println(startkey+"rowkey....................................");
        System.out.println(endkey);
        List<Travel> travels = commonSort(rowkeysql,SP + EP + "%",startkey,endkey);
        return travels;
    }

    //估算酒店星性价比
    //TODO RENAME TRAVELHOTEL
    public List<Travel> sortByHotel(int hotellevel,int currentPageNum, int pageSize, String ST, String SP, String EP){
       int startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINHOTEL WHERE ROWKEYHOTEL LIKE ? AND ST=? AND HOTELLEVEL=? LIMIT 1", Integer.class,SP+EP+"%",ST,hotellevel)+(currentPageNum - 1) * pageSize;
       int endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINHOTEL WHERE ROWKEYHOTEL LIKE ? AND ST=? AND HOTELLEVEL=? LIMIT 1", Integer.class,SP+EP+"%",ST,hotellevel)+(currentPageNum) * pageSize;
        String sql = "SELECT URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER,IMAGE FROM MOBIN WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINHOTEL WHERE PAGEID >= ? AND PAGEID < ?)";
        List<Travel> travels = commonSort(sql,SP + EP + "%",startkey,endkey);
        return travels;
    }

    public Boolean deleteRecordsByPlace(String place) {
        return  false;
    }

    public Boolean deleteRecordsAll() {

        return false;
    }

    //分页查询结果共用部分
    public List<Travel> commonSort(String sql,String rowkey,int startkey,int endkey){
        System.out.println(startkey);
        System.out.println(endkey);
        System.out.println(sql);
        List<Travel> travels = jdbcTemplate.query(sql,
                new RowMapper<Travel>() {
                    public Travel mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        System.out.println(rs);
                       return  packingProerty(rs,rowNum);
                    }

                }, rowkey,startkey,endkey);
        return  travels;
    }

    //包装对象属性
    public Travel packingProerty(ResultSet rs,int rowNum){
        Travel travel = new Travel();
        try {
            //URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN
            travel.setURL(rs.getString("URL"));
            travel.setSP(rs.getString("SP"));
            travel.setEP(rs.getString("EP"));

            travel.setTITLE(rs.getString("TITLE"));
            travel.setTOUATT(rs.getString("TOUATT"));
            travel.setST(rs.getString("ST"));
            travel.setTDATA(rs.getString("TDATA"));

            travel.setPRICE(rs.getString("PRICE"));
            travel.setTRAFFIC(rs.getString("TRAFFIC"));
            travel.setRETURN(rs.getString("RETURN"));

            travel.setTTYPE(rs.getString("TTYPE"));
            travel.setIMAGE(rs.getString("IMAGE"));
            travel.setPROXY(rs.getString("PROXY"));
            travel.setHOTEL(rs.getString("HOTEL"));
            travel.setORIGIN(rs.getString("ORIGIN"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return travel;
    }

    public void update() {
        //PRICE
        String sqlPrice = "UPSERT INTO MOBINPRICE SELECT ROWKEYPRICE,NEXT VALUE FOR MOBINSEQ,ST,ROWKEY FROM MOBIN GROUP BY ROWKEYPRICE,TOTALPRICE,ST,ROWKEY,ROWKEY ORDER BY ROWKEYPRICE;";

        //HOTEL
        String sqlHotel = "UPSERT INTO MOBINHOTEL SELECT ROWKEYHOTEL,HOTELLEVEL,NEXT VALUE FOR MOBINSEQ,ST,ROWKEY FROM MOBIN GROUP BY ROWKEYHOTEL,HOTELLEVEL,ST,ROWKEY;";

        //COSTPER
        String sqlCostPer = "UPSERT INTO MOBINCOSTPER SELECT ROWKEY,NEXT VALUE FOR MOBINSEQ,ST FROM MOBIN GROUP BY ROWKEY,ST;";

        jdbcTemplate.execute(sqlPrice);
        jdbcTemplate.execute(sqlHotel);
        jdbcTemplate.execute(sqlCostPer);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
