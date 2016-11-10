package rms.interceptor;

import rms.model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		try {
			User user = (User)controller.getSessionAttr("user");
			if (user==null) {
				backToLogin(controller);
			} else {
				inv.invoke();
			}
		} catch (NullPointerException e) {
			backToLogin(controller);
		}
	}

	private void backToLogin(Controller controller) {
		controller.redirect("/log");
	}
}
