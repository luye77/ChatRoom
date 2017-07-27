package Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class NewServerThread extends Thread{

	public static List<Socket> sockets=new ArrayList<Socket>();
	public static List<User> user=new ArrayList<User>();
	public ArrayList<String> str2=new ArrayList<String>();
	User us=new User();
	Compare c=new Compare();
	public Socket s;
	public InputStream is=null;
	public OutputStream o=null;
	public NewServerThread(Socket s){
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
				//判断user中是否包含客户姓名，也可以用contains()方法
				if(c.compare(user, str)){
					try{
						o=s.getOutputStream();
						String outStr="服务器说：登陆成功";
						o.write(outStr.getBytes());
						o.flush();
						System.out.println();
					}catch(Exception e){
						Server.sockets.remove(s);
					}	
				}else{
					//如果登录失败就添加到user集合里
					o=s.getOutputStream();
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
							str2.add(new String(c.getName()+":"+str1));
							System.out.print(c.getName().trim());
							System.out.println("说："+str1.trim());
						}
					}
					System.out.println(str2.size());
					//应该将子程序的值返回，存到list中，在进行操作
					//将接收的字符串再发送给客户端，这个可以把其他人说的话也发送出去吗	
					for (String string : str2) {
						o=s.getOutputStream();
						o.write(string.getBytes());
						o.flush();	
					}
				}
			}catch(IOException e){
				Server.sockets.remove(s);
				e.printStackTrace();
			}
	}
}
