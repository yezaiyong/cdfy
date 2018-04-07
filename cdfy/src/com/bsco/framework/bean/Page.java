package com.bsco.framework.bean;

import java.util.ArrayList;
import java.util.List;

public class Page {

	public enum OrderType {
		desc, asc
	}
	private int pageNo=1;
	private int pageSize=15;
	private long totalCount;
	private String orderBy;
	private OrderType orderType;
	private List list = new ArrayList();

	public int getPageNo() {
		return pageNo<1?1:pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPage() {
		return totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

}
