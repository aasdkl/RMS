package rms.config;

import rms.model._MappingKit;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;

import rms.controller.MainController;

public class WebConfig extends JFinalConfig{
	@Override
	public void configConstant(Constants constants) {
		
		constants.setBaseViewPath("html");
		constants.setJsonFactory(new FastJsonFactory()); // using fast json lib
	}

	@Override
	public void configRoute(Routes routes) {
		routes.add("/", MainController.class);
	}
	
	@Override
	public void configPlugin(Plugins plugins) {
		DruidPlugin cp = new DruidPlugin("jdbc:mysql://172.17.0.2:3306/risk?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "root", "");
		plugins.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
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
