package com.CmdLine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import com.ZkServer.ZkServerManager;

import jline.console.ConsoleReader;

@Singleton
public class Console extends Thread {
	private Map<String, ICommandCallback> commands = new HashMap<String, ICommandCallback>();
	
	@PostConstruct
	public void postConstruct() throws IOException {
		final ConsoleReader reader = new ConsoleReader();
		
		ZkServerManager zk = null;
		try {
			zk = new ZkServerManager();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		commands.put("zksrv", new ZkSrvCommands(zk));
		
		reader.setPrompt("smilart> ");
		reader.setHistoryEnabled(true);

		Runnable cr = new Runnable() {
			@Override
			public void run() {
				String line;

				try {
					while ((line = reader.readLine()) != null) {
						if (line.length() == 0 || line.trim().equals("quit")){
							break;
						}
						String parts[] = line.split(" ");

						if (parts.length > 0 && parts[0].length() > 0) {
							String command = parts[0];

							ICommandCallback callback = commands.get(command);
							if (callback != null) {
								StringWriter sw = new StringWriter();

								try {
									callback.invoke(Arrays.copyOfRange(parts, 1, parts.length), sw);
								} catch (Exception ex) {
									ex.printStackTrace();
									reader.println("command error: " + ex.getMessage());
								}

								reader.println(sw.toString());
							} else {
								reader.println("unknown command: " + command);
							}
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		};

		Thread thread = new Thread(cr, "Console");
		thread.setDaemon(true);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void registerCommand(String command, ICommandCallback callback) {
		commands.put(command, callback);
	}
	
	public static void main(String [] args){
		Console c = new Console();
		try {
			c.postConstruct();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
