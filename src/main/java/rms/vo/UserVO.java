package rms.vo;

import rms.vo.constant.UserResult;
import rms.model.User;


public final class UserVO {
	private UserResult userResult;
	private User user;
	public UserVO(UserResult loginResult, User user) {
		this.userResult=loginResult;
		this.user=user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setLoginResult(UserResult loginResult) {
		this.userResult = loginResult;
	}
	public User getUser() {
		return user;
	}
	public UserResult getLoginResult() {
		return userResult;
	}
	
	
	public boolean isLoginSuccess() {
		return getLoginResult()==UserResult.SUCCESS;
	}
	public String getLoginResultInfo() {
		return userResult.getInfo();
	}
	public String getName() {
		return user.getName();
	}
	public String getAccount() {
		return user.getAccount();
	}
}
