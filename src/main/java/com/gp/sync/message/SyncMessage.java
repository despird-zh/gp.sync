package com.gp.sync.message;

public abstract class SyncMessage {

	private SyncType type;
	
	private String traceCode;
	
	private Object payload;

	public SyncType getType() {
		return type;
	}

	public void setType(SyncType type) {
		this.type = type;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

}
