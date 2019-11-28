package cn.hnu.dbms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Socket client = new Socket("localhost", 9999);
			BufferedReader sysbr = new BufferedReader(new InputStreamReader(System.in));
			InputStream is = client.getInputStream();
			OutputStream os = client.getOutputStream();	
			
			
			while (true) {
				//发送信息
				String sendMessage = getSendMessage(sysbr);
				os.write(sendMessage.getBytes("UTF-8"));
				
				//接收信息
				byte[] bytes = new byte[1024];
				int len = 0;
				StringBuffer sb = new StringBuffer();
				String str ="";
				while ((len = is.read(bytes)) != -1) {
					str = new String(bytes, 0, len, "UTF-8");
					if (str.equals("sending message is finished")) {
						break;
					}
					sb.append(new String(bytes, 0, len, "UTF-8"));
				}
				String receiveMessage = "";
				receiveMessage = sb.toString();
				System.out.println(receiveMessage);
				
				
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getSendMessage(BufferedReader sysbr) {
		StringBuffer sb = new StringBuffer();
		String str = "";
		try {
			do {
				str = sysbr.readLine();
				sb.append(str);
			} while(!str.matches(".*?;"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	

}
