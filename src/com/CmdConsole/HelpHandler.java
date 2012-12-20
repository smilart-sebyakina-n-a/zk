package com.CmdConsole;

import java.util.HashMap;

import jline.console.ConsoleReader;

public class HelpHandler implements IHandlerCommands {
	
	private HashMap<String, String> declarationCommands = new HashMap<String, String>();

	public HelpHandler (HashMap<String, String> declarationCommands){
		this.declarationCommands = declarationCommands;
	}
	
	public void call(String[] args, ConsoleReader reader) throws Exception {
		reader.println("Basic commands:");
		for (String key : declarationCommands.keySet()) {
			reader.println(key + ": " + declarationCommands.get(key));
		}
		reader.println("А more detailed use: command help.");
		
	}

}
