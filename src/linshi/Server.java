package linshi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//服务端
public class Server {

	Map<Integer, User> map = new HashMap<Integer,User>();
	
	public static void main(String[] args) throws Exception {
		System.out.println("服务端开始运行。。。");
		new Server().start();
	}

	public void start() throws Exception {
		ServerSocket ss = new ServerSocket(7777);
		
		while(true){
			Socket s = ss.accept();
			DataInputStream dis = new DataInputStream(s.getInputStream());
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			System.out.println("客户端dos："+dos.hashCode());
			User u = new User();
			u.setS(s);
			u.setDis(dis);
			u.setDos(dos);
			u.setMap(map);
			Thread.sleep(2000);
			new Thread(u).start();
		}
	}

}
