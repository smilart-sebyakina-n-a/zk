package com.ZkServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZkServerMain extends Thread {
	
	private volatile Thread workingThread;

	public void run(){
		workingThread = Thread.currentThread();
		try {
			ZkServerManager ZkServer = new ZkServerManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!isInterrupted()){
			//System.out.println("working");
		}
		//ZkServer.close();
	}

	public void doStop()  {  
		if (workingThread != null){
			workingThread.interrupt();  
			System.out.println("exit.....");
		}
	}  

	public static void main(String[] args) {
		ZkServerMain thread = new ZkServerMain();
		thread.start();
		BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
		try {
			String a = d.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		thread.doStop();

	}

}
