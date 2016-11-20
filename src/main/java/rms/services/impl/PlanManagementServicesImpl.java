package rms.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.Plan;
import rms.model.Project;
import rms.model.Risk;
import rms.model.Role;
import rms.model.Trail;
import rms.model.Type;
import rms.model.User;
import rms.services.PlanManagementServices;
import rms.vo.PlanVO;
import rms.vo.ProjectVO;
import rms.vo.RiskVO;
import rms.vo.TrailVO;
import rms.vo.constant.BaseResult;

public class PlanManagementServicesImpl implements PlanManagementServices{
	
	@Override
	public BaseResult addPlan(String name, String desc) {
		if (name!=null&&!name.equals("")) {
			if (Plan.dao.add(name, desc)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public List<Plan> getPlans() {
		return Plan.dao.getAll();
	}

	@Override
	public BaseResult modifyPlan(int id, String name, String desc) {
		if (name!=null&&!name.equals("")) {
			if (Plan.dao.modify(id, name, desc)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public BaseResult deletePlan(int id) {
		List<Type> all=Type.dao.findAllByPlan(id);
		RiskManagementServicesImpl rm = new RiskManagementServicesImpl();
		for (Type each : all) {
			Risk risk= new Risk().findById(each.getRisk());
			if (risk.getProject()==-1 && risk.getBaseRisk()==0) {
				rm.deleteRisk(risk.getId());
			}
		}
		if (new Plan().deleteById(id)) {
			return BaseResult.SUCCESS;
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public PlanVO getPlan(int id) {
		PlanVO vo = new PlanVO();
		Plan plan = Plan.dao.findById(id);
		vo.setPlan(plan);

		List<RiskVO> rvo = new ArrayList<RiskVO>();
		List<Risk>  risks;
		if (id==0) {
			risks = Risk.dao.findAll();
		} else if (plan!=null) {
			risks = plan.getRisks();
		} else {
			return vo;
		}
		List<Trail> tTrails;
		List<TrailVO> trails;
		TrailVO trail;
		User u;
		Role r;
		Date date=new Date();
		for (Risk risk : risks) {
			if (date.after(risk.getTime())) {
				date=risk.getTime();
			}
			trails = new ArrayList<TrailVO>();
			u = User.dao.findById(risk.getPublisher());
			r = Role.dao.find(u.getId(), risk.getProject());
			tTrails = Trail.dao.find("select * from trail where risk=?",risk.getId());
			Collections.reverse(tTrails);
			for (Trail each : tTrails) {
				trail=new TrailVO(each, User.dao.findById(each.getTrailer()).getName());
				trails.add(trail);
			}
			
			List<Risk> links = new ArrayList<Risk>();
			if (risk.getBaseRisk()>0) {
				links=Risk.dao.find("select id,time from risk where baseRisk=? and project!=-1",risk.getBaseRisk());
			} else if(risk.getProject()!=-1) {
				links.add(risk);
			}
			if (links.size()!=0 && date.after(links.get(0).getTime())) {
				date=links.get(0).getTime();
			}
			
			List<Trail> errorLinks = new ArrayList<Trail>();;
			for (Risk each : links) {
				errorLinks.addAll(Trail.dao.find("select time from trail where risk=? and state=?",each.getId(),1));
			}
			
			rvo.add(new RiskVO(u.getName(), r==null?"":r.getRole(), risk, trails, links, errorLinks));
		}
		vo.setOldestDate(date);
		vo.setRisks(rvo);
		return vo;
	}

	@Override
	public BaseResult removeRisk(int rid, int pid) {
		if (Type.dao.delete(rid, pid)) {
			return BaseResult.SUCCESS;
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public BaseResult adjust(int[] rids, int[] toPlan, int fromPlan, String type) {
		for (int pid : toPlan) {
			for (int rid : rids) {
				if (Type.dao.find(rid, pid)!=null) {
					continue;
				}
				if (type.equals("copy")) {
					Type.dao.add(rid, pid);
				} else {
					// from plan!=0
					Type.dao.move(rid, fromPlan, pid);
				}
			}
		}
		return BaseResult.SUCCESS;
	}


	
}
