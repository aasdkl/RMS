package rms.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Tie;

import rms.model.Risk;
import rms.services.RiskManagementServices;
import rms.vo.RiskNumVO;
import rms.vo.constant.BaseResult;

public class RiskManagementServicesImpl implements RiskManagementServices{

	@Override
	public Map<Integer, RiskNumVO> getRiskNum() {
		Map<Integer, RiskNumVO> map = new HashMap<Integer, RiskNumVO>();
		List<Risk> rs = Risk.dao.find("select project,state from risk order by project");
		if (rs.size()==0) {
			return map;
		}
		
		int[] t={0,0,0};
		RiskNumVO vo=new RiskNumVO();
		int pid=rs.get(0).getProject();
		int i = 0;
		Risk risk;
		do {
			risk=rs.get(i);
			i++;
			if (pid!=risk.getProject()) {
				vo.setAll(t[1], t[0], t[2]);
				map.put(pid, vo);
				pid=risk.getProject();
				vo = new RiskNumVO();
				t[0]=t[1]=t[2]=0;
			}
			t[risk.getState()]++;
		} while (i<rs.size());
		vo.setAll(t[1], t[0], t[2]);
		map.put(pid, vo);
		
		return map;
	}

	@Override
	public BaseResult deleteRisk(int id) {
		if (new Risk().deleteById(id)) {
			return BaseResult.SUCCESS;
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

}
