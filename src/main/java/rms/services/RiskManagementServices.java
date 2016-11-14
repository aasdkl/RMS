package rms.services;

import java.util.Map;

import rms.vo.RiskNumVO;
import rms.vo.constant.BaseResult;

public interface RiskManagementServices {

	public Map<Integer, RiskNumVO> getRiskNum();

	public BaseResult deleteRisk(int id);


}
