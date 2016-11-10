package rms.config;

import rms.model._MappingKit;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import rms.controller.MainController;

public class WebConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants constants) {
		PropKit.use("config.properties");
		
		constants.setDevMode(PropKit.getBoolean("devMode", false));
		constants.setBaseViewPath("html");
		constants.setJsonFactory(new FastJsonFactory()); // using fast json lib
	}

	@Override
	public void configRoute(Routes routes) {
		routes.add("/", MainController.class);
	}
	
	@Override
	public void configPlugin(Plugins plugins) {
		C3p0Plugin cp = new C3p0Plugin(PropKit.get("jdbc"), PropKit.get("user"), PropKit.get("password"));
		plugins.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		arp.setShowSql(PropKit.getBoolean("showSql", false));
		arp.setDialect(new MysqlDialect());
		_MappingKit.mapping(arp);
		plugins.add(arp);
	}

	@Override
	public void configHandler(Handlers handlers) {
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
	}

	
}
