package com.CmdConsole.ZkSrvCommand;

import java.io.StringWriter;

import com.CmdConsole.IHandlerCommands;
import com.ZkServer.ZkServerManager;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "print", commandDescription = "Print node for zkserver.")
public class PrintCommand implements IHandlerCommands {
	
	@Parameter(names = "-help", description = "get help about print", help = true)
	private boolean help;
	
	private ZkServerManager zkServerManager;  
	
	public PrintCommand (ZkServerManager zkServerManager){ 
		this.zkServerManager = zkServerManager;   
	}

	@Override
	public void call(String[] args, StringWriter sw) throws Exception {
        try {
            zkServerManager.printZkSrv(sw);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
