package com.gp.sync.svc;

import com.gp.sync.message.SyncNotifMessage;

public interface MessageSender {

	public void sendNotifToUser(SyncNotifMessage event,String sessionId);
	
	
	
}
