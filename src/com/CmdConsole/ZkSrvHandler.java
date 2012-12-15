package com.CmdConsole;

import java.util.HashMap;
import java.util.Map;

import com.CmdConsole.ZkSrvCommand.CreateCommand;
import com.ZkServer.ZkServerManager;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterDescription;

public class ZkSrvHandler implements IHandlerCommands{
	private ZkServerManager zkServerManager;
	private JCommander jc;
	private Map<String, IHandlerCommands> commands = new HashMap<String, IHandlerCommands>();

	public  ZkSrvHandler (ZkServerManager zkServerManager){
		this.zkServerManager = zkServerManager;
		//this.jc = jc;
		JCommander jc = new JCommander();
		
		CreateCommand cc = new CreateCommand(zkServerManager);//, jc);
//		PrintCommand cp = new PrintCommand(zkServerManager);
//		DeleteCommand cd = new DeleteCommand(zkServerManager);
//		UpdateCommand cu = new UpdateCommand(zkServerManager);
//		
		commands.put("create", cc);
		jc.addCommand("create",cc);
		
//		commands.put("print", cp);
//		jc.addCommand("print",cp);
//		
//		commands.put("update", cu);
//		jc.addCommand("update",cu);
//		
//		commands.put("delete", cd);
//		jc.addCommand("delete",cd);
	}

	public void call(String[] args) throws Exception {
//		String subcommand = args[0];
//		String[] params = Arrays.copyOfRange(args, 1, args.length);
//		System.out.println(jc);
		
		Map<String, JCommander> com = jc.getCommands();
		System.out.println(com);
		
//		String name = jc.getParsedCommand();
//		System.out.println(name);
		
		jc.parse(args);
		
		String name = jc.getParsedCommand();
		System.out.println(name);
		ParameterDescription par = jc.getMainParameter();
		
		System.out.println(par);
		
		commands.get(name).call(args);
		System.out.println("done");
	}

}
