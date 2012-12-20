package com.CmdConsole;

import java.io.StringWriter;

import jline.console.ConsoleReader;

public interface IHandlerCommands {
	void call(String args[], ConsoleReader reader) throws Exception;
}
