package rms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseType<M extends BaseType<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setPlan(java.lang.Integer plan) {
		set("plan", plan);
	}

	public java.lang.Integer getPlan() {
		return get("plan");
	}

	public void setRisk(java.lang.Integer risk) {
		set("risk", risk);
	}

	public java.lang.Integer getRisk() {
		return get("risk");
	}

}
