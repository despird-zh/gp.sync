package com.gp.sync.data;

/**
 * The notification is message sent from center node(global/ enterprise) to target nodes .<br>
 * It has 2 kinds content: the target node pull notification and the command aims on target node.<br>
 * 
 * @author diaogc 
 * @version 0.1 2017-5-6
 **/
public class SyncNotification {
	
	private String type;
	
	private String center;
	
	private String traceCode;
	
	private Object payload;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
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
