package com.ljx.statement;

import java.math.BigDecimal;

/**
 * @author admin
 */
public class Contract {
	private BigDecimal salesCommissionRate;
	private BigDecimal stationRate;
	private BigDecimal timeRate;
	
	public BigDecimal getSalesCommissionRate() {
		return salesCommissionRate;
	}
	public void setSalesCommissionRate(BigDecimal salesCommissionRate) {
		this.salesCommissionRate = salesCommissionRate;
	}
	public BigDecimal getStationRate() {
		return stationRate;
	}
	public void setStationRate(BigDecimal stationRate) {
		this.stationRate = stationRate;
	}
	public BigDecimal getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(BigDecimal timeRate) {
		this.timeRate = timeRate;
	}
	
}
