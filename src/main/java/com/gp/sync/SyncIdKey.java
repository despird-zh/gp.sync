package com.gp.sync;

import com.gp.info.Identifier;
import com.gp.info.InfoId;

public enum SyncIdKey implements Identifier{
	
	CEN_DIST("gp_center_dist", "dist_id", Long.class),
	CEN_MSG("gp_center_msgs", "msg_id", Long.class),
	CEN_RCV("gp_center_rcv", "rcv_id", Long.class),
	CEN_SND("gp_center_snd", "snd_id", Long.class),
	CEN_SOURCE("gp_center_sources", "node_id", Long.class),
	NODE_SOURCE("gp_node_sources", "node_id", Long.class),
	NODE_MSG_IN("gp_node_msg_in", "msg_id", Long.class),
	NODE_MSG_OUT("gp_node_msg_out", "msg_id", Long.class),
	NODE_PULL("gp_node_pull", "pull_id", Long.class),
	NODE_PUSH("gp_node_push", "push_id", Long.class),
	SYNC_OPT("gp_sync_opts", "opt_id", Long.class),
	;
	
	private final String schema;
	private final Class<?> clazz;
	private final String idColumn;
	
	private <T> SyncIdKey(String schema,String idColumn, Class<T> clazz){
		this.idColumn = idColumn;
		this.schema = schema;
		this.clazz = clazz;
	}
	
	@Override
	public String getTable() {
		
		return schema;
	}

	@Override
	public String getSchema() {
		
		return this.schema;
	}

	@Override
	public String getIdColumn() {

		return this.idColumn;
	}

	@Override
	public <T> InfoId<T> getInfoId(T sequence) {
		
		if(sequence == null || !this.clazz.equals(sequence.getClass()))
			throw new UnsupportedOperationException("Sequence type is not supported");
		
		return new InfoId<T>(this.getSchema(), this.idColumn, sequence);
	}
}
