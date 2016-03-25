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

    public List findPageRecords(int currentPageNum, int pageSize, String ST, String SP, String EP) {
        //得到每种不同条件查询的起始值
        int startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINCOSTPER WHERE ST >= ? AND ROWKEY LIKE ? LIMIT 1", Integer.class, ST, SP + EP + "%")+(currentPageNum - 1) * pageSize;
        int endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINCOSTPER WHERE ST >= ? AND ROWKEY LIKE ? LIMIT 1", Integer.class, ST, SP + EP + "%")+(currentPageNum) * pageSize;

        //TODO RENAME TRAVELCOSTPER
        String sql = "SELECT URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER,IMAGE FROM MOBIN  WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINCOSTPER WHERE PAGEID >= ? AND PAGEID < ?)";
        List<Travel> travels = commonSort(sql,startkey,endkey);
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
                        travel.setSUPPLIER(rs.getString("SUPPLIER"));
                        travel.setSP(rs.getString("SP"));
                        travel.setEP(rs.getString("EP"));
                        return travel;
                    }
                }, startkey,endkey);
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
        List<Travel> travels = commonSort(rowkeysql,startkey,endkey);
        return travels;
    }

    //估算酒店星性价比
    //TODO RENAME TRAVELHOTEL
    public List<Travel> sortByHotel(int hotellevel,int currentPageNum, int pageSize, String ST, String SP, String EP){
       int startkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINHOTEL WHERE ROWKEYHOTEL LIKE ? AND ST=? AND HOTELLEVEL=? LIMIT 1", Integer.class,SP+EP+"%",ST,hotellevel)+(currentPageNum - 1) * pageSize;
       int endkey = jdbcTemplate.queryForObject("SELECT PAGEID FROM MOBINHOTEL WHERE ROWKEYHOTEL LIKE ? AND ST=? AND HOTELLEVEL=? LIMIT 1", Integer.class,SP+EP+"%",ST,hotellevel)+(currentPageNum) * pageSize;
        String sql = "SELECT URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER,IMAGE FROM MOBIN WHERE ROWKEY IN (SELECT ROWKEY FROM MOBINHOTEL WHERE PAGEID >= ? AND PAGEID < ?)";
        List<Travel> travels = commonSort(sql,startkey,endkey);
        return travels;
    }

    public Boolean deleteRecordsByPlace(String place) {
        return  false;
    }

    public Boolean deleteRecordsAll() {

        return false;
    }

    //分页查询结果共用部分
    public List<Travel> commonSort(String sql,int startkey,int endkey){
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

                }, startkey,endkey);
        return  travels;
    }

    //包装对象属性
    public Travel packingProerty(ResultSet rs,int rowNum){
        Travel travel = new Travel();
        try {
            travel.setURL(rs.getString("URL"));
            travel.setSP(rs.getString("SP"));
            travel.setEP(rs.getString("EP"));
            travel.setST(rs.getString("ST"));
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
