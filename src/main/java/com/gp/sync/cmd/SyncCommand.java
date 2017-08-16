package com.gp.sync.cmd;

public enum SyncCommand {
	
	WGROUP(1,"b");
	
	public static final int CMD_TYPE_DATA = 1;
	public static final int CMD_TYPE_CONTROL = 2;
	
	public final int type;
	
	public final String command;
	
	private SyncCommand(int type, String command) {
		this.type = type;
		this.command = command;
	}
}
