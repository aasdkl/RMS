package rms.interceptor;

import rms.model.User;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class IndexInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		try {
			User user = (User)controller.getSessionAttr("user");
			if (user!=null) {
				backToIndex(controller);
			} else {
				inv.invoke();
			}
		} catch (NullPointerException e) {
			inv.invoke();
		}
	}

	private void backToIndex(Controller controller) {
		controller.redirect("/");
	}
}
