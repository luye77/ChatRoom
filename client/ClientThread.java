package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class ClientThread extends Thread{
	private Socket s;
	public ClientThread(Socket s){
		this.s=s;
	}
	@Override
	public void run() {
		try{
			while(true){
				InputStream is=s.getInputStream();
				byte[] b=new byte[1024];
				is.read(b);
				String str=new String(b);
				System.out.print(str.trim());
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		super.run();
	}
}
