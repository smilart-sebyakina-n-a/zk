package com.CmdConsole;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HelpHandler implements IHandlerCommands {
	
	private HashMap<String, String> declarationCommands = new HashMap<String, String>();

	public HelpHandler (HashMap<String, String> declarationCommands){
		this.declarationCommands = declarationCommands;
	}
	
	public void call(String[] args, StringWriter sw) throws Exception {
		sw.write("Basic commands:" + "\n");
		for (String key : declarationCommands.keySet()) {
			sw.write(key + ": " + declarationCommands.get(key) + "\n");
		}
		sw.write("А more detailed use: command help." + "\n");
		
	}

}
