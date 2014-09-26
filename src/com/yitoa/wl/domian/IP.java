package com.yitoa.wl.domian;

import java.io.Serializable;

public class IP implements Serializable {
	private static final long serialVersionUID = 5605850646023804298L;

	private String mac;// mac地址

	private String wip;// 外网地址

	private String nip;// 内网地址

	private Integer id;

	private int dk;// 端口

	private String air;

	private String poweron;

	private int userid;// 用户id
	
	private String phoneMac;

	private String bm;

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getWip() {
		return wip;
	}

	public void setWip(String wip) {
		this.wip = wip;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public int getDk() {
		return dk;
	}

	public void setDk(int dk) {
		this.dk = dk;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAir() {
		return air;
	}

	public void setAir(String air) {
		this.air = air;
	}

	public String getPoweron() {
		return poweron;
	}

	public void setPoweron(String poweron) {
		this.poweron = poweron;
	}

	public String getPhoneMac() {
		return phoneMac;
	}

	public void setPhoneMac(String phoneMac) {
		this.phoneMac = phoneMac;
	}

}
