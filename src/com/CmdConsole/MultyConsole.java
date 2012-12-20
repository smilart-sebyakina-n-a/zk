package com.CmdConsole;

import java.io.IOException;
import java.util.HashMap;

import jline.console.ConsoleReader;

import com.CmdConsole.IHandlerCommands;
import com.CmdConsole.ZkSrvHandler;
import com.ZkServer.ZkServerManager;

public class MultyConsole extends Thread{
	private HashMap<String, IHandlerCommands> listCommands = new HashMap<String, IHandlerCommands>();
	private HashMap<String, String> declarationCommands = new HashMap<String, String>();
	

	public void startConsole() throws IOException {

		final ConsoleReader reader = new ConsoleReader(System.in, System.out);
		
		ZkServerManager zk = null;
		try {
			zk = new ZkServerManager();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		registerCommand("zksrv", new ZkSrvHandler(zk), "Management zookeeper servers.");
		registerCommand("help",  new HelpHandler(declarationCommands), "Output help.");
				
		reader.setPrompt("smilart> ");
		reader.setHistoryEnabled(true);
		
		RunCommands rc = new RunCommands(reader, listCommands);
		Thread thread = new Thread(rc, "Console");

		thread.start();
	}
	
	public void registerCommand(String command, IHandlerCommands handler, String declaration) {
		listCommands.put(command, handler);
		declarationCommands.put(command, declaration);
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
