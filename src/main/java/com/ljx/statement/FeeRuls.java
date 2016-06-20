/**
 * 
 */
package com.ljx.statement;

/**
 * @author admin
 *
 */
public class FeeRuls {
	private Long rulId;
	private String feeType;
	private String feeName;
	private String ruls;

	public Long getRulId() {
		return rulId;
	}

	public void setRulId(Long rulId) {
		this.rulId = rulId;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getRuls() {
		return ruls;
	}

	public void setRuls(String ruls) {
		this.ruls = ruls;
	}
}