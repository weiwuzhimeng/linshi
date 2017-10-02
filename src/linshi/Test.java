package linshi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//测试
public class Test {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		new Test().start();
	}

	private void start() throws UnknownHostException, IOException, InterruptedException {
		System.out.println("客户端开始运行。。。");
		Client c1 = new Client(1);
		Client c2 = new Client(2);
		Client c3 = new Client(3);

		Thread.sleep(10000);
		
		new Thread(c1).start();
		new Thread(c2).start();
		new Thread(c3).start();
	}

	public class Client implements Runnable {
		Socket s;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		int id;
		
		public Client(int id) throws UnknownHostException, IOException {
			this.id = id;
			System.out.println("这里是客户端："+id);
			s = new Socket("192.168.1.180",7777);
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(id);
		}

		@Override
		public void run() {
			try {
				if(id==3){
					dos.writeInt(id+18);
				}
				if(id==1){
					String str = dis.readUTF();
					System.out.println("服务器返回给："+id+"   消息是："+str);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
