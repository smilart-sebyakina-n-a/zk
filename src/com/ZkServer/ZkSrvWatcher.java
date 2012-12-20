package com.ZkServer;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

public class ZkSrvWatcher implements Watcher{
	
	private String nodeId;
	
	public ZkSrvWatcher (String nodeId){
		this.nodeId = nodeId; 
	}

	public void process(WatchedEvent event) {
		if (event.getType() == EventType.NodeDataChanged) { 
			System.out.println(nodeId.concat(" was changed."));
		}
		
	}

}
