package rms.services;

import rms.vo.UserVO;

public interface UserManagementServices {
	public UserVO login(String account, String password);

	public UserVO register(String account, String password, String password2);
	
}
