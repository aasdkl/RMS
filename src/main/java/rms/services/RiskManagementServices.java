package rms.services;

import java.util.List;
import java.util.Map;

import rms.model.Risk;
import rms.model.Trail;
import rms.vo.RiskNumVO;
import rms.vo.constant.BaseResult;

public interface RiskManagementServices {

	public Map<Integer, RiskNumVO> getRiskNum();

	public BaseResult deleteRisk(int id);

	public List<Risk> getAll();

	public List<Trail> getAllError();

	public Long getNum();

	public BaseResult addRiskInPlan(int uid, int planId,int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan);


}
