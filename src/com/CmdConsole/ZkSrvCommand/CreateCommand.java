package com.CmdConsole.ZkSrvCommand;

import java.io.StringWriter;

import com.CmdConsole.IHandlerCommands;
import com.ZkServer.ZkServerConfiguration;
import com.ZkServer.ZkServerManager;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "create", commandDescription = "Create node for new zkserver.")
public class CreateCommand implements IHandlerCommands{
	@Parameter(names = "-pid", description = "pid for new zkserver (required)", arity = 1, required = true)
	String pid;
	
	@Parameter(names = "-ip", description = "ip for new zkserver", arity = 1, required = false)
	String ip="";
	
	@Parameter(names = "-active", description = "active status for new zkserver (false or true)",  arity = 1, required = false)
	Boolean active=true;
	
	@Parameter(names = "-port_clients", description = "clients port new zkserver", arity = 1, required = false)
	String port_clients="";
	
	@Parameter(names = "-help", description = "get help about create", help = true)
	private boolean help;
	
	public ZkServerManager zkServerManager;
		
	public  CreateCommand (ZkServerManager zkServerManager){
		this.zkServerManager = zkServerManager;
	}
	
	public void call(String[] args, StringWriter sw) throws Exception {

			ZkServerConfiguration ct = new ZkServerConfiguration();
			ct.setIp(ip);
			ct.setPid(pid);
			ct.setActive(active);
			ct.setPort_clients(port_clients);
			try {
				zkServerManager.addZkSrv(ct);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
	
	


