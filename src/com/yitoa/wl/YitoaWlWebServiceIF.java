package com.yitoa.wl;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface YitoaWlWebServiceIF {
/**
 * 获取外网地址
 * @return
 */
public String getWIP();

/***
 * 登陆
 * @param username
 * @param password
 * @return
 */
public String login(@WebParam(name = "username")String username,@WebParam(name = "password")String password);



/***
 * 根据userid查找改用户下面的所以设备
 * @param userId
 * @return
 */
public String querySbByUserId(@WebParam(name = "userId")String userId);


/**
 * 获取版本号
 * @return
 */
public String getVersion(@WebParam(name = "ver")String ver);


/***
 * 判断手机和设备是否在通一个局域网，如何是同一个局域网返回设备的内网地址，否则就返回not，
 * @param phoneMac  手机的mac地址
 * @param wlsn 设备的序列号
 * @return
 */
public String areaNetWork(@WebParam(name = "wlsn")String wlsn,@WebParam(name = "phoneMac")String phoneMac,@WebParam(name = "userId")String userId);


}
