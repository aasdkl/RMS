package rms.model;
import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**
 * Model Generator, a tool to generate the Model and DTO
 */
public class ModelGenerator {
	
	// link database
	public static DataSource getDataSource() {
		Prop p = PropKit.use("config.properties", "utf-8");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(p.get("jdbc"), p.get("user"), p.get("password"));
		c3p0Plugin.start();
		return c3p0Plugin.getDataSource();
	}
	
	public static void main(String[] args) {
		String baseModelPackageName = "rms.model.base";
		String baseModelOutputDir = PathKit.getWebRootPath()+"/../src/rms/model/base";
		
		String modelPackageName = "rms.model";
		String modelOutputDir = baseModelOutputDir + "/..";
		
		Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		gernerator.setDialect(new MysqlDialect());
		gernerator.setGenerateDaoInModel(true);
		gernerator.setGenerateDataDictionary(false);
		gernerator.generate();
	}
}




