package com.yitoa.wl.dao;

import com.yitoa.wl.domian.IP;

/**
 * 条码
 * 
 * 
 * 
 */
public interface PhoneDao{
	/***
	 * 根据mac查找最新的外网地址
	 * @return
	 */
	public IP queryData(String mac);
	
	
	/***
	 * 修改外网的ip地址
	 * @param ip
	 */
	public int  updateIp(IP ip);
	
	/***
	 * 添加ip
	 * @param ip
	 */
	public int saveIp(IP ip);
	
	
	
	/***
	 * 根据mac地址查询数据库存在多少
	 * @param mac
	 * @return
	 */
	public int queryCount(String mac);
	
	
}
