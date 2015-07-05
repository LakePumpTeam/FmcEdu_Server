package com.fmc.edu.admin.builder;

import com.fmc.edu.util.pagenation.Pagination;

/**
 * Created by Yove on 6/30/2015.
 */
public class SelectPaginationBuilder {

	private static final int DEFAULT_PAGINATION_PAGE_SIZE = Integer.MAX_VALUE;

	public static final Pagination getSelectiPagination() {
		return new Pagination(1, DEFAULT_PAGINATION_PAGE_SIZE);
	}
}
