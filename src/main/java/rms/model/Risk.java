package rms.model;

import rms.model.base.BaseRisk;

/**
 * Risk model.
+-------------+-------------+------+-----+---------+----------------+
| Field       | Type        | Null | Key | Default | Extra          |
+-------------+-------------+------+-----+---------+----------------+
| id          | int(12)     | NO   | PRI | NULL    | auto_increment |
| title       | varchar(20) | NO   |     | NULL    |                |
| state       | int(1)      | NO   |     | NULL    |                |
| style       | varchar(20) | YES  |     | NULL    |                |
| content     | varchar(20) | NO   |     | NULL    |                |
| probability | int(1)      | NO   |     | NULL    |                |
| effect      | int(1)      | NO   |     | NULL    |                |
| spy         | varchar(20) | NO   |     | NULL    |                |
| avoid       | varchar(20) | NO   |     | NULL    |                |
| switcher    | varchar(20) | NO   |     | NULL    |                |
| handle      | varchar(20) | NO   |     | NULL    |                |
| publisher   | int(12)     | NO   | MUL | NULL    |                |
| trailer     | int(12)     | NO   | MUL | NULL    |                |
| project     | int(12)     | NO   | MUL | NULL    |                |
+-------------+-------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Risk extends BaseRisk<Risk> {
	public static final Risk dao = new Risk();

	public boolean add(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan) {
		return new Risk().set("title", name).set("state", state).set("content", desc)
				.set("probability", possibility).set("effect", damage).set("spy", spy)
				.set("avoid", "").set("switcher", trigger).set("handle", plan).set("trailer", trailer)
				.set("project", pid).set("publisher", uid).save();
	}

}
