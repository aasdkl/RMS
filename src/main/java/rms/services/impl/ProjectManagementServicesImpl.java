package rms.services.impl;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    	try {
    		List<Risk> risks= new Risk().find("select * from risk where project=?",id);
    		for (Risk risk : risks) {
				risk.delete();
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
		
		List<RiskVO> rvo = new ArrayList<RiskVO>();
		List<Risk> risks = project.getRisks();
		List<Trail> trails;
		User u;
		Role r;
		for (Risk risk : risks) {
			u = User.dao.findById(risk.getPublisher());
			r = Role.dao.find(u.getId(), projectId);
			trails = Trail.dao.find("select * from trail where risk=?",risk.getId());
			trails = Trail.dao.find("select * from trail where risk=?",risk.getId());
			Collections.reverse(trails);
			rvo.add(new RiskVO(u.getName(), r.getRole(), risk, trails));
		}
		vo.setRisks(rvo);
		return vo;
	}

	
	
	// risks
	@Override
	public BaseResult addRisk(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan) {

		if (name!=null&&!name.equals("")) {
			if (new Risk().add(uid, pid, state, name, possibility, damage, desc, spy, trigger, trailer, plan)) {
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


	
}
