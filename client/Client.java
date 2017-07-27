package client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws Exception, IOException {

		Socket s=new Socket("127.0.0.3",30002);
		System.out.println("客户端等待连接");
		InputStream is=s.getInputStream();
		byte[] b=new byte[1024];
		is.read(b);
		String str=new String(b);
		System.out.println(str.trim());
			
		while(true){
			Scanner sc=new Scanner(System.in);
			String name=sc.next();
			OutputStream os=s.getOutputStream();
			os.write(name.getBytes());		
			os.flush();
			//os.close();
			new ClientThread(s).start();
		}		
	}
}
