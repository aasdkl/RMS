package rms.services;

import java.util.List;

import rms.model.Project;
import rms.model.Role;
import rms.model.User;
import rms.vo.ProjectVO;
import rms.vo.constant.BaseResult;

public interface RoleManagementServices {

	public BaseResult addRole(int uid, int pid, String role);

	public Role getRole(int uid, int pid);

	public BaseResult modifyRole(int rid, String role);

}
