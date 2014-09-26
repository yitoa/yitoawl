package com.yitoa.wl.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.yitoa.wl.dao.UserDao;
import com.yitoa.wl.domian.User;

public class UserDaoImpl implements UserDao {
	private SqlMapClientTemplate sqlMap;

	public void setSqlMap(SqlMapClientTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}
	
	/***
	 * 1：表示有改用户  查询成功了
	 * 0：表示没有改用户。
	 */
	@Override
	public String queryUser(User user) {
		Object o=(Object) sqlMap.queryForObject("user.queryUser", user);
		if(o!=null){
			User u=(User) o;
			return String.valueOf(u.getId());
		}else{
			return "0";
		}
	}
	
	/***
	 * 1：表示添加成功
	 * 0：表示添加失败
	 */
	@Override
	public String adduser(User user) {
	 Integer inte=(Integer) sqlMap.insert("user.addUser", user);
	 if(inte>0){
		 return "1";
	 }else{
		return "0";
	 }
	}

	@Override
	public Integer queryCount(String username) {

		return (Integer) sqlMap.queryForObject("user.queryCount",username);
	}
}
