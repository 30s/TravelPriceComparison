package com.mobin.domain;

import java.sql.Blob;

import org.apache.hadoop.classification.InterfaceAudience.Private;

public class Travel {


	private String ROWKEY;
	private String URL;
	private String SP;
	private String EP;
	private String ST;
	private String ET;
	private String SIGHTS;
	private String ALLDATE;
	private String HOTEL;
	private String TOTALPRICE;
	private String TRAFFIC;
	private String IMAGE;
	private String TRAVELTYPE;
	private String SUPPLIER;

	public String getROWKEY() {
		return ROWKEY;
	}

	public void setROWKEY(String ROWKEY) {
		this.ROWKEY = ROWKEY;
	}

	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSP() {
		return SP;
	}
	public void setSP(String sP) {
		SP = sP;
	}
	public String getEP() {
		return EP;
	}
	public void setEP(String eP) {
		EP = eP;
	}
	public String getST() {
		return ST;
	}
	public void setST(String sT) {
		ST = sT;
	}
	public String getET() {
		return ET;
	}
	public void setET(String eT) {
		ET = eT;
	}
	public String getSIGHTS() {
		return SIGHTS;
	}
	public void setSIGHTS(String sIGHTS) {
		SIGHTS = sIGHTS;
	}
	public String getALLDATE() {
		return ALLDATE;
	}
	public void setALLDATE(String aLLDATE) {
		ALLDATE = aLLDATE;
	}
	public String getHOTEL() {
		return HOTEL;
	}
	public void setHOTEL(String hOTEL) {
		HOTEL = hOTEL;
	}
	public String getTOTALPRICE() {
		return TOTALPRICE;
	}
	public void setTOTALPRICE(String tOTALPRICE) {
		TOTALPRICE = tOTALPRICE;
	}
	public String getTRAFFIC() {
		return TRAFFIC;
	}
	public void setTRAFFIC(String tRAFFIC) {
		TRAFFIC = tRAFFIC;
	}
	public String getTRAVELTYPE() {
		return TRAVELTYPE;
	}
	public void setTRAVELTYPE(String tRAVELTYPE) {
		TRAVELTYPE = tRAVELTYPE;
	}
	public String getSUPPLIER() {
		return SUPPLIER;
	}
	public void setSUPPLIER(String sUPPLIER) {
		SUPPLIER = sUPPLIER;
	}

	public String getIMAGE() {
		return IMAGE;
	}

	public void setIMAGE(String IMAGE) {
		this.IMAGE = IMAGE;
	}
}
