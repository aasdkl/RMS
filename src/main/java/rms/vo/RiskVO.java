package rms.vo;

import java.util.List;

import rms.model.Risk;
import rms.model.Trail;
import rms.model.User;

public class RiskVO {

	private String publisher;
	private String role;
	private Risk risk;
	private List<Trail> trails;
	
	public RiskVO(String publisher, String role, Risk risk, List<Trail> trails) {
		this.publisher=publisher;
		this.role=role;
		this.risk=risk;
		this.trails=trails;
	}
	public String getPublisher() {
		return publisher;
	}
	public String getRole() {
		return role;
	}public void setPublisher(String publisher) {
		this.publisher = publisher;
	}public void setRole(String role) {
		this.role = role;
	}
	public Risk getRisk() {
		return risk;
	}
	public List<Trail> getTrails() {
		return trails;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	public void setTrails(List<Trail> trails) {
		this.trails = trails;
	}

}
