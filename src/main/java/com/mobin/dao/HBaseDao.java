package com.mobin.dao;

/**
 * Created by hadoop on 3/15/16.
 */
public interface HBaseDao {

    public void truncate();
    public void generateHFile();
}
