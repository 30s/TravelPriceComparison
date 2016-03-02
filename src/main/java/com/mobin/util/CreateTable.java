package com.mobin.util;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hadoop on 2/29/16.
 */
public class CreateTable {
    ////在Phoenix里创建表（可分页）
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;


    public int  createView(String ST,String SP,String EP){
        String sequence = "CREATE SEQUENCE PP";

        String create_viewToHBase = "CREATE VIEW TRAVEL (ROWKEY VARCHAR PRIMARY KEY,INFO.URL VARCHAR,INFO.SP VARCHAR," +
                "INFO.EP VARCHAR,INFO.ST VARCHAR,INFO.ET VARCHAR,INFO.SIGHTS VARCHAR,INFO.ALLDATE VARCHAR," +
                "INFO.HOTEL VARCHAR,INFO.TOTALPRICE VARCHAR,INFO.TRAFFIC VARCHAR,INFO.TRAVELTYPE VARCHAR," +
                "INFO.SUPPLIER VARCHAR,INFO.RECORD INTEGER)";

       String create_view = "CREATE TABLE TRAVELS (ROWKEY VARCHAR PRIMARY KEY,PAGEID BIGINT,INFO.URL VARCHAR,INFO.SP VARCHAR," +
                "INFO.EP VARCHAR,INFO.ST VARCHAR,INFO.ET VARCHAR,INFO.SIGHTS VARCHAR,INFO.ALLDATE VARCHAR," +
                "INFO.HOTEL VARCHAR,INFO.TOTALPRICE VARCHAR,INFO.TRAFFIC VARCHAR,INFO.TRAVELTYPE VARCHAR," +
                "INFO.SUPPLIER VARCHAR,INFO.RECORD INTEGER)";

        String UPSERT_SQL =  "UPSERT INTO TEST SELECT ROWKEY,NEXT VALUE FOR PAGEID,URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL," +
                "TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER FROM TRAVEL";

        /*jdbcTemplate.execute(sequence);//创建sequence
        jdbcTemplate.execute(create_viewToHBase);
        jdbcTemplate.execute(create_view);//最终可分页的表
        jdbcTemplate.update(UPSERT_SQL);//从不可分页的TRAVEL视图中插入数据到可分页的TRAVELS表中*/

      /*  Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM TRAVEL WHERE ST=? AND ROWKEY LIKE ?");
            stmt.setString(1, ST);
            stmt.setString(2, SP+EP+"%");
            rs = stmt.executeQuery();
            if(rs.next()){
                System.out.print("uuuuuuuuu");
                return rs.getInt(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

             System.out.print("lll");
        return 0;*/
     //int i =  jdbcTemplate.queryForInt("SELECT PAGEID FROM TRAVELS WHERE ROWKEY LIKE '"+SP+EP+"%"+"' limit 1");
       int i = jdbcTemplate.queryForObject("SELECT PAGEID FROM TRAVELS WHERE ST >= ? AND ROWKEY LIKE ? LIMIT 1",Integer.class,ST,SP+EP+"%");
        System.out.print(i+"bbbb");
        return  i ;

    }



    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
