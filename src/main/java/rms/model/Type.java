package rms.model;

import java.util.List;

import rms.model.base.BaseType;
import rms.vo.constant.BaseResult;

/**
 * Role model.
+---------+-------------+------+-----+---------+----------------+
| Field   | Type        | Null | Key | Default | Extra          |
+---------+-------------+------+-----+---------+----------------+
| id      | int(12)     | NO   | PRI | NULL    | auto_increment |
| risk    | int(12)     | NO   |  | NULL    |                |
| plan | int(12)     | NO   |  | NULL    |                |
+---------+-------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class Type extends BaseType<Type> {
	public static final Type dao = new Type();

	public boolean add(int rid, int pid) {
		new Type().set("risk", rid).set("plan", pid).save();
		Plan plan = new Plan().findById(pid);
		plan.setNum(plan.getNum()+1);
		return plan.update();
	}

	public Type find(int rid, int pid) {
		return dao.findFirst("select * from type where risk=? and plan=?",rid,pid);
	}
	
	public List<Type> findAllByPlan(int pid) {
		return dao.find("select * from type where plan=?",pid);
	}
	
	public List<Type> findAllByRisk(int rid) {
		return dao.find("select * from type where risk=?",rid);
	}

	public void deleteAllByRisk(int id) {
		List<Type> ls = new Type().findAllByRisk(id);
		for (Type type : ls) {
			Plan plan = new Plan().findById(type.getPlan());
			plan.setNum(plan.getNum()-1);
			plan.update();
			type.delete();
		}
	}

	public boolean delete(int rid, int pid) {
		Type type = new Type().find(rid,pid);
		boolean re = type.delete();
		Plan p = new Plan().findById(pid);
		p.set("num",p.getNum()-1).update();
		return re;
	}

	// only used in plan
	public boolean move(int rid, int fromPlan, int pid) {
		Type type = new Type().find(rid,fromPlan);
		boolean re = type.set("plan", pid).update();
		Plan p = new Plan().findById(fromPlan);
		p.set("num",p.getNum()-1).update();
		p = new Plan().findById(pid);
		p.set("num",p.getNum()+1).update();
		return re;
	}

}
