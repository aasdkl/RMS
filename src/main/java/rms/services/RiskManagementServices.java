package rms.services;

import rms.vo.constant.BaseResult;

public interface RiskManagementServices {

	BaseResult addRisk(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan);


}
