package com.CmdConsole.ZkSrvCommand;

import com.ZkServer.ZkServerManager;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "delete", commandDescription = "Delete node with zkserver by pid.")
public class DeleteCommand implements IZkSrvCommands {
	
	@Parameter(names = "-pid", description = "pid for deleted zkserver (required)", arity = 1, required = true)
	String pid;
	
	@Parameter(names = "-help", description = "get help about delete", help = true)
	private boolean help;
	
	private ZkServerManager zkServerManager;  
	
	public DeleteCommand (ZkServerManager zkServerManager){ 
		this.zkServerManager = zkServerManager;   
	}
	
	@Override
	public void call(String[] args) throws Exception {

        try {
            zkServerManager.removeZkSrv(pid);
        } catch (Exception e) {
                    e.printStackTrace();
        }
    }

}
