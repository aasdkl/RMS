package rms.vo;

import java.util.Date;
import java.util.List;
import rms.model.Risk;
import rms.model.Project;

public class ProjectVO {

	private Project project;
	private Date oldestDate;
	private List<RiskVO> risks;
	private List<Risk> recommand;
	
	public Date getOldestDate() {
		return oldestDate;
	}
	public void setOldestDate(Date oldestDate) {
		this.oldestDate = oldestDate;
	}
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
	public List<Risk> getRecommand() {
		return recommand;
	}
	public void setRecommand(List<Risk> recommand) {
		this.recommand = recommand;
	}
	
}
