package rms.controller;

import java.util.regex.Pattern;

import rms.model.Role;
import rms.model.User;
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
    private ProjectManagementServices projectManageservice = new ProjectManagementServicesImpl();
    private RoleManagementServices roleManageservice = new RoleManagementServicesImpl();
    private RiskManagementServices riskManageservice = new RiskManagementServicesImpl();

    // -----------------------------login--------------------------
    @Clear
    @Before(IndexInterceptor.class)
    public void log() {
		renderHtml("由于使用了数据库，准备使用一个tomcat容器，一个mysql容器<br>然而两个容器尚未link成功，所以首页无法显示<br>（我本来想容器内部可以调用宿主机的数据库的，可是遇到了许多坑……）");
//		render("login.html");
	}
    
    @Clear
    @Before(IndexInterceptor.class)
	public void login() {
	    String account = getPara("account");
	    String password = getPara("password");

	    UserVO result = userManageservice.login(account, password);
	    setAttr("result", result.getLoginResultInfo());
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
	    setAttr("result", result.getLoginResultInfo());
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
    	render("index.html");
    }

    public void addProject() {
	    String name = getPara("name");
	    String desc = getPara("desc");
	    BaseResult result = projectManageservice.addProject(name, desc);
	    setAttr("result", result.getInfo());

	    renderJson();
    }
    
    public void deleteProject() {
    	int id = getParaToInt("id");
    	BaseResult result = projectManageservice.deleteProject(id);
    	setAttr("result", result.getInfo());
    	
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
    	
    	setAttr("result", result.getInfo());
    	
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
    	if (!project.isValid()) {//judge id ok 
			redirect("/");
			return;
		}
		setAttr("projects", projectManageservice.getProjects());
		setAttr("project", project.getProject());
		setAttr("risk", project.getRisks());
		setAttr("role", roleManageservice.getRole(getLoginId(), project.getProject().getId()));
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
    	setAttr("result", result.getInfo());
    	
    	renderJson();
    }
}
