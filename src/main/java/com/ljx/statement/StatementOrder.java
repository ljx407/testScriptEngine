/**
 * 
 */
package com.ljx.statement;

import java.util.List;

/**
 * @author admin
 *
 */
public class StatementOrder {
	private String statementOrderNo;
	private Order order;
	private List<Fee> feeList;

	public String getStatementOrderNo() {
		return statementOrderNo;
	}

	public void setStatementOrderNo(String statementOrderNo) {
		this.statementOrderNo = statementOrderNo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Fee> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<Fee> feeList) {
		this.feeList = feeList;
	}
}
