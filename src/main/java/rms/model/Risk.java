package rms.model;

import java.util.List;

import rms.model.base.BaseRisk;

/**
 * Risk model.
+-------------+-----------+------+-----+-------------------+-----------------------------+
| Field       | Type      | Null | Key | Default           | Extra     |
+-------------+-----------+------+-----+-------------------+-----------------------------+
| id          | int(12)   | NO   | PRI | NULL              | auto_increment     |
| title       | text      | YES  |     | NULL              |     |
| state       | int(1)    | NO   |     | NULL              |     |
| style       | text      | YES  |     | NULL              |     |
| content     | text      | YES  |     | NULL              |     |
| probability | int(1)    | NO   |     | NULL              |     |
| effect      | int(1)    | NO   |     | NULL              |     |
| spy         | text      | YES  |     | NULL              |     |
| avoid       | text      | YES  |     | NULL              |     |
| switcher    | text      | YES  |     | NULL              |     |
| handle      | text      | YES  |     | NULL              |     |
| publisher   | int(12)   | NO   | MUL | NULL              |     |
| trailer     | text      | YES  |     | NULL              |     |
| project     | int(12)   | NO   | MUL | NULL              |     |
| time        | timestamp | NO   |     | CURRENT_TIMESTAMP |     |
| baseRisk    | int(12)   | NO   |     | NULL              |     |
+-------------+-----------+------+-----+-------------------+-----------------------------+
 */
@SuppressWarnings("serial")
public class Risk extends BaseRisk<Risk> {
	public static final Risk dao = new Risk();

	public boolean add(int uid, int pid, int state, String name,
			int possibility, int damage, String desc, String spy,
			String trigger, String trailer, String plan, int lid) {
		if (lid!=0) {
			Risk risk = new Risk().findById(lid);
			risk.set("baseRisk", risk.getId()).update();
		}
		return new Risk().set("title", name).set("state", state).set("content", desc)
				.set("probability", possibility).set("effect", damage).set("spy", spy)
				.set("avoid", "").set("switcher", trigger).set("handle", plan).set("trailer", trailer)
				.set("project", pid).set("publisher", uid).set("baseRisk", lid).save();
	}

	public boolean modify(int rid, int state, String name, int possibility,
			int damage, String desc, String spy, String trigger,
			String trailer, String plan) {
		return new Risk().findById(rid).set("title", name).set("state", state).set("content", desc)
				.set("probability", possibility).set("effect", damage).set("spy", spy)
				.set("avoid", "").set("switcher", trigger).set("handle", plan).set("trailer", trailer)
				.update();
	}

	public List<Risk> recommand(int projectId) {
		int n = 3;
		List<Risk> r = dao.find("select * from risk where project!=? and baseRisk!=0 group by baseRisk", projectId);
		for (int i=r.size()-1; i>=0; i--) {
			int base=r.get(i).getBaseRisk();
			if (dao.find("select * from risk where project=? and baseRisk=?", projectId, base).size()!=0) {
				r.remove(i);
			}
		}
		n-=r.size();
		if (n>0) {
			r.addAll(dao.find("select * from risk where project!=? and baseRisk=0 order by probability + effect limit ?",projectId, n));
		} else {
			r=r.subList(0, 3);
		}
		return r;
	}

}
