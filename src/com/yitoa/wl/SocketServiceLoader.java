package com.yitoa.wl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yitoa.wl.dao.IPDao;
import com.yitoa.wl.dao.PhoneDao;
import com.yitoa.wl.domian.IP;

public class SocketServiceLoader implements ServletContextListener {

	private DatagramSocket server_Socket = null;
	private int UDP_PORT = 84;
	private Thread thread;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != server_Socket) {
			server_Socket.close();
		}
		thread.interrupt();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		MainThread  mthread=new MainThread();
		thread=new Thread(mthread);
		thread.start();
	}

	public class MainThread implements Runnable {
		public void run() {
			try {
				server_Socket = new DatagramSocket(UDP_PORT);
				System.out.println("server start");
				DatagramPacket packet = null;
				while (true) {
					if (server_Socket == null) {
						break;
					} else {
						try {
							byte[] receive = new byte[1024];
							packet = new DatagramPacket(receive, receive.length);
							if(!server_Socket.isClosed()){
								server_Socket.receive(packet);
								caozuo(packet);
							}else{
								break;
							}
							//UDPThread udp = new UDPThread(packet, server_Socket);
							//udp.run();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
	}

	private void caozuo(DatagramPacket packet) {
		WebApplicationContext webApplicationContext = ContextLoader
				.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext
				.getServletContext();
		WebApplicationContext springContent = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		IPDao ipDao = (IPDao) springContent.getBean("ipDao");
		PhoneDao phoneDao = (PhoneDao) springContent.getBean("phoneDao");
		String sendMessage = null;
		UtilUdp udp = new UtilUdp();
		// 获取客户端ip
		String ip = packet.getAddress().getHostAddress();
		// 端口号
		int port = packet.getPort();
		// mac地址和内网地址
		String message = new String(packet.getData(), 0, packet.getLength()); // 可以接受设备的mac地址
		// 接受设备的信息，存储设备最新的端口和ip
		if (message != null && !"".equals(message)) {
			if (message.contains(";")) {
				// 设备访问服务端
				String[] splitMacIp = message.split(";");
				String mac =null;
				IP ipDomain = new IP();
				ipDomain.setWip(ip);
				ipDomain.setDk(port);
				// 接受成功了
				// 获取mac地址
				if(splitMacIp[0].contains("=")){
					String[] macs = splitMacIp[0].split("=");
					mac = macs[1];
					ipDomain.setMac(mac);
				}else{
					return;
				}
				// 获取内网地址
				if(splitMacIp[1].contains("=")){
					String[] nips = splitMacIp[1].split("=");
					String nip = nips[1];
					ipDomain.setNip(nip);
				}else{
					return;
				}
				if(splitMacIp[2].contains("=")){
					String poweron= splitMacIp[2].split("=")[1];
					ipDomain.setPoweron(poweron);
				}else{
					return;
				}
				if(splitMacIp[3].contains("=")){
					String air=splitMacIp[3].split("=")[1];
					ipDomain.setAir(air);
				}else{
					return;
				}
				// 首先查询数据库里面可有这个mac
				int count = ipDao.queryCount(mac);
				if (count == 0) {// 数据库里面没有，那就需要添加到数据库中
					int i = ipDao.saveIp(ipDomain);
					// System.out.println("===saveIp==="+i);
				} else {// 数据库里面存在，那就根据mac地址进行修改获取最新的内网地址
					int i = ipDao.updateIp(ipDomain);
					// System.out.println("===updateIp==="+i);
				}
				sendMessage = "success";
				udp.sendDevice(ip, port, sendMessage, server_Socket);
			} else if (message.contains("&cmd")) {// 手机访问的要求命令转发给设备
				String[] mess = message.split("&");
				IP phoneDomain = new IP();
				phoneDomain.setDk(port);
				phoneDomain.setMac(mess[1].split("=")[1]);
				phoneDomain.setWip(ip);
				String userId = mess[4].split("=")[1];
				int count = phoneDao.queryCount(mess[1].split("=")[1]);
				if (count == 0) {// 数据库里面没有，那就需要添加到数据库中
					phoneDao.saveIp(phoneDomain);
				} else {// 数据库里面存在，那就根据mac地址进行修改获取最新的内网地址
					phoneDao.updateIp(phoneDomain);
				}
				IP ipDomain = new IP();
				ipDomain.setMac(mess[0].split("=")[1]);
				ipDomain.setUserid(Integer.parseInt(userId));
				IP newipDomain = ipDao.queryData(ipDomain);
				if (newipDomain != null) {//
					if (newipDomain.getWip() != null
							&& (!"".equals(newipDomain.getWip()))) {
						udp.sendDevice(newipDomain.getWip(), newipDomain
								.getDk(), mess[1] + "&" + mess[2] + "&"
								+ mess[3] + "&cmd", server_Socket);
					} else {
						udp.sendDevice(ip, port, "NotfindSbAddress",
								server_Socket);
					}
				}
			} else if (message.contains("&end")) {// 设备操作完了返回的命令转发给手机
				String[] mess = message.split("&");
				IP ipDomain = new IP();
				StringBuffer sb = new StringBuffer();
				for (int i = 1; i < mess.length; i++) {
					sb.append(mess[i]).append("&");
				}
				ipDomain = phoneDao.queryData(mess[0].split("=")[1]);
				if (ipDomain != null) {//
					// 给手机发送操作完成的指令
					udp.sendDevice(ipDomain.getWip(), ipDomain.getDk(), sb
							.toString().substring(0, sb.length() - 1),
							server_Socket);
				}

			} else {// 手机让服务端给设备发送一个打孔命令
				System.out.println("===message1===");
				/*
				 * IP ipDomain = new IP(); ipDomain = ipDao.queryData(message); //
				 * 获取设备的ip和端口地址，给设备发送信息让他去打孔。 String strIp = "ip:" + ip +
				 * ";port:" + port + ";send"; if (ipDomain != null) {// //
				 * 给设备发送打孔的信息其中包括手机的外网地址和端口 udp.sendDevice(ipDomain.getWip(),
				 * ipDomain.getDk(), strIp, server_Socket); } sendMessage =
				 * "ip:" + ipDomain.getWip() + ";port:" + ipDomain.getDk(); //
				 * 给手机发个信息。 udp.sendDevice(ip,port,sendMessage,server_Socket);
				 */
			}

		}
	}

}
