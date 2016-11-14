package rms.vo;

import java.util.List;import rms.model.Risk;

import rms.model.Project;

public class ProjectVO {

	private Project project;
	private List<RiskVO> risks;
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<RiskVO> getRisks() {
		return risks;
	}
	public void setRisks(List<RiskVO> risks) {
		this.risks = risks;
	}
	public boolean isValid() {
		return project!=null;
	}
	
	
}
