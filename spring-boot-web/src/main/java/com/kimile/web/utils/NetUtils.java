package com.kimile.web.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class NetUtils {
	
	public static boolean isLoclePortUsing(int port) {
		return isPortUsing("127.0.0.1", port);
	}
	
	public static boolean isPortUsing(String host, int port) {
		boolean flag = false;
		try {
			InetAddress theAddress = InetAddress.getByName(host);
			Socket socket = new Socket(theAddress, port);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
