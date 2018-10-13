package application;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String userName;
	private String pw;
	private List<String> serviceLines;
	
	public User(String userName, String pw) {
		this.userName = userName;
		this.pw = pw;
		this.serviceLines = new ArrayList<>();
		this.serviceLines.add("default service1");
		this.serviceLines.add("default service2");
		this.serviceLines.add("default service3");
	}
	
	public List<String> getUserInfo(){
		List<String> info = new ArrayList<>();
		info.add(userName);
		info.addAll(serviceLines);
		return info;
	}

}
