package Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import java.util.List;



public class ServerThread extends Thread{

	public static List<User> user=new ArrayList<User>();
	Compare c=new Compare();
	public Socket s;
	InputStream is=null;
	InputStream is1=null;
	public ServerThread(Socket s){
		this.s=s;
	}
	@Override
	public void run() {
		try {
			//接收客户端发来的消息
			is = s.getInputStream();
			byte b[]=new byte[1024];
			is.read(b);		
			String str=new String(b);
			
			if(c.compare(user, str)){
				for (Socket so : Server.sockets) {
					try{
						OutputStream o=s.getOutputStream();
						String outStr="服务器说：登陆成功";
						o.write(outStr.getBytes());
						o.flush();
						System.out.println();
						}catch(Exception e){
							Server.sockets.remove(so);
						}
					}
			}else{
				OutputStream o=s.getOutputStream();
				String outStr="服务器说：登陆失败,已经给你自动注册";
				o.write(outStr.getBytes());
				o.flush();
				user.add(new User(str,s.getLocalSocketAddress()));
				//可以试试user里有几个了
				for (User c : user) {
					System.out.println(c.getName()+c.getIp());
				}
			}
			while(true){
				
				is1 = s.getInputStream();
				//这里有改动：b
				byte b1[]=new byte[1024];
				is.read(b1);		
				String str1=new String(b1);
				//user.add(new User(str,s.getLocalSocketAddress()));
//				System.out.println(user.size());		
				//这有问题
				for (Socket so : Server.sockets) {
					try{
						OutputStream o=so.getOutputStream();
						
						o.write(str1.getBytes());
						o.flush();
						}catch(Exception e){
							Server.sockets.remove(so);
						}
					}
//				for(User us:user){
//					if(s.getLocalSocketAddress()==us.getIp()){
//						System.out.println(us.getName()+"说"+str1);
//					}
//				}	
				//System.out.println(s.getRemoteSocketAddress()+":"+str);		
				}			
			}
		 catch (IOException e) {
			e.printStackTrace();
		}
	}
}
