package com.CmdConsole.ZkSrvCommand;

import com.CmdConsole.IHandlerCommands;
import com.ZkServer.ZkServerConfiguration;
import com.ZkServer.ZkServerManager;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "create", commandDescription = "Create node for new zkserver.")
public class CreateCommand implements IHandlerCommands{
	@Parameter(names = "-pid", description = "pid for new zkserver (required)", arity = 1)//, required = true)
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
	//public JCommander jc = new JCommander();
	
	// ???????? void
	public  CreateCommand (ZkServerManager zkServerManager){//, JCommander jc){
		this.zkServerManager = zkServerManager;
		//this.jc = jc;
	}

	
	public void call(String[] args) throws Exception {


//		if (help){
//			jc.usage("create");
//		} else {
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
//		}
//		ip = ""; pid = ""; port_clients = ""; active = true; help = false;
	}
		
}
	
	


