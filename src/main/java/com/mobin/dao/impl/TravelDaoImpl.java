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
    private String rowkey;
    private int startkey;
    private int startRecord = 0;
    private int endkey;
    private String isSortByPrice;

    private String rk1;
    private String rk2;

    private String findPageRecords_sql;


    public Boolean login(String user, String password) {

        if (password.equals("154283") && user.equals("mobin@qq.com"))
            return true;
         else
            return false;
    }

    //得到rowkey,startkey,endkey
    public void getXkey(int currentPageNum, int pageSize, String ST, String SP, String EP,String TDATA,String HGRADE,String sort
            ,String firstPrice,String secondPrice,String TRAFFIC) {
        System.out.println(currentPageNum);
        System.out.println(pageSize);
        System.out.println(ST);
        System.out.println(SP);
        System.out.println(EP);
        System.out.println(TDATA);
        System.out.println("------");
        System.out.println(sort);

        if (firstPrice != null && secondPrice != null) {
             if (null == HGRADE && null == TDATA && null == TRAFFIC) {
                rk1 = SP + "PRICE" + EP + firstPrice + "%";
                rk2 = SP + "PRICE" + EP + secondPrice + "%";
                rowkey = SP + "PRICE" + EP + "%";
             }
             else if(null == HGRADE && null == TDATA){
                 rk1 = SP + "HHH" + EP + TRAFFIC + firstPrice + "%";
                 rk2 = SP + "HHH" + EP + TRAFFIC + secondPrice + "%";
                 rowkey = SP + "HHH" + EP + TRAFFIC;
             }
             else if (null == HGRADE && null == TRAFFIC){
                 rk1 = SP + "EEE" + EP + TDATA + "天" + firstPrice + "%";
                 rk2 = SP + "EEE" + EP + TDATA + "天" + secondPrice + "%";
                 rowkey = SP + "EEE" + EP + TDATA + "天" + "%";
             }
             else if (null == TDATA && null == TRAFFIC){
                 rk1 = SP + "FFF" + EP + HGRADE + firstPrice + "%";
                 rk2 = SP + "FFF" + EP + HGRADE + secondPrice + "%";
                 rowkey = SP + "FFF" + EP + HGRADE + "%";
             }
             else if (null == HGRADE) {
                rk1 = SP + "EEE" + EP + TRAFFIC + "%" +  TDATA + "天" + firstPrice + "%";
                rk2 = SP + "EEE" + EP + TRAFFIC + "%" +  TDATA + "天" + secondPrice + "%";
                rowkey = SP + "EEE" + EP + TRAFFIC + "%" +  TDATA + "天";
             } else if (null == TDATA) {
                rk1 = SP + "FFF" + EP + HGRADE + firstPrice + "%";
                rk2 = SP + "FFF" + EP + HGRADE + secondPrice + "%";
                rowkey = SP + "FFF" + EP + HGRADE + "%";
             } else {
                rk1 = SP + "AAA" + EP + TDATA + "天" + HGRADE + firstPrice + "%";
                rk2 = SP + "AAA" + EP + TDATA + "天" + HGRADE + secondPrice + "%";
                rowkey = SP + "AAA" + EP + TDATA + "天" + HGRADE + "%";
             }
             System.out.println(rk1);
             startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVELTESTS WHERE ROWKEY >= ?  LIMIT 1 ", Integer.class, rk1);
             startkey = startRecord + (currentPageNum - 1) * pageSize;
             endkey = startRecord + (currentPageNum) * pageSize;
             findPageRecords_sql = "SELECT ROWKEY FROM TRAVELTESTS WHERE ROWKEY >= "+"'"+""+rk1+""+"'"+" AND ROWKEY <= "+"'"+""+rk2+""+"'"+" AND RECORDID >= ? AND RECORDID <= ?";
        }

        else {
            if (sort != null && !"".equals(sort)) { //按价格进行排序
                if (null == HGRADE && null == TDATA && TRAFFIC == null)  //查询条件为出发点,目的地,时间，按价格排序
                    rowkey =  SP + "PRICE" + EP + "%";
                else if(null == HGRADE && null == TDATA)
                    rowkey = SP + "GGG" + EP + TRAFFIC + "%";
                else if (null == HGRADE && null == TRAFFIC)                 //查询条件为出发点,目的地,时间，出游天数,按价格排序
                    rowkey = SP + "EEE" + EP + TDATA + "天%";
                else if (null == TDATA && null == TRAFFIC)                   //查询条件为出发点,目的地,时间，酒店等级，按价格排序
                    rowkey = SP + "FFF" + EP + HGRADE + "%";
                else if (null == TDATA)
                    rowkey = SP + "HHH" + EP + TRAFFIC + HGRADE + "%";
                else if(null == HGRADE)
                    rowkey = SP + "HHH" + EP + TRAFFIC + "%" +TDATA + "天%";
                else if(null == TRAFFIC)
                    rowkey = SP + "AAA" + EP + TDATA + "天" + HGRADE + "%";
                else                                          //查询条件为出发点,目的地,时间，出游天数,酒店等级，按价格排序
                    rowkey = SP + "HHH" + EP + TRAFFIC + HGRADE +TDATA + "天%";

                if ("DESC".equals(sort)) {  //降序
                    System.out.println(99 + rowkey);
                    startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVELTESTS WHERE ROWKEY LIKE ? ORDER BY ROWKEY DESC LIMIT 1 ", Integer.class, rowkey);
                    endkey = startRecord - (currentPageNum - 1) * pageSize;
                    startkey = startRecord - (currentPageNum) * pageSize;
                } else {  //升序（默认）
                    System.out.println(rowkey + "   mobin");
                    startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVELTESTS WHERE  ? LIMIT 1 ", Integer.class, rowkey);
                    startkey = startRecord + (currentPageNum - 1) * pageSize;
                    endkey = startRecord + (currentPageNum) * pageSize;
                }

            } else {  //根据性价比排序
                String r = "ROWKEY LIKE ";
                if ((null == HGRADE && null == TDATA && TRAFFIC == null))    //参考上面if语句
                    rowkey = SP + EP + "%";
                else if(null == HGRADE && null == TDATA)
                    rowkey = SP + "KKK" + EP + TRAFFIC + "%";
                else if(null == HGRADE && null == TRAFFIC)
                    rowkey = SP + "DDD" + EP + TDATA + "天%";
                else if (null == TDATA && null == TRAFFIC)
                    rowkey = SP + "CCC" + EP +HGRADE + "%";
                else if (null == TDATA)
                    rowkey = SP + "LLL" + EP + TRAFFIC + HGRADE + "%";
                else if(null == HGRADE)
                    rowkey = SP + "LLL" + EP + TRAFFIC + "%" +TDATA + "天%";
                else if(null == TRAFFIC)
                    rowkey = SP + "BBB" + EP + TDATA + "天" + HGRADE + "%";
                else
                    rowkey = SP + "LLL" + EP + TRAFFIC + HGRADE +TDATA + "天%";

                System.out.println(rowkey + "KPOP");
                //得到行记录数
                startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVELTESTS WHERE  " + r + "  ? LIMIT 1", Integer.class, rowkey);
                startkey = startRecord + (currentPageNum - 1) * pageSize;
                endkey = startRecord + (currentPageNum) * pageSize;

            }
            findPageRecords_sql = "SELECT ROWKEY FROM TRAVELTESTS WHERE ROWKEY LIKE "+"'"+""+rowkey+""+"'"+" AND RECORDID >= ? AND RECORDID < ? ";
        }
            isSortByPrice = (null == sort) ? "" : "ORDER BY PRICE " + sort;


    }

    //调用该函数之前，该函数中所用到的变量如startkey,endkey等都已被getXKey初始化好了
    public List findPageRecords() {
        System.out.println(isSortByPrice+"llllll");
        System.out.println(startkey+"llllll");
        System.out.println(endkey+"llllll");
        System.out.println(findPageRecords_sql+"llllll");

        //查询都是基于行键
            final StringBuffer sb = new StringBuffer();
            jdbcTemplate.query(findPageRecords_sql,
                    new RowMapper<Integer>() {
                        public Integer mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            System.out.println(rs.getString("ROWKEY"));
                            String ROWKEY = rs.getString("ROWKEY");
                            String subRowkey;

                            if(ROWKEY.contains("-"))
                                 subRowkey = ROWKEY.split("-")[1];
                            else
                                 subRowkey = ROWKEY;
                            if(rowNum > 0)
                                sb.append(","+"'"+subRowkey+ "'");
                            else
                                sb.append("'"+subRowkey+ "'");
                            return 0;
                        }
                    },startkey,endkey);

        //TODO  去掉ROWKEY
        String sql1 = "SELECT ROWKEY,URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN FROM TRAVELTESTS WHERE ROWKEY IN ("+sb.toString()+")  " +isSortByPrice;

        List<Travel> travels = jdbcTemplate.query(sql1,
                    new RowMapper<Travel>() {
                        public Travel mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return  packingProerty(rs,rowNum);  //封装对象
                        }
                    });
        return travels;
    }

    public int getTotalRecords() {

        if(rk1== null && rk2 == null)////当按价格区间进行筛选时其count(*)语句不同于其他情况
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELTESTS WHERE  ROWKEY LIKE ? ", Integer.class, rowkey);
        else
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELTESTS WHERE  ROWKEY >= ? AND ROWKEY <= ?", Integer.class, rk1,rk2);
    }

    public int getTotalRecords(String SP) {
        if(SP.equals("all")){
            return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELTESTS",Integer.class);
        }else{
           return  jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELTESTS WHERE SP=?",Integer.class,SP);
        }
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
            System.out.println(rs.getString("ROWKEY"));
            System.out.println(rs.getString("PRICE"));
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
