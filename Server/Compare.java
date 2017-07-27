package Server;


import java.util.List;

//可以改成contains方法
public class Compare {
	public boolean compare(List<User> list,String name){
		boolean flag=false;
		for(User us:list){
			if(name==us.getName()){
				flag=true;
			}else{
				flag=false;
			}
		}
		return flag;		
	}
}
