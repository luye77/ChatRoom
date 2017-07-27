package Server;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;



public class Server {

	public static List<Socket> sockets=new ArrayList<Socket>();
	static ArrayList<Future<String>> results=null;
	static String str=null;
	static Socket s=null;
	static OutputStream o=null;
	static int i=0;
	public static void main(String[] args) throws IOException {

		ServerSocket ss=new ServerSocket(30002);
		while(true){
			
			//循环监听客户端
			s=ss.accept();
			sockets.add(s);
			//提示客户端登录
			OutputStream o=s.getOutputStream();
			String outStr="服务器：请输入姓名";
			o.write(outStr.getBytes());
			o.flush();
			//分配给这个客户端一个线程
			//用线程池接收返回值
//			ExecutorService executor = Executors.newCachedThreadPool();
//			ServerCall sc=new ServerCall(s);
//			Future<String> result = executor.submit(sc); 
//			results.add(result);
//			System.out.println(results.size());
//			for (Future<String> string : results) {
//				try {
//					str=string.get().toString();
//					o=s.getOutputStream();
//					o.write(str.getBytes());
//					o.flush();	
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				}
//			}
			NewServerThread ns=new NewServerThread(s);
			ns.start();
			
		}	
	}
}
