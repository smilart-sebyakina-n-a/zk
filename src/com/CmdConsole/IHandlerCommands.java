package com.CmdConsole;

import java.io.StringWriter;

public interface IHandlerCommands {
	void call(String args[], StringWriter sw) throws Exception;
}
