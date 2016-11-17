package rms.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Tie;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.Risk;
import rms.model.Trail;
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

	@Before(Tx.class)
	@Override
	public BaseResult deleteRisk(int id) {
		Risk r = new Risk().findById(id);
		if (id!=r.getBaseRisk()) {
			List<Risk> otherLinks = Risk.dao.find("select * from risk where baseRisk=? and project!=?", r.getBaseRisk(), r.getProject());
			System.err.println(otherLinks);
			if (otherLinks.size()==1 && otherLinks.get(0).getId()==r.getBaseRisk()) {
				new Risk().findById(r.getBaseRisk()).set("baseRisk", 0).update();
			}
			if (r.delete()) {
				return BaseResult.SUCCESS;
			}
		} else {
			// linked num
			if (Risk.dao.find("select * from risk where baseRisk=? and project!=?",id, r.getProject()).size()==0) {
				if (r.delete()) {
					return BaseResult.SUCCESS;
				}
			}
			if (r.set("project", -1).update()) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public List<Risk> getAll() {
		return Risk.dao.find("select id,title,time,baseRisk from risk");
	}

	@Override
	public List<Trail> getAllError() {
		return Trail.dao.find("select risk,time from trail where state=?",1);
	}

}
