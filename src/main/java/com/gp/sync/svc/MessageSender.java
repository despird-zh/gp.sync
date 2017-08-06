package com.gp.sync.svc;

import com.gp.sync.web.model.SyncNoticeMessage;

public interface MessageSender {

	public void sendEventToClient(SyncNoticeMessage event,String sessionId);
	
}
