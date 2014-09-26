package com.yitoa.wl.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.yitoa.wl.dao.IPDao;
import com.yitoa.wl.domian.IP;

public class IPDaoImpl implements IPDao {
	
	private SqlMapClientTemplate sqlMap;

	public void setSqlMap(SqlMapClientTemplate sqlMap) {
		this.sqlMap = sqlMap;
	}

	@Override
	public IP queryData(IP ip) {
		Object o=sqlMap.queryForObject("ip.queryWIp", ip);
		if(o==null){
			return null;
		}else{
			return (IP) o;
		}
	}

	@Override
	public int updateIp(IP ip) {
		int count=sqlMap.update("ip.updIp", ip);
		return count;
	}

	@Override
	public int  saveIp(IP ip) {
		return (Integer) sqlMap.insert("ip.add", ip);
		
	}

	@Override
	public int queryCount(String mac) {
		// TODO Auto-generated method stub
		return (Integer)sqlMap.queryForObject("ip.queryCount",mac);
	}

	@SuppressWarnings("unchecked")
	public String  getSBByUserId(Integer userId) {
		List<IP> list=sqlMap.queryForList("ip.querySbByUserId",userId);
		StringBuffer sb=new StringBuffer();
		if(list==null||list.size()==0){
			return "0";
		}else{
			String bm=null;
			String poweron=null;
			for(IP ip:list){
				if(ip.getBm()==null){
					bm="x";
				}else{
					bm=ip.getBm();
				}
				if(ip.getPoweron()==null){
					poweron="1";
				}else{
					poweron=ip.getPoweron();
				}
				String str=ip.getMac()+":"+poweron+":"+ip.getAir()+":"+bm;
				sb.append(str).append("&");
			}
			String str=sb.toString();
			str=str.substring(0,str.length()-1);
			return str;
			
		}
	}

	@Override
	public String updUserId(IP ip) {
		int count=sqlMap.update("ip.updUserId",ip);
		return String.valueOf(count);
	}

	@Override
	public String addUserIdWlsn(IP ip) {
		Object o=sqlMap.insert("ip.addUserIdWlsn",ip);
		if(o==null){
			return "0";
		}else{
			return o.toString();
		}
	}

	@Override
	public int queryByUserIdWlsn(IP ip) {
		Object o=sqlMap.queryForObject("ip.queryByUserIdWlsn",ip);
		if(o==null){
			return 0;
		}else{
			return Integer.parseInt(o.toString());
		}
		
	}

	@Override
	public String updBm(IP ip) {
		int count=sqlMap.update("ip.updBm",ip);
		return String.valueOf(count);
	}

	@Override
	public Object queryIsNet(IP ip) {
		return sqlMap.queryForObject("ip.queryIsNet",ip);
	}

}
