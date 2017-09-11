package com.gp.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.disruptor.EventHooker;
import com.gp.disruptor.EventPayload;
import com.gp.disruptor.EventType;
import com.gp.exception.RingEventException;

public class SyncEventHooker extends EventHooker<SyncEventLoad>{

	public static Logger LOGGER = LoggerFactory.getLogger(SyncEventHooker.class);
	
	public SyncEventHooker() {
		this(EventType.SYNC);
	}
	
	public SyncEventHooker(EventType eventType) {
		super(eventType);
	}

	@Override
	public void processPayload(EventPayload payload) throws RingEventException {
		
		SyncEventLoad data = (SyncEventLoad) payload;
		
		LOGGER.debug("event sync: {}", data.getData());
	}

}
