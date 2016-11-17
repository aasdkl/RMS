package rms.services.impl;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.Project;
import rms.model.Risk;
import rms.model.Trail;
import rms.model.User;
import rms.model.Role;
import rms.services.ProjectManagementServices;
import rms.vo.ProjectVO;
import rms.vo.RiskVO;
import rms.vo.TrailVO;
import rms.vo.constant.BaseResult;

public class ProjectManagementServicesImpl implements ProjectManagementServices{
	
	@Override
	public BaseResult addProject(String name, String desc) {
		if (name!=null&&!name.equals("")) {
			if (Project.dao.add(name, desc)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public List<Project> getProjects() {
		return Project.dao.getAll();
	}

	@Override
	public BaseResult modifyProject(int id, String name, String desc) {
		if (name!=null&&!name.equals("")) {
			if (Project.dao.modify(id, name, desc)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

    @Before(Tx.class)
	@Override
	public BaseResult deleteProject(int id) {
    	RiskManagementServicesImpl r = new RiskManagementServicesImpl();
    	try {
    		List<Risk> risks= new Risk().find("select id from risk where project=?",id);
    		for (Risk risk : risks) {
    			r.deleteRisk(risk.getId());
			}
			new Project().deleteById(id);
			return BaseResult.SUCCESS;
		} catch (Exception e) {
			System.err.println(e);
		}
		return BaseResult.UNEXPECTED_ERROR;
	}


	@Override
	public ProjectVO getProject(int projectId) {
		ProjectVO vo = new ProjectVO();
		Project project = Project.dao.findById(projectId);
		vo.setProject(project);
		
		List<Risk> recommands=Risk.dao.recommand(projectId);
		vo.setRecommand(recommands);
		
		List<RiskVO> rvo = new ArrayList<RiskVO>();
		List<Risk> risks = project.getRisks();
		List<Trail> tTrails;
		List<TrailVO> trails;
		TrailVO trail;
		User u;
		Role r;
		Date date=new Date();
		//TODO (n)^2
		for (Risk risk : risks) {
			if (date.after(risk.getTime())) {
				date=risk.getTime();
			}
			trails = new ArrayList<TrailVO>();
			u = User.dao.findById(risk.getPublisher());
			r = Role.dao.find(u.getId(), projectId);
			tTrails = Trail.dao.find("select * from trail where risk=?",risk.getId());
			Collections.reverse(tTrails);
			for (Trail each : tTrails) {
				trail=new TrailVO(each, User.dao.findById(each.getTrailer()).getName());
				trails.add(trail);
			}
			List<Risk> links = new ArrayList<Risk>();
			if (risk.getBaseRisk()>0) {
				links=Risk.dao.find("select id,time from risk where baseRisk=?",risk.getBaseRisk());
			} else {
				links.add(risk);
			}
			if (date.after(links.get(0).getTime())) {
				date=links.get(0).getTime();
			}
			
			List<Trail> errorLinks = new ArrayList<Trail>();;
			for (Risk each : links) {
				errorLinks.addAll(Trail.dao.find("select time from trail where risk=? and state=?",each.getId(),1));
			}

			rvo.add(new RiskVO(u.getName(), r.getRole(), risk, trails, links, errorLinks));
		}
		vo.setOldestDate(date);
		vo.setRisks(rvo);
		return vo;
	}

	
	
	// risks
	@Override
	public BaseResult addRisk(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan, int lid) {

		if (name!=null&&!name.equals("")) {
			if (new Risk().add(uid, pid, state, name, possibility, damage, desc, spy, trigger, trailer, plan, lid)) {
				return BaseResult.SUCCESS;
			}
		}
		
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public BaseResult modifyRisk(int rid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan) {
		
		if (name!=null&&!name.equals("")) {
			if (Risk.dao.modify(rid, state, name, possibility, damage, desc, spy, trigger, trailer, plan)) {
				return BaseResult.SUCCESS;
			}
		}
		
		return BaseResult.UNEXPECTED_ERROR;
	}
	
	
	
	@Override
	@Before(Tx.class)
	public BaseResult addTrail(int id, String desc, int state, int loginId) {
    	try {
    		new Trail().add(id,desc,state,loginId);
    		new Risk().findById(id).set("state",state).update();
    		
			return BaseResult.SUCCESS;
		} catch (Exception e) {
			System.err.println(e);
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public Date getOldestDate() {
		return Risk.dao.findFirst("select min(time) as time from risk").getDate("time");
	}


	
}
