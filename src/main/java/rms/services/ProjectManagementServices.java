package rms.services;

import java.util.List;

import rms.model.Project;
import rms.vo.ProjectVO;
import rms.vo.constant.BaseResult;

public interface ProjectManagementServices {

	public BaseResult addProject(String name, String desc);
	public List<Project> getProjects();
	public BaseResult modifyProject(int id, String name, String desc);
	public BaseResult deleteProject(int id);
	public ProjectVO getProject(int projectId);

}