package com.yitoa.wl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.yitoa.wl.dao.IPDao;
import com.yitoa.wl.dao.UserDao;
import com.yitoa.wl.domian.IP;
import com.yitoa.wl.domian.User;
import com.yitoa.wl.util.MD5;

@WebService(endpointInterface = "com.yitoa.wl.YitoaWlWebServiceIF", serviceName = "reservationService")
public class YitoaWlWebServiceImpl implements YitoaWlWebServiceIF {

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

	/***************************************************************************
	 * 获取外网地址
	 * 
	 * @return
	 */
	public String getWIP() {
		MessageContext ctx = context.getMessageContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(AbstractHTTPDestination.HTTP_REQUEST);
		String clentIp = request.getRemoteAddr();
		return clentIp;
	}

	public void setIpDao(IPDao ipDao) {
		this.ipDao = ipDao;
	}

	@Override
	public String login(String username, String password) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		UserDao userDao = (UserDao) springContent.getBean("userDao");
		User u = new User();
		u.setUsername(username);
		u.setPassword(MD5.getMD5(password));
		String str = userDao.queryUser(u);
		return str;
	}

	@Override
	public String querySbByUserId(String userId) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		ipDao = (IPDao) springContent.getBean("ipDao");
		return ipDao.getSBByUserId(Integer.parseInt(userId));
	}

	@Override
	public String getVersion(String ver) {
		Properties ppt = new Properties();
		String apkVersion = null;
		String apkSize = null;// apk的大小
		String apkPath = null;// apk路径
		try {
			ppt.load(UDPThread.class
					.getResourceAsStream("/apkVersion.properties"));
			apkVersion = ppt.getProperty("apkVersion");
			apkSize = ppt.getProperty("apkSize");
			apkPath = ppt.getProperty("apkPath");
		} catch (Exception e) {
			e.printStackTrace();
		}
		apkVersion = apkVersion.replace(".", "");
		ver = ver.replace(".", "");
		if (Integer.parseInt(ver) < Integer.parseInt(apkVersion)) {// 服务器上有新的版本了
			return apkSize + "&" + apkPath + "&version";
		} else {
			return "notupd";
		}
	}

	@Override
	public String areaNetWork(String wlsn, String phoneMac, String userId) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		ipDao = (IPDao) springContent.getBean("ipDao");
		IP ip=new IP();
		ip.setMac(wlsn);
		ip.setPhoneMac(phoneMac);
		ip.setUserid(Integer.parseInt(userId));
		Object o=ipDao.queryIsNet(ip);
		if(o==null){
			return "not";
		}else{
			return o.toString();
		}
		
	}

}
