package com.ZkServer;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import com.ZkServer.ZkServerManager;

public class ZkSrvWatcher implements Watcher{
	
	private String node;
	
	private ZooKeeper zk;
	
	public ZkSrvWatcher (ZooKeeper zk, String node){
		this.node = node; 
		this.zk = zk;
	}
    @Override
	public void process(WatchedEvent event) {
    	if (event.getType() == EventType.NodeDataChanged) { 
			System.out.println(node + " was changed.");
		}
		try {
			zk.getData(ZkServerManager.zkSrvPath(node), this, null);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
