package com.CmdConsole;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import jline.console.ConsoleReader;

//import jline.ConsoleReader;

import com.CmdConsole.IHandlerCommands;
import com.beust.jcommander.JCommander;

public class RunCommands implements Runnable {
	
	public ConsoleReader reader;
	
	public HashMap<String, IHandlerCommands> listCommands;
	
	public RunCommands (ConsoleReader reader, HashMap<String, IHandlerCommands> listCommnds){
		this.reader = reader;
		System.out.println("this.reader = " + reader);
		this.listCommands = listCommnds;
	}
	
	public void run() {
		String line;

		try {
			System.out.println("reader = " + reader);
			line = reader.readLine();
			System.out.println("Read line:::" + line);
			while (line != null) {
				if (line.length() == 0 || line.trim().equals("quit")){
					break;
				}
				String parts[] = line.split(" ");

				if (parts.length > 0 && parts[0].length() > 0) {
					String command = parts[0];

					IHandlerCommands handler = listCommands.get(command);
					if (handler != null) {
//						StringWriter sw = new StringWriter();
//						JCommander jc = new JCommander();

						try {
							handler.call(Arrays.copyOfRange(parts, 1, parts.length));
						} catch (Exception ex) {
							ex.printStackTrace();
							reader.println("command error: " + ex.getMessage());
						}
//						reader.println(sw.toString());
					} else {
						reader.println("unknown command: " + command);
//						reader.printString("unknown command: " + command);
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}