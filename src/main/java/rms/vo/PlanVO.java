package rms.vo;

import java.util.Date;
import java.util.List;

import rms.model.Plan;

public class PlanVO {

	private Plan plan;
	private Date oldestDate;
	private List<RiskVO> risks;
	
	public Date getOldestDate() {
		return oldestDate;
	}
	public void setOldestDate(Date oldestDate) {
		this.oldestDate = oldestDate;
	}
	public List<RiskVO> getRisks() {
		return risks;
	}
	public void setRisks(List<RiskVO> risks) {
		this.risks = risks;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public boolean isValid() {
		return plan!=null;
	}
}
