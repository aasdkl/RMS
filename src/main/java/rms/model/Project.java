package rms.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rms.model.base.BaseProject;

/**
 * Project model.
+---------+--------------+------+-----+---------+----------------+
| Field   | Type         | Null | Key | Default | Extra          |
+---------+--------------+------+-----+---------+----------------+
| id      | int(12)      | NO   | PRI | NULL    | auto_increment |
| name    | varchar(20)  | NO   |     | NULL    |                |
+---------+--------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Project extends BaseProject<Project> {
	public static final Project dao = new Project();

	public boolean add(String name, String desc) {
		return new Project().set("name", name).set("description", desc).save();
	}

	public List<Project> getAll() {
		return find("select * from project");
	}

	public boolean modify(int id, String name, String desc) {
		return new Project().findById(id).set("name", name).set("description", desc).update();
	}

	public List<Risk> getRisks() {
		return Risk.dao.find("select * from risk where project=?",get("id"));
	}
	public List<Integer> getRisksIds() {
		List<Integer> l = new ArrayList<Integer>();
		List<Risk> list = getRisks();
		for (Risk risk : list) {
			l.add(risk.getId());
			if (risk.getBaseRisk()!=0) {
				l.add(risk.getBaseRisk());
			}
		}
		Collections.sort(l);
		return l;
	}

	
}
