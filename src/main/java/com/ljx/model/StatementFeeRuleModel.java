package com.ljx.model;

public class StatementFeeRuleModel {

	private int id;
	private int statementTypeId;
	private String feeCode;
	private String desc;
	private String feeRule ; //当前费用的计算规则

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatementTypeId() {
		return statementTypeId;
	}

	public void setStatementTypeId(int statementTypeId) {
		this.statementTypeId = statementTypeId;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFeeRule() {
		return feeRule;
	}

	public void setFeeRule(String feeRule) {
		this.feeRule = feeRule;
	}
	

}
