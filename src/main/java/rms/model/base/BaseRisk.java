package rms.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRisk<M extends BaseRisk<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setProbability(java.lang.Integer probability) {
		set("probability", probability);
	}

	public java.lang.Integer getProbability() {
		return get("probability");
	}

	public void setEffect(java.lang.Integer effect) {
		set("effect", effect);
	}

	public java.lang.Integer getEffect() {
		return get("effect");
	}

	public void setSpy(java.lang.String spy) {
		set("spy", spy);
	}

	public java.lang.String getSpy() {
		return get("spy");
	}

	public void setAvoid(java.lang.String avoid) {
		set("avoid", avoid);
	}

	public java.lang.String getAvoid() {
		return get("avoid");
	}

	public void setSwitcher(java.lang.String switcher) {
		set("switcher", switcher);
	}

	public java.lang.String getSwitcher() {
		return get("switcher");
	}

	public void setHandle(java.lang.String handle) {
		set("handle", handle);
	}

	public java.lang.String getHandle() {
		return get("handle");
	}

	public void setPublisher(java.lang.Integer publisher) {
		set("publisher", publisher);
	}

	public java.lang.Integer getPublisher() {
		return get("publisher");
	}

	public void setTrailer(java.lang.String trailer) {
		set("trailer", trailer);
	}

	public java.lang.String getTrailer() {
		return get("trailer");
	}

	public void setProject(java.lang.Integer project) {
		set("project", project);
	}

	public java.lang.Integer getProject() {
		return get("project");
	}
	
	public void setBaseRisk(java.lang.Integer baseRisk) {
		set("baseRisk", baseRisk);
	}

	public java.lang.Integer getBaseRisk() {
		return get("baseRisk");
	}

	public void setTime(java.util.Date time) {
		set("time", time);
	}

	public java.util.Date getTime() {
		return get("time");
	}

}
