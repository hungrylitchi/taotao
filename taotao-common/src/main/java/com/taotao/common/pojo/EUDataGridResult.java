package com.taotao.common.pojo;

import java.util.List;

/**
 * EasyUIDateGrid支持的数据格式封装而成的一个POJO 。调用dao查询商品列表。并分页。
 * 返回商品列表。返回一个EasyUIDateGrid支持的数据格式。 需要创建一个Pojo。此pojo应该放到taotao-common工程中。
 * 
 * @author Alan Wang
 *
 */
public class EUDataGridResult {
	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
