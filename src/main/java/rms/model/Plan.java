package rms.model;

import java.util.List;

import rms.model.base.BasePlan;

/**
 * Plan model.
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int(12)      | NO   | PRI | NULL    | auto_increment |
| name    | varchar(20)  | NO   |     | NULL    |                |
|description
+---------+--------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Plan extends BasePlan<Plan> {
	public static final Plan dao = new Plan();

	public boolean add(String name, String desc) {
		return new Plan().set("name", name).set("description", desc).save();
	}
	
	public List<Plan> getAll() {
		return find("select * from plan");
	}

	public boolean modify(int id, String name, String desc) {
		return new Plan().findById(id).set("name", name).set("description", desc).update();
	}

	public List<Risk> getRisks() {
		return Risk.dao.find("select risk.* from risk,type where type.plan=? and type.risk=risk.id",get("id"));
	}

	
}
