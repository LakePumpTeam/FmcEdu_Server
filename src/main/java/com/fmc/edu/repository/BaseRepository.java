package com.fmc.edu.repository;


import com.fmc.edu.constant.GlobalConstant;
import com.fmc.edu.constant.JSONOutputConstant;
import com.fmc.edu.util.pagenation.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRepository {

	protected static final String START_INDEX = "start";
	protected static final String SIZE = "size";
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	public SqlSession getSqlSession() {
		return this.sqlSession;
	}

	protected Map<String, Object> paginationToParameters(Pagination pPagination) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (pPagination == null) {
			return parameters;
		}
		parameters.put(START_INDEX, pPagination.getPageStartIndex());
		parameters.put(SIZE, pPagination.getPageSize());
		return parameters;
	}

	protected void addIsLastPageFlag(Map<String,Object> pDataMap, List<?> pResult, final int pPageSize){
		if(pResult == null){
			pDataMap.put(JSONOutputConstant.IS_LAST_PAGE, GlobalConstant.TRUE);
			return;
		}

		if(CollectionUtils.isEmpty(pResult) || pResult.size() < pPageSize){
			pDataMap.put(JSONOutputConstant.IS_LAST_PAGE, GlobalConstant.TRUE);
		}else{
			pDataMap.put(JSONOutputConstant.IS_LAST_PAGE, GlobalConstant.FALSE);
		}
	}
}
