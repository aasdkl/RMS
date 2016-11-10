package rms.services.impl;

import java.util.List;

import rms.model.Project;
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

	@Override
	public BaseResult deleteProject(int id) {
		boolean result=new Project().deleteById(id);
		// TODO foreign key
		if (result) {
			return BaseResult.SUCCESS;
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

	
}
