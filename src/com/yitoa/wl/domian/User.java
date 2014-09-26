package com.yitoa.wl.domian;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 4870353400494674290L;
	
	private int id;
	
	//用户名
	private String username;
	//密码
	private String password;
	//手机号码
	private String phonenumber;
	//邮箱
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
