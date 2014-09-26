package com.yitoa.wl;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yitoa.wl.dao.IPDao;
import com.yitoa.wl.dao.UserDao;
import com.yitoa.wl.domian.IP;
import com.yitoa.wl.domian.User;
import com.yitoa.wl.util.MD5;

@WebService(endpointInterface = "com.yitoa.wl.YitoaUserWebServiceIF", serviceName = "userService")
public class YitoaUserWebServiceImpl implements YitoaUserWebServiceIF {

	private IPDao ipDao;
	private WebApplicationContext springContent = null;

	@Resource(name = "org.apache.cxf.jaxws.context.WebServiceContextImpl")
	private WebServiceContext context;

	public WebApplicationContext getSpringContent() {
		return springContent;
	}

	public void setSpringContent(WebApplicationContext springContent) {
		this.springContent = springContent;
	}

	@Override
	public String updUserId(String userId, String mess) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		ipDao = (IPDao) springContent.getBean("ipDao");
		String messStr[] = mess.split("&");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < messStr.length; i++) {
			String seri = messStr[i];
			IP ipDomian = new IP();
			ipDomian.setMac(seri);
			ipDomian.setUserid(Integer.parseInt(userId));
			String result = null;
			int res = ipDao.queryByUserIdWlsn(ipDomian);
			if (res == 0) {
				result = ipDao.addUserIdWlsn(ipDomian);
			} else {
				result = ipDao.updUserId(ipDomian);
			}
			sb.append(result);
		}
		String str = sb.toString();
		return str;
	}

	/***************************************************************************
	 * 注册用户
	 */
	@Override
	public String addUsers(String username, String password,
			String phonenumber, String email) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		UserDao userDao = (UserDao) springContent.getBean("userDao");
		int count = userDao.queryCount(username);
		if (count == 0) {
			User u = new User();
			u.setUsername(username);
			u.setPassword(MD5.getMD5(password));
			u.setPhonenumber(phonenumber);
			u.setEmail(email);
			String s = userDao.adduser(u);
			return s;
		} else {
			return "2";// 改用户名存在
		}
	}
	
	/***
	 * 添加别名
	 */
	@Override
	public String updBm(String wlsn, String bm,String userid) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		ipDao = (IPDao) springContent.getBean("ipDao");
		IP ip=new IP();
		ip.setMac(wlsn);
		ip.setBm(bm);
		ip.setUserid(Integer.parseInt(userid));
		return ipDao.updBm(ip);
	}

}
