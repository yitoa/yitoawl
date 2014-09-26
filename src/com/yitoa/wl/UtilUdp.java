package com.yitoa.wl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Properties;

public class UtilUdp {
	
	/***
	 * 给设备传去手机最新的端口和ip地址进行打孔
	 * @param address
	 * @param port
	 * @param ipstr
	 */
	public void sendDevice(String address,int port,String ipstr,DatagramSocket client) {
		SocketAddress target = new InetSocketAddress(address, port);
		try {
			byte[] sendbuf = ipstr.getBytes();
			DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length,target);
			client.send(pack);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
