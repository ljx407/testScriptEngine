/**
 * 
 */
package com.ljx.statement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * @author admin
 *
 */
public class StatementTest {
	@Test
	public void statement() throws ScriptException {
		Map<Long, FeeRuls> ruls = getRuls();
		List<Order> orders = getOrders();
		Map<String, Long> orderFeeRuls = getOrderFeeRuls();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		for (Order order : orders) {
			engine.put("order", order);
			engine.put("contractClient", ContractClient.getInstances());
			StatementOrder statementOrder = new StatementOrder();
			String statementOrderNo = "statement_" + order.getOrderNo();
			statementOrder.setStatementOrderNo(statementOrderNo);
			statementOrder.setOrder(order);
			List<Fee> feeList = new ArrayList<Fee>();
			Iterator<String> it = orderFeeRuls.keySet().iterator();
			while (it.hasNext()) {
				Fee fee = new Fee();
				fee.setStatementOrderNo(statementOrderNo);
				String feeType = it.next();
				fee.setOrderNo(order.getOrderNo());
				fee.setFeeType(feeType);
				fee.setFeeAmount(new BigDecimal((Double) engine.eval(ruls.get(orderFeeRuls.get(feeType)).getRuls()))
						.setScale(2, BigDecimal.ROUND_HALF_UP));
				feeList.add(fee);
			}
			statementOrder.setFeeList(feeList);
			System.out.println(JSON.toJSONString(statementOrder));
			System.out.println(statementOrder.getFeeList().get(0).getFeeType()+":"+statementOrder.getFeeList().get(0).getFeeAmount());
		}
	}

	private Map<Long, FeeRuls> getRuls() {
		FeeRuls ruls1 = new FeeRuls();
		ruls1.setRulId(1L);
		ruls1.setFeeType("SALES_COMMISSION");
		ruls1.setFeeName("f1");
		ruls1.setRuls("var contract = contractClient.getContract(1,2);order.totalAmount*contract.salesCommissionRate");

		FeeRuls ruls2 = new FeeRuls();
		ruls2.setRulId(2L);
		ruls2.setFeeType("TASKTIME_COMMISSION");
		ruls2.setFeeName("f2");
		ruls2.setRuls("var contract = contractClient.getContract(1,2);order.taskTimeFee*contract.stationRate");
		FeeRuls ruls3 = new FeeRuls();
		ruls3.setRulId(3L);
		ruls3.setFeeType("STATION_COMMISSION");
		ruls3.setFeeName("f3");
		ruls3.setRuls("var contract = contractClient.getContract(1,2);order.stationFee*contract.timeRate");
		Map<Long, FeeRuls> rulsList = new HashMap<Long, FeeRuls>();
		rulsList.put(ruls1.getRulId(), ruls1);
		rulsList.put(ruls2.getRulId(), ruls2);
		rulsList.put(ruls3.getRulId(), ruls3);
		return rulsList;
	}

	private List<Order> getOrders() {
		List<Order> orderList = new ArrayList<Order>();
		int orderNum = new Random().nextInt(100);
		// int orderNum = 100000;
		for (long i = 1; i <= orderNum; i++) {
			Order order = new Order();
			order.setId(i);
			order.setOrderNo("order" + i);
			order.setOrderType("SALE");
			int amount = new Random().nextInt(1000);
			order.setTotalAmount(new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP));
			order.setTaskTimeFee(new BigDecimal(amount * Math.random()).setScale(2, BigDecimal.ROUND_HALF_UP));
			order.setStationFee(new BigDecimal(amount * Math.random()).setScale(2, BigDecimal.ROUND_HALF_UP));
			orderList.add(order);
		}
		return orderList;
	}

	private Map<String, Long> getOrderFeeRuls() {
		Map<String, Long> ruls = new HashMap<String, Long>();
		ruls.put("SALES_COMMISSION", 1L);
		ruls.put("STATION_COMMISSION", 3L);
		return ruls;
	}

}
