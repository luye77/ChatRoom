package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ServerCall implements Callable<String>{
	public static List<Socket> sockets=new ArrayList<Socket>();
	User us=new User();
	public static List<User> user=new ArrayList<User>();
	Compare c=new Compare();
	public Socket s;
	InputStream is=null;
	public String str2=null;
	public ServerCall(Socket s){
		this.s=s;
	}
	@Override
	public String call() {
		try {
				//接收客户端发来的消息
				is = s.getInputStream();
				byte b[]=new byte[1024];
				is.read(b);		
				String str=new String(b);
				//判断user中是否包含客户姓名，也可以用contains()方法
				if(c.compare(user, str)){
					try{
						OutputStream o=s.getOutputStream();
						String outStr="服务器说：登陆成功";
						o.write(outStr.getBytes());
						o.flush();
						System.out.println();
					}catch(Exception e){
						Server.sockets.remove(s);
					}	
				}else{
					//如果登录失败就添加到user集合里
					OutputStream o=s.getOutputStream();
					String outStr="服务器：登陆失败,已经给你自动注册";
					o.write(outStr.getBytes());
					o.flush();
					//也可以用这种方法add user对象
					us.setIp(s.getLocalSocketAddress());
					us.setName(str);
					user.add(us);
					//user.add(new User(str,s.getLocalSocketAddress()));	
				}
				System.out.println("进入聊天室");
				while(true){
					//循环接收客户端消息
					is = s.getInputStream();
					byte b1[]=new byte[1024];
					is.read(b1);		
					String str1=new String(b1);
					for (User c : user) {					
						//****为什么用==不行，因为比较的是字符串的值
						if(s.getLocalSocketAddress().equals(c.getIp())){		
							System.out.print(c.getName().trim());
							System.out.println("说："+str1.trim());
							str2=new String(c.getName()+":"+str1);						}
					}
				}	
			}catch(IOException e){
				Server.sockets.remove(s);
				e.printStackTrace();
			}
			return str2;
	}
}
