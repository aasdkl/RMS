package rms.vo;

import java.util.List;import rms.model.Risk;

import rms.model.Project;

public class ProjectVO {

	private Project project;
	private List<Risk> risks;
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Risk> getRisks() {
		return risks;
	}
	public void setRisks(List<Risk> risks) {
		this.risks = risks;
	}
	public boolean isValid() {
		return project!=null;
	}
	
	
}
