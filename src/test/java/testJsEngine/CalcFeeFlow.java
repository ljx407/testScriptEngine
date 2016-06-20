package testJsEngine;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.ljx.model.OrderModel;
import com.ljx.model.StatementFeeRuleModel;
import com.ljx.statement.Fee;

public class CalcFeeFlow {
	
	/**
	 * 1.获取待结算单列表orderList
	 * 2.获取jsEngine获取计算结算单所需要的相关对象
	 * 3.获取计算结算单所需要的相关对象，并将所有对象加入到jsEngine环境中
	 * 4.for循环结算单
	 * 4.1 获取每个结算单的结算类型
	 * 4.2 获取当前结算类型的结算费及结算规则列表ruleList
	 * 4.3 for规则列表获得费用项结果
	 * 4.4 对结算结果进行后期处理
	 */
	public void cacluFee() {
		List<OrderModel> orderList = getOrderList(); //1
		
		ScriptEngineManager sem = new ScriptEngineManager(); //2
		ScriptEngine se = sem.getEngineByName("javascript");
		
		getRelItem(se); //3
		
		for(OrderModel orderModel : orderList) { //4
			List<StatementFeeRuleModel> feeRuleList = getStatementFeeById(orderModel.getStatementTypeId()); //4.1
			List<Fee> feeList = new ArrayList<Fee>();
			for(StatementFeeRuleModel feeRuleModel  : feeRuleList) { //4.3
				String funRule = feeRuleModel.getFeeRule();
				try {
					BigDecimal result = new BigDecimal((Double)se.eval(funRule)).setScale(2, BigDecimal.ROUND_HALF_UP);
					Fee fee = new Fee();
					fee.setFeeAmount(result);
					feeList.add(fee);
				} catch (ScriptException e) {
					e.printStackTrace();
				}
			}
			postHandle(feeList);
		}
		
	}

	/**
	 * 获取待结算单列表orderList
	 * @return
	 */
	private List<OrderModel> getOrderList() {
		return null ;
	}
	
	/**
	 * 获取计算结算单所需要的相关对象
	 */
	private void getRelItem(ScriptEngine se) {
		
	}
	
	/**
	 * 根据结算类型id获取相关的结算费用规则
	 */
	private List<StatementFeeRuleModel> getStatementFeeById(int statementTypeId) {
		return null;
	}
	
	/**
	 * 获得的数据进行后期处理（当条入库或者批量入库操作）
	 * @param feeList
	 */
	private void postHandle(List<Fee> feeList) {
		
	}
	
	
}
