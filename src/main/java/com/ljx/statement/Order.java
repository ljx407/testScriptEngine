/**
 * 
 */
package com.ljx.statement;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class Order {
	private Long id;
	private String orderNo;
	private String orderType;
	private BigDecimal totalAmount;
	private BigDecimal taskTimeFee;
	private BigDecimal stationFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTaskTimeFee() {
		return taskTimeFee;
	}

	public void setTaskTimeFee(BigDecimal taskTimeFee) {
		this.taskTimeFee = taskTimeFee;
	}

	public BigDecimal getStationFee() {
		return stationFee;
	}

	public void setStationFee(BigDecimal stationFee) {
		this.stationFee = stationFee;
	}
}
