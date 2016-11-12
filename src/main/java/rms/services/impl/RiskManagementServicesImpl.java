package rms.services.impl;

import rms.model.Risk;
import rms.services.RiskManagementServices;
import rms.vo.constant.BaseResult;

public class RiskManagementServicesImpl implements RiskManagementServices{

	@Override
	public BaseResult addRisk(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan) {

		if (name!=null&&!name.equals("")) {
			if (Risk.dao.add(uid, pid, state, name, possibility, damage, desc, spy, trigger, trailer, plan)) {
				return BaseResult.SUCCESS;
			}
		}
		
		return BaseResult.UNEXPECTED_ERROR;
	}


}
