package rms.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.Plan;
import rms.model.Risk;
import rms.model.Role;
import rms.model.Trail;
import rms.model.Type;
import rms.services.RiskManagementServices;
import rms.vo.RiskNumVO;
import rms.vo.constant.BaseResult;

public class RiskManagementServicesImpl implements RiskManagementServices{

	@Override
	public Map<Integer, RiskNumVO> getRiskNum() {
		Map<Integer, RiskNumVO> map = new HashMap<Integer, RiskNumVO>();
		List<Risk> rs = Risk.dao.find("select project,state from risk where project!=-1 or baseRisk=0 order by project");
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
		Type.dao.deleteAllByRisk(id);
		
		Risk r = new Risk().findById(id);
		// linking or not be linked
		if (id!=r.getBaseRisk()) {
			List<Risk> otherLinks = new Risk().find("select * from risk where baseRisk=? and project!=?", r.getBaseRisk(), r.getProject());
			// only have the link it self
			if (otherLinks.size()==1 && otherLinks.get(0).getId()==r.getBaseRisk()) {
				if (otherLinks.get(0).getProject()==-1) {
					otherLinks.get(0).delete();
				} else {
					new Risk().findById(r.getBaseRisk()).set("baseRisk", 0).update();
				}
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
			} else {
				if (r.set("project", -1).update()) {
					List<Trail> trails=new Trail().find("select * from trail where risk=?",r.getId());
					for (Trail trail : trails) {
						trail.delete();
					}
					return BaseResult.SUCCESS;
				}
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public List<Risk> getAll() {
		return Risk.dao.find("select * from risk where project!=-1");
	}

	@Override
	public List<Trail> getAllError() {
		return Trail.dao.find("select * from trail where state=?",1);
	}

	@Override
	public Long getNum() {
		return Risk.dao.findFirst("select count(*) as cnt from risk where project!=-1 or baseRisk=0").getLong("cnt");
	}

	@Before(Tx.class)
	@Override
	public BaseResult addRiskInPlan(int uid, int planId, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan) {
		
		if (name!=null&&!name.equals("")) {
			int id=Risk.dao.add(uid, -1, state, name, possibility, damage, desc, spy, trigger, trailer, plan, 0);
			if (planId!=0 && id!=0) {
				new Type().add(id, planId);
			}
			return BaseResult.SUCCESS;
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

}
