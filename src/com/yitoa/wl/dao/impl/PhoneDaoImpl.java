package com.yitoa.wl.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.yitoa.wl.dao.PhoneDao;
import com.yitoa.wl.domian.IP;

public class PhoneDaoImpl implements PhoneDao {
	
	private SqlMapClientTemplate sqlMap;

	public void setSqlMap(SqlMapClientTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public IP queryData(String mac) {
		Object o=sqlMap.queryForObject("phone.queryWIp", mac);
		if(o==null){
			return null;
		}else{
			return (IP) o;
		}
	}

	@Override
	public int updateIp(IP ip) {
		int count=sqlMap.update("phone.updIp", ip);
		return count;
	}

	@Override
	public int  saveIp(IP ip) {
		return (Integer) sqlMap.insert("phone.add", ip);
		
	}

	@Override
	public int queryCount(String mac) {
		// TODO Auto-generated method stub
		return (Integer)sqlMap.queryForObject("phone.queryCount",mac);
	}

}
