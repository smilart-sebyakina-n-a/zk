package com.CmdConsole;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HelpHandler implements IHandlerCommands {
	
	private HashMap<String, String> declarationCommands = new HashMap<String, String>();

	public HelpHandler (HashMap<String, String> declarationCommands){
		this.declarationCommands = declarationCommands;
	}
	
	public void call(String[] args) throws Exception {
		System.out.println("Basic commands:");
		Set<Map.Entry<String, String>> set = declarationCommands.entrySet();
		for (Map.Entry<String, String> me : set) {
		      System.out.print(me.getKey() + ": ");
		      System.out.println(me.getValue());
		    }
		System.out.println("А more detailed use: command help.");
		
	}

}
