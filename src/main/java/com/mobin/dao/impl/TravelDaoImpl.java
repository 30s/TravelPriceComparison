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

    //首页的分页(根据性价比排序)
    public List findPageRecords(int currentPageNum, int pageSize, String ST, String SP, String EP,String TDATA,String HGRADE,String sort) {
        //得到每种不同条件查询的起始值
        System.out.println(currentPageNum);
        System.out.println(pageSize);
        System.out.println(ST);
        System.out.println(SP);
        System.out.println(EP);
        int startkey;
        int startRecord = 0;
        int endkey;
        String rowkey = "";
        final StringBuffer sb = new StringBuffer();
        //查询都是基于行键
        if(!"".equals(sort)) { //按价格进行排序
            if ("".equals(HGRADE) && "".equals(TDATA))  //查询条件为出发点,目的地,时间，按价格排序
                rowkey = SP + "PRICE" + EP + "%";
            else if ("".equals(HGRADE))                 //查询条件为出发点,目的地,时间，出游天数,按价格排序
                rowkey = SP + "EEE" + EP + TDATA + "天%";
            else if ("".equals(TDATA))                   //查询条件为出发点,目的地,时间，酒店等级，按价格排序
                rowkey = SP + "FFF" + EP + HGRADE + "%";
            else                                          //查询条件为出发点,目的地,时间，出游天数,酒店等级，按价格排序
                rowkey = SP + "AAA" + EP + TDATA + "天" + HGRADE + "%";

            if ("DESC".equals(sort)){  //降序
                System.out.println(99);
                startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? ORDER BY ROWKEY DESC LIMIT 1 ", Integer.class, rowkey);
                endkey= startRecord - (currentPageNum - 1) * pageSize;
                startkey = startRecord-(currentPageNum) * pageSize;
            }else {  //升序（默认）
                startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? LIMIT 1 ", Integer.class, rowkey);
                startkey= startRecord + (currentPageNum - 1) * pageSize;
                endkey = startRecord+(currentPageNum) * pageSize;
            }

        }else{  //根据性价比排序
            if("".equals(HGRADE) && "".equals(TDATA))    //参考上面if语句
                rowkey = SP + EP + "%";
            else if("".equals(HGRADE))
                rowkey = SP + "DDD" + EP + TDATA + "天%";
            else if("".equals(TDATA))
                rowkey = SP + "CCC" + EP + HGRADE +"%";
            else
                rowkey = SP + "BBB" + EP + TDATA + "天" + HGRADE + "%";

            startRecord = jdbcTemplate.queryForObject("SELECT RECORDID FROM TRAVEL WHERE ROWKEY LIKE ? LIMIT 1", Integer.class, rowkey);
            startkey= startRecord + (currentPageNum - 1) * pageSize;
            endkey = startRecord+(currentPageNum) * pageSize;
        }
            String iSsort = "".equals(sort) ? "" : "ORDER BY ROWKEY "+sort;
            String sql = "SELECT ROWKEY FROM TRAVEL WHERE ROWKEY LIKE ? AND RECORDID >= ? AND RECORDID < ? " +iSsort;

            jdbcTemplate.query(sql,
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
                    }, rowkey,startkey,endkey);

        String sql1 = "SELECT URL,SP,EP,TITLE,TOUATT,ST,TDATA,PRICE,TRAFFIC,RETURN,TTYPE,IMAGE,PROXY,HOTEL,ORIGIN FROM TRAVEL WHERE ROWKEY IN ("+sb.toString()+") ";

        List<Travel> travels = jdbcTemplate.query(sql1,
                    new RowMapper<Travel>() {
                        public Travel mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return  packingProerty(rs,rowNum);  //封装对象
                        }
                    });
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
