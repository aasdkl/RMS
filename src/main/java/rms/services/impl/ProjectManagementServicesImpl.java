package rms.services.impl;

import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.Project;
import rms.model.Risk;
import rms.services.ProjectManagementServices;
import rms.vo.ProjectVO;
import rms.vo.constant.BaseResult;

public class ProjectManagementServicesImpl implements ProjectManagementServices{
	
	@Override
	public BaseResult addProject(String name, String desc) {
		if (!name.equals("")&&name!=null) {
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
		if (!name.equals("")&&name!=null) {
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
			System.out.println(e);
		}
		return BaseResult.UNEXPECTED_ERROR;
	}


	@Override
	public ProjectVO getProject(int projectId) {
		ProjectVO vo = new ProjectVO();
		Project project = Project.dao.findById(projectId);
		vo.setProject(project);
		vo.setRisks(project.getRisks());
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


	
}
