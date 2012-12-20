package com.CmdConsole.ZkSrvCommand;

import com.ZkServer.ZkServerManager;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "print", commandDescription = "Print node for zkserver.")
public class PrintCommand implements IZkSrvCommands {
	
	@Parameter(names = "-help", description = "get help about print", help = true)
	private boolean help;
	
	private ZkServerManager zkServerManager;  
	
	public PrintCommand (ZkServerManager zkServerManager){ 
		this.zkServerManager = zkServerManager;   
	}

	@Override
	public void call(String[] args) throws Exception {
        try {
            zkServerManager.printZkSrv();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
