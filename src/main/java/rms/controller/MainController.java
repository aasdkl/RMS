package rms.controller;

import java.util.List;
import java.util.regex.Pattern;

import rms.model.Plan;
import rms.model.User;
import rms.config.ReturnConstants;
import rms.interceptor.IndexInterceptor;
import rms.interceptor.LoginInterceptor;
import rms.services.*;
import rms.services.impl.*;
import rms.vo.*;
import rms.vo.constant.*;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

@Before(LoginInterceptor.class)
public class MainController extends Controller {

    private UserManagementServices userManageservice = new UserManagementServicesImpl();
    private PlanManagementServices planManageservice = new PlanManagementServicesImpl();
    private ProjectManagementServices projectManageservice = new ProjectManagementServicesImpl();
    private RoleManagementServices roleManageservice = new RoleManagementServicesImpl();
    private RiskManagementServices riskManageservice = new RiskManagementServicesImpl();

    // -----------------------------login--------------------------
    @Clear
    @Before(IndexInterceptor.class)
    public void log() {
		render("login.html");
	}
    
    @Clear
    @Before(IndexInterceptor.class)
	public void login() {
	    String account = getPara("account");
	    String password = getPara("password");

	    UserVO result = userManageservice.login(account, password);
		setAttr(ReturnConstants.result.toString(), result.getLoginResultInfo());
	    setLogin(result);
		renderJson();
	}
	
	@Clear
    @Before(IndexInterceptor.class)
	public void register() {
	    String account = getPara("account");
	    String password = getPara("password1");
	    String password2 = getPara("password2");

	    UserVO result = userManageservice.register(account, password, password2);
	    setAttr(ReturnConstants.result.toString(), result.getLoginResultInfo());
	    setLogin(result);

		renderJson();
	}
	
    private void setLogin(UserVO result) {
	    if (result.isLoginSuccess()) {
			setSessionAttr("user", result.getUser());
		}
	}
    
    private int getLoginId() {
		try {
			User user = (User)getSessionAttr("user");
			if (user==null) {
				return -1;
			} else {
				return user.getId();
			}
		} catch (NullPointerException e) {
			System.err.println("no log before");
			return -1;
		}
    }
    
	public void logout() {
		removeSessionAttr("user");
		redirect("/log");		
	}

    // -----------------------------projects--------------------------
    public void index() {
    	setAttr("projects", projectManageservice.getProjects());
    	setAttr("risks", riskManageservice.getRiskNum());
    	render("index.html");
    }

    public void addProject() {
	    String name = getPara("name");
	    String desc = getPara("desc");
	    BaseResult result = projectManageservice.addProject(name, desc);
	    setAttr(ReturnConstants.result.toString(), result.getInfo());

	    renderJson();
    }
    
    public void deleteProject() {
    	int id = getParaToInt("id");
    	BaseResult result = projectManageservice.deleteProject(id);
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }

    public void modifyProject() {
    	int id = getParaToInt("id");
    	String name = getPara("name");
    	String desc = getPara("desc");
    	BaseResult result = projectManageservice.modifyProject(id, name, desc);

    	if (result==BaseResult.SUCCESS) {
    		int rid = getParaToInt("rid");
    		String role = getPara("role");
    		result = roleManageservice.modifyRole(rid, role);
		}
    	
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }
    
    // -----------------------------risk--------------------------
    public void risk() {
    	String id = getPara();
    	if (id==null||id.isEmpty()||!isInteger(id)) {
			redirect("/");
			return;
		}
    	ProjectVO project = projectManageservice.getProject(Integer.valueOf(id));
    	if (project!=null&&!project.isValid()) {//judge id ok 
			redirect("/");
			return;
		}
		setAttr("projects", projectManageservice.getProjects());
		setAttr("project", project.getProject());
		setAttr("risk", project.getRisks());
		setAttr("oldDate", project.getOldestDate());
		setAttr("recommands", project.getRecommand());
		setAttr("role", roleManageservice.getRole(getLoginId(), project.getProject().getId()));
		setAttr("plans", planManageservice.getPlans());

		render("risk.html");
		
    }
    
    private boolean isInteger(String str) {    
        Pattern pattern = Pattern.compile("^[\\d]*$");    
        return pattern.matcher(str).matches();
    }
    
    public void addRole() {
    	int uid = getLoginId();
    	int pid = getParaToInt("pid");
    	String role = getPara("role");
    	
    	BaseResult result = roleManageservice.addRole(uid,pid,role);
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }
    
    public void addRisk() {
    	int lid=getParaToInt("lid");
    	int state=getParaToInt("state");
    	String name=getPara("name");
    	int possibility=getParaToInt("possibility");
    	int damage=getParaToInt("damage");
    	String desc=getPara("desc");
    	String spy=getPara("spy");
    	String trigger=getPara("trigger");
    	String trailer=getPara("trailer");
    	String plan=getPara("plan");
    	int pid=getParaToInt("pid");
    	int uid=getLoginId();

    	BaseResult result = projectManageservice.addRisk(uid,pid,state,name,possibility,damage,desc,spy,trigger,trailer,plan,lid);
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }
    public void modifyRisk() {
    	int rid=getParaToInt("rid");
    	int state=getParaToInt("state");
    	String name=getPara("name");
    	int possibility=getParaToInt("possibility");
    	int damage=getParaToInt("damage");
    	String desc=getPara("desc");
    	String spy=getPara("spy");
    	String trigger=getPara("trigger");
    	String trailer=getPara("trailer");
    	String plan=getPara("plan");
    	
    	BaseResult result = projectManageservice.modifyRisk(rid,state,name,possibility,damage,desc,spy,trigger,trailer,plan);
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }
    
