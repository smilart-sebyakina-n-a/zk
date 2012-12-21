package com.ZkServer;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
	
	public static ArrayList<String> notEquals(ZkServerConfiguration a, ZkServerConfiguration b) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		ArrayList<String> diff = new ArrayList<String>();
		for (Field field : a.getClass().getFields()){
			Object aValue = field.get(a);
			Object bValue = field.get(b);
			if ( !aValue.equals(bValue)) {
				diff.add(field.getName());
			}
		}
		if (diff.size() != 0){
			return diff;
		} else{
			return null;
		}
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
