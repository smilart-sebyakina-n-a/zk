package com.ZkServer;
import java.util.Map;

public class ZkServerConfiguration extends ValueObject {

	public String pid;
	
	public String ip;
	
	public String port_clients;
	
	/*
	public String port_leader;
	
	public String port_follower;
	*/
		
	public boolean active;

	public static ZkServerConfiguration fromJson(byte[] bytes) {
		return gson.fromJson(new String(bytes), ZkServerConfiguration.class);
	}

	public boolean isActive() {
		return active;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setPort_clients(String port_clients) {
		this.port_clients = port_clients;
	}
	
	public String getPort_clients() {
		return  port_clients;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getIp() {
		return ip;
	}
	
	public String getPid() {
		return pid;
	}

}
