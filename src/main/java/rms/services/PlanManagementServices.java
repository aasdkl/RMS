package rms.services;

import java.util.List;

import rms.model.Plan;
import rms.vo.PlanVO;
import rms.vo.constant.BaseResult;

public interface PlanManagementServices {

	public BaseResult addPlan(String name, String desc);
	public List<Plan> getPlans();
	public BaseResult modifyPlan(int id, String name, String desc);
	public BaseResult deletePlan(int id);
	public PlanVO getPlan(int id);
	public BaseResult removeRisk(int rid, int pid);
	public BaseResult adjust(int[] rids, int[] toPlan, int fromPlan,
			String type);
	
}
