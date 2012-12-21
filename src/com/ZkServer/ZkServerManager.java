package com.ZkServer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jline.console.ConsoleReader;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class ZkServerManager implements IZkServerManager{

	private static final Logger log = (Logger) LoggerFactory.getLogger(ZkServerManager.class);
	// в мс
	private static final int TIMEOUT = 3000;
	
	private static final String Path_to_Zk_srv = "/smilart/zk_srv";

	private ZooKeeper zk;
	
	public static ConsoleReader reader;
	
//	private ZkServerConfiguration zk_srv;
	
//	private ArrayList<ZkServerConfiguration> listZkSrv;
	
	private Map<String, ZkSrvWatcher> entries = new HashMap<String, ZkSrvWatcher>();
	
	Watcher watcher = new Watcher() {
         @Override
         final public void process(WatchedEvent event) {
                 if (event.getType() == EventType.NodeChildrenChanged || event.getType() == EventType.NodeDataChanged)
					try {
						infoAboutChanges();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
                         //updateCameras(this);
                 try {
                 	 zk.getChildren(Path_to_Zk_srv, watcher);
				} catch (Exception e) {
					e.printStackTrace();
				} 
         }
	 };
	 
	 class ZkSrvWatcher implements Watcher{
			private String node;
//			private ZooKeeper zk;
			public ZkSrvWatcher (String node){
				this.node = node; 
//				this.zk = zk;
			}
		    @Override
			public void process(WatchedEvent event) {
		    	if (event.getType() == EventType.NodeDataChanged) { 
		    		try {
						println(node + " was changed.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					zk.getData(ZkServerManager.zkSrvPath(node), this, null);
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	 public ZkServerManager() throws Exception {
		zk = new ZooKeeper("localhost:2181", TIMEOUT, null);
		if (zk.exists(Path_to_Zk_srv, false) == null){
			String[] nodes_to_zk_srv = Path_to_Zk_srv.split("/");
			String path_to_node = "";
			for (int i=1; i<nodes_to_zk_srv.length; i++){
				path_to_node = path_to_node.concat("/" + nodes_to_zk_srv[i]);
				if (zk.exists(path_to_node, false) == null){
					zk.create(path_to_node, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.info("Created node " + path_to_node);
				}
			}
		}
		// может еще что-то нужно будет сделать
		zk.getChildren(Path_to_Zk_srv, watcher);
		this.entries = underСontrol();
		this.reader = null;
	}
	 
	 private Map<String, ZkSrvWatcher> underСontrol () throws KeeperException, InterruptedException{
		 Map<String, ZkSrvWatcher> node_watcher = new HashMap<String, ZkSrvWatcher>();
		 for (String node : zk.getChildren(Path_to_Zk_srv, false)){
			 ZkSrvWatcher zkSrvWatcher = new ZkSrvWatcher(node);
			 node_watcher.put(node, zkSrvWatcher);
			 zk.getData(zkSrvPath(node), zkSrvWatcher, null);
		 }
		 return  node_watcher;
	 }

	 public void setReader (ConsoleReader reader){
		 this.reader = reader;
	 }
	 
	 public boolean readerIsNull(){
		 if (reader == null){
			 return true;
		 } else {
			 return false;
		 }
	 }
	
	public void println(String message) throws IOException {
		if (readerIsNull()) {
			System.out.println(message);
		} else {
			reader.print(message);
			reader.accept();
		}
	}

	private void print(String message) throws IOException {
		if (readerIsNull()) {
			System.out.print(message);
		} else {
			reader.print(message);
		}
	}
	 
	public static String zkSrvPath (String node){
		return Path_to_Zk_srv + "/" + node;
	}
	
	public ArrayList<ZkSrvEnumerationEntry> enumerate() throws Exception {
		ArrayList<ZkSrvEnumerationEntry> list = new ArrayList<ZkSrvEnumerationEntry>();
		for (String node : zk.getChildren(Path_to_Zk_srv, false)){
			Stat stat = new Stat();
			byte[] bytes = zk.getData(zkSrvPath(node), null, stat);
			ZkServerConfiguration configuration = ZkServerConfiguration.fromJson(bytes);
			ZkSrvEnumerationEntry zkSrv = new ZkSrvEnumerationEntry(configuration,node,stat.getVersion());
			list.add(zkSrv);
		}
		return list;
	}
	
	public void printZkSrv () throws Exception {
		for (String node : zk.getChildren(Path_to_Zk_srv, false)){
			print(node);
			Stat stat = new Stat();
			byte[] bytes = zk.getData(zkSrvPath(node), null, stat);
			println(new String(bytes));
		}
	}
	
	public void infoAboutChanges() throws Exception{
		println("Zookeeper Watcher to  register changes in ".concat(Path_to_Zk_srv));
		println("");
		printZkSrv();
		if (!readerIsNull()) {
				println(reader.getPrompt());
		}
	}
		
	public ZkSrvEnumerationEntry findByPath (String node) throws Exception {
		for (ZkSrvEnumerationEntry zkSrv : enumerate()) {
			if (zkSrv.node.equals(node))
				return zkSrv;
		}
		return null;
	}
	
	public ZkSrvEnumerationEntry findByPid(String pid) throws Exception {
		for (ZkSrvEnumerationEntry zkSrv : enumerate()) {
			if (zkSrv.configuration.pid.equals(pid))
				return zkSrv;
		}
		return null;
	}
	
	public String addZkSrv(ZkServerConfiguration zkSrv) throws Exception {
		if (findByPid(zkSrv.pid) == null){
			byte[] bytes = zkSrv.toJson();
			String nameNode = zkSrvPath("zk");  
			
			String rez = zk.create(nameNode, bytes, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
			return rez;
		}
		return null;
	}
	
	public void removeZkSrv(String pid) throws Exception {
		ZkSrvEnumerationEntry ptr = findByPid(pid); 
		if (ptr != null){
			zk.delete(zkSrvPath(ptr.node), ptr.version);
		}
	}
	
	public void updateZkSrv(String node, ZkServerConfiguration zkSrv, int version) throws Exception {
		if (zk.exists(zkSrvPath(node), false) != null){
			ZkSrvEnumerationEntry c = findByPath(node);
			if ((!c.configuration.pid.equals(zkSrv.pid) && findByPid(zkSrv.pid) == null)  || c.configuration.pid.equals(zkSrv.pid)) {
				zk.setData(zkSrvPath(node), zkSrv.toJson(), version);
			}
		}
	}
	
	public void close() throws Exception {
		zk.close();
	}
	
	public static void main(String[] args) throws Exception{
		try{
			ZkServerManager ZkServer = new ZkServerManager();
			//ZkServer.findByPath("/smilart/zk_srv");

//			ZkServerConfiguration zkTest = new ZkServerConfiguration();
//			zkTest.pid = "6";
//			zkTest.ip =  "192.168.0.103";
//			zkTest.port_clients = "2181";
			
			StringWriter sw = new StringWriter();
			ZkServer.printZkSrv();
			System.out.println(sw.toString());
			sw.close();
			
			//ZkServer.addZkSrv(zkTest);
			//ZkServer.removeZkSrv("4");
			/*while(true){
                
            }*/
			//ZkServer.printZkSrv();
			//ZkServer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

