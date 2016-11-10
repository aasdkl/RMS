package rms.model;

import com.jfinal.aop.Before;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.tx.Tx;

import rms.model.base.BaseUser;

/**
 * User model.
+----------+-------------+------+-----+---------+----------------+
| Field    | Type        | Null | Key | Default | Extra          |
+----------+-------------+------+-----+---------+----------------+
| id       | int(12)     | NO   | PRI | NULL    | auto_increment |
| account  | varchar(20) | NO   |     | NULL    |                |
| password | varchar(20) | NO   |     | NULL    |                |
| name     | varchar(20) | YES  |     |         |                |
+----------+-------------+------+-----+---------+----------------+
 */
@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	public static final User dao = new User();

	public User login(String account, String password) {
		return dao.findFirst("select * from user where account = ? and password = md5(?)", account, password);
	}

	public boolean isExist(String account) {
		if (dao.findFirst("select * from user where account = ? ", account)!=null) {
			return true;
		}
		return false;
	}
	
	public User register(String account, String password) {
		int result = Db.update("insert into user(account, password, name) value (?, md5(?), ?)",account,password,account);
		if (result!=0) {
			return dao.login(account, password);
		} 
		return null;
	}
	
}
