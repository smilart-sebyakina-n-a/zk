package com.CmdConsole;

import java.util.HashMap;
import java.util.Map;

import com.CmdConsole.ZkSrvCommand.*;
import com.ZkServer.ZkServerManager;
import com.beust.jcommander.JCommander;

public class ZkSrvHandler implements IHandlerCommands{
	private ZkServerManager zkServerManager;
	private JCommander jc;
	private Map<String, IHandlerCommands> listCommands = new HashMap<String, IHandlerCommands>();

	public  ZkSrvHandler (ZkServerManager zkServerManager){
		this.zkServerManager = zkServerManager;
	}

	private void registerCommand(String command, IHandlerCommands handler) {
		listCommands.put(command, handler);
		jc.addCommand(command, handler);
	}
	
	public void call(String[] args) throws Exception {

		jc = new JCommander();
		
		registerCommand("create", new CreateCommand(zkServerManager));
		registerCommand("delete", new DeleteCommand(zkServerManager));
		registerCommand("print",  new PrintCommand(zkServerManager));
		registerCommand("update", new UpdateCommand(zkServerManager));
		registerCommand("help",   new HelpCommand(zkServerManager));
		
		jc.parse(args);
		String name = jc.getParsedCommand();
		
		if (name.equals("help")) {
			jc.usage();
		} else {
			StringBuilder sb = new StringBuilder();
			for (String s: args){ sb.append(s).append(" ");}
			if (sb.indexOf("-help") != -1){
				jc.usage(name);
			} else {
			listCommands.get(name).call(args);
			}
		}
		
	}

}
