package com.gp.sync.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gp.dao.BaseDAO;
import com.gp.sync.dao.info.CenterDistInfo;

public interface CenterDistDAO extends BaseDAO<CenterDistInfo>{

	public static RowMapper<CenterDistInfo> DistMapper = new RowMapper<CenterDistInfo>() {

		@Override
		public CenterDistInfo mapRow(ResultSet rs, int arg1) throws SQLException {
			CenterDistInfo info = new CenterDistInfo();
			
			
			return info;
		}};
}
