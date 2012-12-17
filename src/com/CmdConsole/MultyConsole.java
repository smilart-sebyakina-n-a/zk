package com.CmdConsole;

import java.io.IOException;
import java.util.HashMap;

import jline.console.ConsoleReader;

import com.CmdConsole.IHandlerCommands;
import com.CmdConsole.ZkSrvHandler;
import com.ZkServer.ZkServerManager;

public class MultyConsole extends Thread{
	private HashMap<String, IHandlerCommands> listCommands = new HashMap<String, IHandlerCommands>();
	

	public void startConsole() throws IOException {

		final ConsoleReader reader = new ConsoleReader();
		
		ZkServerManager zk = null;
		try {
			zk = new ZkServerManager();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		registerCommand("zksrv", new ZkSrvHandler(zk));
		
		reader.setPrompt("smilart> ");
		reader.setHistoryEnabled(true);
		
		RunCommands rc = new RunCommands(reader, listCommands);
		Thread thread = new Thread(rc, "Console");

		thread.start();
	}
	
	public void registerCommand(String command, IHandlerCommands handler) {
		listCommands.put(command, handler);
	}
	
	public static void main(String [] args){
		try {
			MultyConsole c = new MultyConsole();
			c.startConsole();
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
}
