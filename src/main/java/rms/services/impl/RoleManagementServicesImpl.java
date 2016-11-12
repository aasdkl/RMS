package rms.services.impl;

import rms.model.Project;
import rms.model.Role;
import rms.model.User;
import rms.services.RiskManagementServices;
import rms.services.RoleManagementServices;
import rms.vo.constant.BaseResult;

public class RoleManagementServicesImpl implements RoleManagementServices{

	@Override
	public BaseResult addRole(int uid, int pid, String role) {
		if (role!=null&&!role.equals("")) {
			if (Role.dao.add(uid, pid, role)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}

	@Override
	public Role getRole(int uid, int pid) {
		return Role.dao.find(uid,pid);
	}

	@Override
	public BaseResult modifyRole(int rid, String role) {
		if (role!=null&&!role.equals("")) {
			if (Role.dao.modify(rid, role)) {
				return BaseResult.SUCCESS;
			}
		}
		return BaseResult.UNEXPECTED_ERROR;
	}


}
