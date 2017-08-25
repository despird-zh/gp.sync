package com.gp.sync.model;

/**
 * the distributed nodes use push structure to send information to center node or global node.
 * so the payload of content could be any operation to be synchronized.
 * 
 * @author admin
 * @version 0.1 2017-9-10
 * 
 **/
public class SyncPushMessage {

	private String type;
	
	private String node;
	
	private String traceCode;
	
	private Object payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
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
