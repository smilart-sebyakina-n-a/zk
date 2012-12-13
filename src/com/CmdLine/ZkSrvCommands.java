package com.CmdLine;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import com.ZkServer.ZkServerConfiguration;
import com.ZkServer.ZkServerManager;
import com.ZkServer.ZkSrvEnumerationEntry;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.CmdLine.ICommandCallback;

public class ZkSrvCommands implements ICommandCallback {

	ZkServerManager zkServerManager;

	public  ZkSrvCommands (ZkServerManager zkServerManager){
		this.zkServerManager = zkServerManager;
	}

	@Override
	public void invoke(String[] args, StringWriter output) throws Exception {
		String subcommand = args[0];
		String[] params = Arrays.copyOfRange(args, 1, args.length);

		if (subcommand.equals("create")) {
			@Parameters(commandNames = "create", commandDescription = "Create node for new zkserver.")
			class CreateZkSrv {
				
				@Parameter(names = "-pid", description = "pid for new zkserver (required)", arity = 1, required = true)
				String pid;
				
				@Parameter(names = "-ip", description = "ip for new zkserver", arity = 1, required = false)
				String ip;
				
				@Parameter(names = "-active", description = "active status for new zkserver (false or true)",  arity = 1, required = false)
				Boolean active ;
				
				@Parameter(names = "-port_clients", description = "clients port new zkserver", arity = 1, required = false)
				String port_clients;
				
				@Parameter(names = "-help", description = "get help about create", help = true)
				private boolean help;

			}

			CreateZkSrv cc = new CreateZkSrv();

			JCommander jk = new JCommander(cc, params);
			jk.addCommand("create", cc);
			
			if (cc.help){
				jk.usage("create");
			} else {
				ZkServerConfiguration ct = new ZkServerConfiguration();
				ct.setIp(cc.ip);
				ct.setPid(cc.pid);
				ct.setActive(cc.active);
				ct.setPort_clients(cc.port_clients);
				zkServerManager.addZkSrv(ct);
			}
		} else if (subcommand.equals("delete")) {
			class DeleteZkSrv {
				@Parameter(names = "-pid", arity = 1, required = true)
				String pid;
			}
			
			DeleteZkSrv cc = new DeleteZkSrv();
			
			new JCommander(cc, params);
			
			zkServerManager.removeZkSrv(cc.pid);
		} else if (subcommand.equals("enumerate")) {
			Collection<ZkSrvEnumerationEntry> list = zkServerManager.enumerate();
			for (ZkSrvEnumerationEntry cnf : list) {
				System.out.print(cnf.configuration.pid + " ");
			}
		} else if (subcommand.equals("print")) {
				zkServerManager.printZkSrv();
		} else if (subcommand.equals("update")) {
				class UpdateZkSrv {
				
				@Parameter(names = "-srv", arity = 1, required = true)
				String srv;
				
				@Parameter(names = "-pid", arity = 1, required = false)
				String pid;
				
				@Parameter(names = "-ip", arity = 1, required = false)
				String ip;
				
				@Parameter(names = "-active", arity = 1, required = false)
				Boolean active ;
				
				@Parameter(names = "-port_clients", arity = 1, required = false)
				String port_clients;
				
			}

			UpdateZkSrv cc = new UpdateZkSrv();

			new JCommander(cc, params);

			Collection<ZkSrvEnumerationEntry> list = zkServerManager.enumerate();

			for (ZkSrvEnumerationEntry cnf : list) {
				if (cnf.node.equals(cc.srv)){
					ZkServerConfiguration ct = cnf.configuration;
					if (cc.pid != null) {ct.setPid(cc.pid);};
					if (cc.ip != null) {ct.setIp(cc.ip);};
					if (cc.active != null) {ct.setActive(cc.active);};
					if (cc.port_clients != null) {ct.setPort_clients(cc.port_clients);};
					zkServerManager.updateZkSrv(cnf.node, ct, cnf.version);
					break;
				}
			}
			
		} else
			throw new RuntimeException();
	}

}
