package rms.services;

import java.util.Date;
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
	
	// risks
	public BaseResult addRisk(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan, int lid);
	public BaseResult modifyRisk(int rid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan);
	
	public BaseResult addTrail(int id, String desc, int state, int loginId);
	public Date getOldestDate();
	public BaseResult checkin(int pid, int[] plans, int uid);
	

}
