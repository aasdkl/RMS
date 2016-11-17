package rms.vo;

import rms.model.Trail;
import rms.model.User;

public class TrailVO {

	private Trail trail;
	private String user;
	
	public TrailVO(Trail trail,String user) {
		this.trail = trail;
		this.user = user;
	}
	
	public void setTrail(Trail trail) {
		this.trail = trail;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Trail getTrail() {
		return trail;
	}
	public String getUser() {
		return user;
	}
}
