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

public class TravelDaoImpl implements TravelDao{

	private JdbcTemplate jdbcTemplate;

	private static  String rowKey = " ";



	/*public List<Travel> query(String SP, String EP, String ST) {

		List<Travel> travels = 	jdbcTemplate.query("SELECT ROWKEY,URL,SP,EP,ST,ET,SIGHTS,ALLDATE,HOTEL,TOTALPRICE,TRAFFIC,TRAVELTYPE,SUPPLIER from TRAVELS WHERE SP=? AND EP=? AND ST=?",
			new RowMapper<Travel>() {
				public Travel mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Travel travel = new Travel();
					travel.setROWKEY(rs.getString("ROWKEY"));
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
					travel.setTRAVELTYPE(rs.getString("TRAVELTYPE"));
					travel.setSUPPLIER(rs.getString("SUPPLIER"));
					return travel;
				}
			  
			},SP,EP,ST);
		rowKey = travels.get(travels.size()-1).getROWKEY();
		System.out.println(travels.size()+"ppppppp");
		System.out.println(rowKey+"qqqqq");
		return travels;
		
	}*/


	public List findPageRecords(int currentPageNum, int pageSize,String ST,String SP,String EP) {
		//得到每种不同条件查询的起始值
		int startkey = jdbcTemplate.queryForObject("SELECT * FROM TRAVELS WHERE ST >= ? AND ROWKEY LIKE ? LIMIT 1",Integer.class,ST,SP+EP+"%");

		List<Travel> travels = 	jdbcTemplate.query("SELECT * FROM TRAVELS where PAGEID > ? AND ST >= ? AND SP=? AND EP=? limit 8",
				new RowMapper<Travel>() {
					public Travel mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Travel travel = new Travel();
						travel.setROWKEY(rs.getString("ROWKEY"));
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
						travel.setTRAVELTYPE(rs.getString("TRAVELTYPE"));
						travel.setSUPPLIER(rs.getString("SUPPLIER"));
						return travel;
					}

				},startkey+(currentPageNum - 1)*pageSize,ST,SP,EP);

		    return  travels;
	}

	public int getTotalRecords(String ST,String SP,String EP) {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TRAVELS WHERE ST >= ? AND ROWKEY LIKE ?",Integer.class,ST,SP+EP+"%");
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
