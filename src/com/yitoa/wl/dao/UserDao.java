package com.yitoa.wl.dao;

import com.yitoa.wl.domian.User;

public interface UserDao {
	
	/***
	 * 查询user
	 * @param user
	 * @return
	 */
	public String queryUser(User user);
	
	/***
	 * 增加user
	 * @param user
	 * @return
	 */
	public String adduser(User user);
	
	
	/****
	 * 根据用户名查找改用户名可存在
	 * @param username
	 * @return
	 */
	public Integer queryCount(String username);

}
