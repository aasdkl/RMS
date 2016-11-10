package rms.services.impl;

import rms.model.User;
import rms.services.UserManagementServices;
import rms.vo.UserVO;
import rms.vo.constant.UserResult;

public class UserManagementServicesImpl implements UserManagementServices{

	@Override
	public UserVO login(String account, String password) {
		User user = null;
		UserResult type = getVertify(account, password);
		if (type == UserResult.FORMAT_PASS) {
			user = User.dao.login(account, password);
			if (user == null) {
				type = UserResult.PSW_ERROR;
			} else {
				type = UserResult.SUCCESS;
			}
		}
		return wrapLoginReturn(type, user);
	}

	private UserVO wrapLoginReturn(UserResult type, User user) {
		return new UserVO(type, user);
	}

	private UserResult getRegVertify(String account, String password, String password2) {
		if (password2==null || !password.equals(password2)) {
			return UserResult.PSW_AGAIN_ERROR;
		}
		if (isAccountExist(account)) {
			return UserResult.ACC_EXIST;
		}
		return getVertify(account, password);
	}

	private UserResult getVertify(String account, String password) {
		if (!isAccountFormatError(account)) {
			return UserResult.ACC_FORMAT_ERROR;
		} else if (!isPasswordFormatError(password)) {
			return UserResult.PSW_FORMAT_ERROR;
		} else {
			return UserResult.FORMAT_PASS;
		}
	}

	private boolean isAccountExist(String account) {
		if(account!=null && User.dao.isExist(account)){
			return true;
		}
		return false;
	}
	private boolean isAccountFormatError(String account) {
		if(account!=null && account.length()>0){
			return true;
		}
		return false;
	}

	private boolean isPasswordFormatError(String password) {
		if(password!=null && password.length()>0){
			return true;
		}
		return false;
	}

	@Override
	public UserVO register(String account, String password, String password2) {
		User user = null;
		UserResult type = getRegVertify(account, password, password2);
		if (type == UserResult.FORMAT_PASS) {
			user = User.dao.register(account, password);
			if (user == null) {
				type = UserResult.PSW_ERROR;
			} else {
				type = UserResult.SUCCESS;
			}
		}
		return wrapLoginReturn(type, user);
	}


}
