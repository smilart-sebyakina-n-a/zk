package com.CmdConsole.ZkSrvCommand;

import java.io.StringWriter;
import java.util.Collection;

import com.CmdConsole.IHandlerCommands;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ZkServer.ZkServerManager;

import com.ZkServer.ZkServerConfiguration;
import com.ZkServer.ZkSrvEnumerationEntry;

@Parameters(commandNames = "update", commandDescription = "Update node for zkserver by name.")
public class UpdateCommand implements IHandlerCommands {

	@Parameter(names = "-srv", description = "name zkserver (required)", arity = 1, required = true)
	String srv;
	
	@Parameter(names = "-pid", description = "new pid for zkserver (required)", arity = 1, required = false)
	String pid;
	
	@Parameter(names = "-ip", description = "new ip for zkserver", arity = 1, required = false)
	String ip="";
	
	@Parameter(names = "-active", description = "new active status for zkserver (false or true)",  arity = 1, required = false)
	Boolean active=true;
	
	@Parameter(names = "-port_clients", description = "new clients port zkserver", arity = 1, required = false)
	String port_clients="";
	
	@Parameter(names = "-help", description = "get help about update", help = true)
	private boolean help;
	
	private ZkServerManager zkServerManager; 
	
	public  UpdateCommand (ZkServerManager zkServerManager){
		this.zkServerManager = zkServerManager;
	}
	
	@Override
	public void call(String[] args, StringWriter sw) throws Exception {
        
		Collection<ZkSrvEnumerationEntry> list = null;
        try {
                list = zkServerManager.enumerate();
        } catch (Exception e1) {
                e1.printStackTrace();
        }

        for (ZkSrvEnumerationEntry cnf : list) {
                if (cnf.node.equals(srv)){
                        ZkServerConfiguration ct = cnf.configuration;
                        if (pid != null) {ct.setPid(pid);};
                        if (ip != null) {ct.setIp(ip);};
                        if (active != null) {ct.setActive(active);};
                        if (port_clients != null) {ct.setPort_clients(port_clients);};
                        try {
                        	zkServerManager.setWriter(sw);    
                        	zkServerManager.updateZkSrv(cnf.node, ct, cnf.version);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                        break;
                }
        }

	}
}

