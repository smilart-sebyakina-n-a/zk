package com.ZkServer;

import com.google.gson.Gson;

public class ValueObject {
	protected static final Gson gson = new Gson();
	
	public final byte[] toJson() {
		return gson.toJson(this).getBytes();
	}
}