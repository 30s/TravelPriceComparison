package com.mobin.wordcount;

import org.springframework.jdbc.core.JdbcTemplate;

public class PhoenixHBaseTest {

	private JdbcTemplate jdbcTemplate;
	
	
	
	public PhoenixHBaseTest() {
		super();
	}

   
	public int query(){
		//jdbcTemplate.query("");
		System.out.println(jdbcTemplate+"333");
		int count = jdbcTemplate.queryForObject("SELECT count(*) from TRAVEL",Integer.class);
		System.out.println(count+"555");
		return count;
	}
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		System.out.println(jdbcTemplate+"222");
		this.jdbcTemplate = jdbcTemplate;
	}


	/*public static void main(String[] args) throws SQLException {
		long starttime = System.currentTimeMillis();
		System.out.println(starttime);
		Statement stmt = null;
		ResultSet rset = null;
		String rowkey = "澳门莫高窟";
		
		Connection con = DriverManager.getConnection("jdbc:phoenix:master");
		PreparedStatement statement = con.prepareStatement("select * from TRAVEL where ROWKEY like '澳门莫高窟%'");
		rset = statement.executeQuery();
		System.out.println(System.currentTimeMillis() - starttime+"uuuu");
		while (rset.next()) {
			System.out.println(rset.getString("ROWKEY")+"\t"+rset.getString("URL")+"\t"+rset.getString("SP")
					+"\t"+rset.getString("EP")+"\t"+rset.getString("ST")+"\t"+rset.getString("ET")+"\t"+rset.getString("SIGHTS")
					+"\t"+rset.getString("ALLDATE")+"\t"+rset.getString("HOTEL")+"\t"+rset.getString("TOTALPRICE")+"\t"+rset.getString("TRAFFIC")
					+"\t"+rset.getString("TRAVELTYPE")+"\t"+rset.getString("SUPPLIER"));
		
		}
		System.out.println("ppp"+(System.currentTimeMillis() - starttime));
		statement.close();
		con.close();
	}*/
	
}