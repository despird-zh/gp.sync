package gp.test.comm;

import java.util.Optional;

import com.gp.sync.message.SyncMessages;
import com.gp.sync.message.SyncPushMessage;

import gp.sync.gui.SyncTests;

public class SyncMessageTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SyncTests tests = new SyncTests();
		String pushMsg = tests.getTestData("/sync.push");
		
		SyncPushMessage msg = SyncMessages.parsePushMessage(Optional.ofNullable(pushMsg));
		
		System.out.println(msg.getPayload());
	}

}
