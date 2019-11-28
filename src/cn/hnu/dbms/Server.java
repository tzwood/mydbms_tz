package cn.hnu.dbms;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;

		try {
			server = new ServerSocket(9999);

			while (true) {
				Socket socket = server.accept();
				//为新来的客服端服务
				new ServesClient(socket);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

class ServesClient extends Thread {
	private Socket socket;
	private InputStream is = null;
	private OutputStream os = null;

	public ServesClient(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.is = socket.getInputStream();
		this.os = socket.getOutputStream();
		this.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {
			while (true) {
				// 打印输入信息
				byte[] bytes = new byte[1024];
				int len = 0;
				StringBuffer sb = new StringBuffer();
				String str = "";

				while ((len = is.read(bytes)) != -1) {
					str = new String(bytes, 0, len, "UTF-8");
					sb.append(str);
					if (str.matches(".*?;")) {
						break;
					}
				}

				String receiveMessage = "";
				receiveMessage = sb.toString();
				System.out.println("收到的信息为" + receiveMessage);

				// 发送数据
				String sendMessage = "";
				if (!receiveMessage.equals("")) {
					sendMessage = "已经收到信息" + receiveMessage;
				} else {
					sendMessage = "未收到相应的信息";
				}

				os.write(sendMessage.getBytes("UTF-8"));
				os.flush();
				os.write("sending message is finished".getBytes("UTF-8"));
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
