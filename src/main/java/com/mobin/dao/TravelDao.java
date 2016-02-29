package com.mobin.dao;

import java.util.List;

import com.mobin.domain.Travel;

public interface TravelDao {

	public List<Travel> query(String SP, String EP, String ST);
}
