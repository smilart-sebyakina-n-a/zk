package com.CmdConsole.ZkSrvCommand;

import com.ZkServer.ZkServerManager;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "help", commandDescription = "List commands for zksrv.")
public class HelpCommand implements IZkSrvCommands  {
	
	private ZkServerManager zkServerManager;  
	
	public HelpCommand (ZkServerManager zkServerManager){ 
		this.zkServerManager = zkServerManager;   
	}
	@Override
	public void call(String[] args) throws Exception {
		
	}

}
