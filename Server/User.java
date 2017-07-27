package Server;
import java.net.SocketAddress;


public class User {

	private String name;
	private SocketAddress ip;
	public User(){}
	public User(String name,SocketAddress ip){
		this.ip=ip;
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SocketAddress getIp() {
		return ip;
	}
	public void setIp(SocketAddress ip) {
		this.ip = ip;
	}
}
