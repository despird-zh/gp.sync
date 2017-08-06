package com.gp.sync.web.model;

public class SyncNoticeMessage {
	
	private String categroy;
	
	private String from;
	
	private String to;
	
	private String highWatermark;

	public String getCategroy() {
		return categroy;
	}

	public void setCategroy(String categroy) {
		this.categroy = categroy;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getHighWatermark() {
		return highWatermark;
	}

	public void setHighWatermark(String highWatermark) {
		this.highWatermark = highWatermark;
	}
	
	
}
