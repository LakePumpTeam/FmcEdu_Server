package com.fmc.edu.repository;


import org.apache.ibatis.session.SqlSession;

import javax.annotation.Resource;

public abstract class BaseRepository {

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;


	public SqlSession getSqlSession() {
		return this.sqlSession;
	}

}
