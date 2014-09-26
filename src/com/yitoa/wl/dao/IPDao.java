package com.yitoa.wl.dao;

import java.util.List;

import com.yitoa.wl.domian.IP;

/**
 * 条码
 * 
 * 
 * 
 */
public interface IPDao{
	/***
	 * 根据mac查找最新的外网地址
	 * @return
	 */
	public IP queryData(IP ip);
	
	
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
	
	
	/***
	 * 根据用户userid获取该用户下面的设备
	 * @param id
	 * @return
	 */
	public String getSBByUserId(Integer userId);
	
	
	/***
	 * 根据mac地址修改userid
	 * @param ip
	 * @return
	 */
	public String updUserId(IP ip);
	
	/***
	 * 根据userid和序列号查询
	 * @param ip
	 * @return
	 */
	public int  queryByUserIdWlsn(IP ip);
	
	
	/***
	 * 将userId和序列号注册到数据库中
	 * @param ip
	 * @return
	 */
	public  String addUserIdWlsn(IP ip);
	
	
	/****
	 * 修改别名
	 * @param ip
	 * @return
	 */
	public String updBm(IP ip);
	
	/***
	 * 查找设备和手机是否在同一个局域网
	 * @param ip
	 * @return
	 */
	public Object queryIsNet(IP ip);
	
	
}
