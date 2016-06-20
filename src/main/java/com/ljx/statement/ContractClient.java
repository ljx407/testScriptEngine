package com.ljx.statement;

import java.math.BigDecimal;

/**
 * @author admin
 *
 */
public class ContractClient {
	private static ContractClient contractClient = new ContractClient();
	
	public Contract getContract(int companyId,int mercharntId){
		Contract contract = new Contract();
		contract.setSalesCommissionRate(new BigDecimal(companyId).divide(new BigDecimal(100)));
		contract.setStationRate(new BigDecimal(mercharntId).divide(new BigDecimal(100)));
		contract.setTimeRate(new BigDecimal(mercharntId+companyId).divide(new BigDecimal(100)));
		
		return  contract;
	}
	
	public static ContractClient getInstances(){
		return contractClient;
	}
}