    public void deleteRisk() {
    	int id=getParaToInt("id");
    	BaseResult result = riskManageservice.deleteRisk(id);
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();

	}
    
    public void addTrail() {
    	int id=getParaToInt("id");
    	String desc=getPara("desc");
    	int state=getParaToInt("state");
    	
    	BaseResult result = projectManageservice.addTrail(id,desc,state,getLoginId());
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	renderJson();
    	
    }
    
    public void checkin() {
    	int pid=getParaToInt("pid");
    	int[] plans=toIntArr(getPara("plans"));
    	BaseResult result = projectManageservice.checkin(pid, plans, getLoginId());
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	renderJson();
    }
    
    
    
    // -----------------------------statistic--------------------------
    public void statistic() {
		setAttr("trail", riskManageservice.getAllError());
		setAttr("risk", riskManageservice.getAll());

		setAttr("oldestDate", projectManageservice.getOldestDate());
		setAttr("projects", projectManageservice.getProjects());
		render("statistic.html");

    }
// -----------------------------plan--------------------------
@Clear
	public void plan() {
UserVO result = userManageservice.login("qwe", "qwe");
setLogin(result);

		String id = getPara();
		if (id==null||id.isEmpty()){
			setAttr("allNum", riskManageservice.getNum());
			setAttr("projects", projectManageservice.getProjects());
			setAttr("plans", planManageservice.getPlans());
			render("plan.html");
			return;
		}
		
		if (!isInteger(id)) {
			redirect("/plan");
			return;
		}
		
		int pid = Integer.valueOf(id);
		PlanVO plan = planManageservice.getPlan(pid);
		if (plan!=null && pid!=0 && !plan.isValid()) {//judge id ok 
			redirect("/plan");
			return;
		}
		
		// pid==0 means show all
		setAttr("projects", projectManageservice.getProjects());
		setAttr("risk", plan.getRisks());
		setAttr("plans", planManageservice.getPlans());
		setAttr("plan", plan.getPlan());
		setAttr("oldDate", plan.getOldestDate());
		render("planDetail.html");
	}

	public void addPlan() {
	    String name = getPara("name");
	    String desc = getPara("desc");
	    BaseResult result = planManageservice.addPlan(name, desc);
	    setAttr(ReturnConstants.result.toString(), result.getInfo());
	
	    renderJson();
	}
	
	public void deletePlan() {
		int id = getParaToInt("id");
		BaseResult result = planManageservice.deletePlan(id);
		setAttr(ReturnConstants.result.toString(), result.getInfo());
		
		renderJson();
	}
	
	public void removeRisk() {
		int rid = getParaToInt("rid");
		int pid = getParaToInt("pid");
		BaseResult result = planManageservice.removeRisk(rid,pid);
		setAttr(ReturnConstants.result.toString(), result.getInfo());
		
		renderJson();
	}
	
	public void modifyPlan() {
		int id = getParaToInt("id");
		String name = getPara("name");
		String desc = getPara("desc");
		BaseResult result = planManageservice.modifyPlan(id, name, desc);
	
		setAttr(ReturnConstants.result.toString(), result.getInfo());
		
		renderJson();
	}
	
    public void addRiskInPlan() {
    	int state=getParaToInt("state");
    	String name=getPara("name");
    	int possibility=getParaToInt("possibility");
    	int damage=getParaToInt("damage");
    	String desc=getPara("desc");
    	String spy=getPara("spy");
    	String trigger=getPara("trigger");
    	String trailer=getPara("trailer");
    	String plan=getPara("plan");
    	int planId=getParaToInt("planId");
    	int uid=getLoginId();

    	BaseResult result = riskManageservice.addRiskInPlan(uid,planId,state,name,possibility,damage,desc,spy,trigger,trailer,plan);
    	
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	
    	renderJson();
    }

    public void adjustRisk(){
    	int[] rids=toIntArr(getPara("rids"));
    	int[] toPlan=toIntArr(getPara("toP"));
    	int fromPlan=getParaToInt("fromP");
    	String type=getPara("type");
    	if (fromPlan==0) {
			type="copy";
		}
    	BaseResult result = planManageservice.adjust(rids,toPlan,fromPlan,type);
    	
    	
    	setAttr(ReturnConstants.result.toString(), result.getInfo());
    	renderJson();
    }

	private int[] toIntArr(String str) {
		String[] s = str.split(",");
		int[] re = new int[s.length];
		for (int i = 0; i < s.length; i++) {
			re[i]=Integer.valueOf(s[i].trim());
		}
		return re;
	}

	
	    
}
