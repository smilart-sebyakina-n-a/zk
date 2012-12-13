package com.ZkServer;

public class ZkSrvEnumerationEntry {
	
	public final ZkServerConfiguration configuration;

	public final String node;
		
	public final int version;
	
	public ZkSrvEnumerationEntry(ZkServerConfiguration configuration, String node, int version) {
		this.configuration = configuration;
		this.node = node;
		this.version = version;
	}
}
