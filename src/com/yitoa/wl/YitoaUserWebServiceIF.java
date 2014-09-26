package com.yitoa.wl;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface YitoaUserWebServiceIF {

public String updUserId(@WebParam(name = "userId")String userId,@WebParam(name = "mess")String mess);


/***
 * 注册用户
 * @param username
 * @param password
 * @param phonenumber
 * @param email
 * @return
 */
public String addUsers(@WebParam(name = "username")String username,@WebParam(name = "password")String password,@WebParam(name = "phonenumber")String phonenumber,@WebParam(name = "email")String email);

/***
 * 修改别名
 * @param wlsn
 * @param bm
 * @return
 */
public String updBm(@WebParam(name = "wlsn")String wlsn,@WebParam(name = "bm")String bm,@WebParam(name = "userid")String userid);
}
