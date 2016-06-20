/**
 * 
 */
package com.ljx.statement;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class Fee {
	private String statementOrderNo;
	private String orderNo;
	private String feeType;
	private BigDecimal feeAmount;

	public String getStatementOrderNo() {
		return statementOrderNo;
	}

	public void setStatementOrderNo(String statementOrderNo) {
		this.statementOrderNo = statementOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public BigDecimal getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}
}
