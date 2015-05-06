package com.fmc.edu.repository;


import com.fmc.edu.util.pagenation.Pagination;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRepository {

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;


	public SqlSession getSqlSession() {
		return this.sqlSession;
	}


	protected static final String START_INDEX = "start";

	protected static final String SIZE = "size";

	protected Map<String, Object> paginationToParameters(Pagination pPagination) {
		Map<String, Object> parameters = new HashMap<>();
		if (pPagination == null) {
			return parameters;
		}
		parameters.put(START_INDEX, pPagination.getPageStartIndex());
		parameters.put(SIZE, pPagination.getPageSize());
		return parameters;
	}

}
