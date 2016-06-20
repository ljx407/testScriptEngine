package com.ljx.model;

import java.math.BigDecimal;

public class OrderModel {
	private int statementTypeId ;
	private BigDecimal fee1;
	private BigDecimal fee2;
	
	public int getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(int statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

	public BigDecimal getFee1() {
		return fee1;
	}

	public void setFee1(BigDecimal fee1) {
		this.fee1 = fee1;
	}

	public BigDecimal getFee2() {
		return fee2;
	}

	public void setFee2(BigDecimal fee2) {
		this.fee2 = fee2;
	}

}
