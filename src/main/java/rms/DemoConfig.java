package rms;

import com.jfinal.config.*;

public class DemoConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
	}

	public void configRoute(Routes me) {
		me.add("/", HelloController.class);
	}

	public void configPlugin(Plugins me) {
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}
}