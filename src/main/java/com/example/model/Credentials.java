package com.example.model;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix="info.my")
public class Credentials {
	
	private String userName;
	
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Credentials() {
		super();
	}

	public Credentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Credentials [userName=" + userName + ", password=" + password + "]";
	}

}
