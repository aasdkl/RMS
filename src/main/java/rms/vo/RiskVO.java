package rms.vo;

import java.util.List;

import rms.model.Risk;
import rms.model.Trail;
import rms.model.User;

public class RiskVO {

	private String publisher;
	private String role;
	private Risk risk;
	private List<Risk> links;
	private List<Trail> linkErrorTrails;
	private List<TrailVO> trails;
	
	public RiskVO(String publisher, String role, Risk risk, List<TrailVO> trails,List<Risk> links, List<Trail> linkErrorTrails) {
		this.publisher=publisher;
		this.role=role;
		this.risk=risk;
		this.trails=trails;
		this.links = links;
		this.linkErrorTrails = linkErrorTrails;
	}
	public List<Trail> getLinkErrorTrails() {
		return linkErrorTrails;
	}
	public void setLinkErrorTrails(List<Trail> linkErrorTrails) {
		this.linkErrorTrails = linkErrorTrails;
	}
	public List<Risk> getLinks() {
		return links;
	}
	public void setLinks(List<Risk> links) {
		this.links = links;
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
	public List<TrailVO> getTrails() {
		return trails;
	}
	public void setRisk(Risk risk) {
		this.risk = risk;
	}
	public void setTrails(List<TrailVO> trails) {
		this.trails = trails;
	}

}
