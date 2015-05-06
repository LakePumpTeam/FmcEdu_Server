package com.fmc.edu.util.pagenation;

/**
 * Created by Yove on 5/6/2015.
 */
public class Pagination {

	private static final int FIRST_PAGE = 1;

	private int mPageNum = FIRST_PAGE;

	private int mPageSize;

	private int mRowTotal;

	public Pagination(int pPageNum, int pPageSize) {
		mPageNum = pPageNum;
		mPageSize = pPageSize;
	}

	public boolean isFirstPage() {
		return getPageNum() == FIRST_PAGE;
	}


	public boolean isLastPage() {
		return getPageNum() == getMaxPageNum();
	}


	public int getNextPage() {
		if (getPageNum() >= getMaxPageNum()) {
			return getMaxPageNum();
		}
		return getPageNum() + 1;
	}


	public int getPrePage() {
		if (getPageNum() <= FIRST_PAGE) {
			return FIRST_PAGE;
		}
		return getPageNum() - 1;
	}


	public int getPageStartIndex() {
		return (mPageNum - 1) * mPageSize;
	}


	/**
	 * @return the maxPageNum
	 */
	public int getMaxPageNum() {
		return (getRowTotal() / getPageSize()) + (0 == getRowTotal() % getPageSize() ? 0 : 1);
	}

	public int getPageNum() {
		return mPageNum;
	}

	public void setPageNum(final int pPageNum) {
		mPageNum = pPageNum;
	}

	public int getPageSize() {
		return mPageSize;
	}

	public void setPageSize(final int pPageSize) {
		mPageSize = pPageSize;
	}

	public int getRowTotal() {
		return mRowTotal;
	}

	public void setRowTotal(final int pRowTotal) {
		mRowTotal = pRowTotal;
	}
}
