package rms.model;

import rms.model.base.BaseRole;

/**
 * Role model.
+---------+-------------+------+-----+---------+----------------+
| Field   | Type        | Null | Key | Default | Extra          |
+---------+-------------+------+-----+---------+----------------+
| id      | int(12)     | NO   | PRI | NULL    | auto_increment |
| user    | int(12)     | NO   | MUL | NULL    |                |
| project | int(12)     | NO   | MUL | NULL    |                |
| role    | varchar(20) | NO   |     | NULL    |                |
+---------+-------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Role extends BaseRole<Role> {
	public static final Role dao = new Role();

	public boolean add(int uid, int pid, String role) {
		return new Role().set("user", uid).set("project", pid).set("role", role).save();
	}

	public Role find(int uid, int pid) {
		return dao.findFirst("select * from role where user=? and project=?",uid,pid);
	}

	public boolean modify(int rid, String role) {
		return new Role().findById(rid).set("role", role).update();
	}
}
