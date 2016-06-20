package com.ljx.statementflow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.ljx.model.OrderModel;
import com.ljx.model.StatementFeeRuleModel;
import com.ljx.utils.ScriptEngineUtils;

public class FlowImplV2 {

	private static final Logger logger = LogManager.getLogger(FlowImplV2.class);

	private Integer statementTypeId;

	private Map<Integer, List<StatementFeeRuleModel>> map;

	public FlowImplV2() {
		this.statementTypeId = 1;
		map = new HashMap<Integer, List<StatementFeeRuleModel>>();
		init();
	}

	public void init() {
		List<StatementFeeRuleModel> list = new ArrayList<StatementFeeRuleModel>();
		StatementFeeRuleModel f1 = new StatementFeeRuleModel();
		f1.setFeeCode("fee1");
		f1.setFeeRule("order.totalAmount * contract.salesCommissionRate");

		StatementFeeRuleModel f2 = new StatementFeeRuleModel();
		f2.setFeeCode("fee2");
		f2.setFeeRule("order.taskTimeFee*contract.stationRate");
		list.add(f1);
		list.add(f2);

		map.put(statementTypeId, list);
	}

	/**
	 * 获取结算单的结算项
	 * 
	 * @param statementTypeId
	 * @return
	 */
	public List<StatementFeeRuleModel> getStatementFee(Integer statementTypeId) {
		List<StatementFeeRuleModel> list = map.get(statementTypeId);
		return list;
	}

	/**
	 * 更具结算单的结算项
	 * 
	 * @param list
	 * @param orderModel
	 * @return
	 */
	public BigDecimal caculate(List<StatementFeeRuleModel> list, OrderModel orderModel) {
		BigDecimal result = BigDecimal.ZERO; // 最终计算结果

		if (!CollectionUtils.isEmpty(list)) {
			String orderJson = JSON.toJSONString(orderModel);
			try {
				for (StatementFeeRuleModel model : list) {
					String feeCode = model.getFeeCode(); // 结算项编码
					String script = model.getFeeRule(); // 结算规则
					Invocable inv = ScriptEngineUtils.getInstance(script);
					Double res = (Double) inv.invokeFunction("feeFun", orderJson, feeCode);// 调用function并传入参数
					result = result.add(new BigDecimal(res));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 更具结算单的结算项
	 * 
	 * @param list
	 * @param orderModel
	 * @return
	 */
	public BigDecimal caculate(List<StatementFeeRuleModel> list, Map<String, Object> map) {
		BigDecimal result = BigDecimal.ZERO; // 最终计算结果

		if (!CollectionUtils.isEmpty(list)) {
			String orderJson = JSON.toJSONString(map);
			logger.info(orderJson);
			try {
				for (StatementFeeRuleModel model : list) {
					String feeCode = model.getFeeCode(); // 结算项编码
					String script = model.getFeeRule(); // 结算规则
					Invocable inv = ScriptEngineUtils.getInstance(script);
					Double res = (Double) inv.invokeFunction("feeFun", orderJson, feeCode);// 调用function并传入参数
					result = result.add(new BigDecimal(res));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * 执行结算引擎
	 * 
	 * @param orderModel
	 * @return
	 */
	public BigDecimal execFeeRule(OrderModel orderModel) {
		List<StatementFeeRuleModel> statementFeeLists = getStatementFee(statementTypeId);
		BigDecimal result = caculate(statementFeeLists, orderModel);
		return result;
	}

	/**
	 * 执行结算引擎
	 * 
	 * @param orderModel
	 * @return
	 */
	public BigDecimal execFeeRule(Map<String, Object> params) {

		List<StatementFeeRuleModel> statementFeeLists = getStatementFee(2);
		BigDecimal result = caculate(statementFeeLists, params);
		return result;
	}

	public void execWithMap(FlowImplV2 f) {

		OrderModel orderModel = new OrderModel();
		orderModel.setFee1(new BigDecimal("2.5"));
		orderModel.setFee2(new BigDecimal("5.0"));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderModel", orderModel);
		map.put("relModel", new ArrayList<Object>());

		BigDecimal execFeeRule = f.execFeeRule(map);
		logger.info("map:" + execFeeRule);

	}

	public void execWithObj(FlowImplV2 f) {

		OrderModel orderModel = new OrderModel();
		orderModel.setFee1(new BigDecimal("2.5"));
		orderModel.setFee2(new BigDecimal("5.0"));

		BigDecimal execFeeRule = f.execFeeRule(orderModel);
		logger.info("object" + execFeeRule);

	}

	public static void main(String[] args) {
		FlowImplV2 f = new FlowImplV2();
		f.execWithMap(f);
	}

}
