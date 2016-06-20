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

public class FlowImpl {

	private static final Logger logger = LogManager.getLogger(FlowImpl.class);

	private Integer statementTypeId;

	private Map<Integer, List<StatementFeeRuleModel>> map;

	public FlowImpl() {
		this.statementTypeId = 1;
		map = new HashMap<Integer, List<StatementFeeRuleModel>>();
		init();
	}

	public void init() {
		List<StatementFeeRuleModel> list = new ArrayList<StatementFeeRuleModel>();
		StatementFeeRuleModel f1 = new StatementFeeRuleModel();
		f1.setFeeCode("fee1");
		f1.setFeeRule(
				"function feeFun(orderData, itemName){ var orderObj = eval('(' + orderData + ')'); var caclItem = 'orderObj.' + itemName; var result = eval(caclItem) * 0.4; return result}");
		
		StatementFeeRuleModel f2 = new StatementFeeRuleModel();
		f2.setFeeCode("fee2");
		f2.setFeeRule(
				"function feeFun(orderData, itemName){ var orderObj = eval('(' + orderData + ')'); var caclItem = 'orderObj.' + itemName; var result = eval(caclItem) * 0.2 ; return result;}");
		list.add(f1);
		list.add(f2);
		
		List<StatementFeeRuleModel> list2 = new ArrayList<StatementFeeRuleModel>();
		StatementFeeRuleModel f12 = new StatementFeeRuleModel();
		f12.setFeeCode("fee1");
		f12.setFeeRule(
				"function feeFun(objData, itemName){ var obj = eval('(' + objData + ')'); var caclItem = 'obj.orderModel.' + itemName; var result = eval(caclItem) * 0.4; return result}");

		StatementFeeRuleModel f22 = new StatementFeeRuleModel();
		f22.setFeeCode("fee2");
		f22.setFeeRule(
				"function feeFun(objData, itemName){ var obj = eval('(' + objData + ')'); var caclItem = 'obj.orderModel.' + itemName; var result = eval(caclItem) * 0.2 ; return result;}");
		list2.add(f12);
		list2.add(f22);

		map.put(statementTypeId, list);
		map.put(2, list2);
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
					Double res = (Double)inv.invokeFunction("feeFun", orderJson, feeCode);// 调用function并传入参数
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
	public BigDecimal caculate(List<StatementFeeRuleModel> list, Map<String,Object> map) {
		BigDecimal result = BigDecimal.ZERO; // 最终计算结果

		if (!CollectionUtils.isEmpty(list)) {
			String orderJson = JSON.toJSONString(map);
			logger.info(orderJson);
			try {
				for (StatementFeeRuleModel model : list) {
					String feeCode = model.getFeeCode(); // 结算项编码
					String script = model.getFeeRule(); // 结算规则
					Invocable inv = ScriptEngineUtils.getInstance(script);
					Double res = (Double)inv.invokeFunction("feeFun", orderJson, feeCode);// 调用function并传入参数
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
	public BigDecimal execFeeRule(Map<String,Object> params) {

		List<StatementFeeRuleModel> statementFeeLists = getStatementFee(2);
		BigDecimal result = caculate(statementFeeLists, params);
		return result;
	}
	
	public void execWithMap( FlowImpl f) {
		
		OrderModel orderModel = new OrderModel();
		orderModel.setFee1(new BigDecimal("2.5"));
		orderModel.setFee2(new BigDecimal("5.0"));
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderModel", orderModel);
		map.put("relModel",new ArrayList<Object>());
		
		BigDecimal execFeeRule = f.execFeeRule(map);
		logger.info("map:" + execFeeRule);
		
	}
	
	public void execWithObj(FlowImpl f) {
		
		OrderModel orderModel = new OrderModel();
		orderModel.setFee1(new BigDecimal("2.5"));
		orderModel.setFee2(new BigDecimal("5.0"));
		
		BigDecimal execFeeRule = f.execFeeRule(orderModel);
		logger.info("object" + execFeeRule);
		
		
	}
	
	public static void main(String[] args) {
		FlowImpl f = new FlowImpl();
		f.execWithMap(f);
	}

}
