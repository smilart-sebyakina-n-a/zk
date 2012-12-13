package com.CmdLine;

import java.io.StringWriter;

public interface ICommandCallback {
	void invoke(String args[], StringWriter output) throws Exception;
}
