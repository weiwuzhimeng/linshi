package linshi;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

//代表一个app终端
public class User implements Runnable {

	int id;
	Socket s; // 此app的连接
	private DataInputStream dis = null; // 此app的输入
	private DataOutputStream dos = null; // 此app的输出
	Map<Integer, User> map = null; // 用户集
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public Map<Integer, User> getMap() {
		return map;
	}

	public void setMap(Map<Integer, User> map) {
		this.map = map;
	}

	@Override
	public void run(){
		System.out.println("此app的map是："+map.hashCode());
		while (true) {
			try {
				int msg = dis.readInt();// 得到这个app发来的消息
				if(1<=msg && msg<=10){ //[1,10]则建立连接 1、2、3
					this.setId(msg);
					map.put(msg, this);
					System.out.println("此时map集合中的元素是："+map);
					System.out.println("这是连接注册模块"+msg+"：已经注册");
				}else if(21<=msg && msg<=30){ //[20,30]发送消息 21、22、23
					int senderId = this.getId(); //发送者
					//int senderId = msg-18; //发送者
					System.out.println("app发送者id:"+senderId);
					int accepterId = msg-20; //接收者
					System.out.println("app接收者id:"+accepterId);
					User user = map.get(accepterId);
					if(user!=null){
						DataOutputStream dos1 = user.getDos();
						String str = senderId+"发送给"+accepterId;
						dos1.writeUTF(str);
						System.out.println("这是发送模块："+str);
					}else{
						System.out.println("user对象不存在");
					}
				}
			} catch (SocketException e) { //异常处理先后顺序？小在前？
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
