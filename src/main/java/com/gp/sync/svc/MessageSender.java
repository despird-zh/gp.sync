package com.gp.sync.svc;

import com.gp.sync.model.SyncNotifMessage;

public interface MessageSender {

	public void sendNotifToUser(SyncNotifMessage event,String sessionId);
	
	
	
}
