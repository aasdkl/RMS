package rms.model;

import rms.model.base.BaseTrail;

/**
 * Trail model.
+---------+-------------+------+-----+-------------------+-----------------------------+
| Field   | Type        | Null | Key | Default           | Extra                       |
+---------+-------------+------+-----+-------------------+-----------------------------+
| id      | int(12)     | NO   | PRI | NULL              | auto_increment              |
| risk    | int(12)     | NO   | MUL | NULL              |                             |
| state   | int(1)      | NO   |     | NULL              |                             |
| content | varchar(20) | NO   |     | NULL              |                             |
| trailer | int(12)     | NO   | MUL | NULL              |                             |
| time    | timestamp   | NO   |     | CURRENT_TIMESTAMP | on update CURRENT_TIMESTAMP |
+---------+-------------+------+-----+-------------------+-----------------------------+
 */
@SuppressWarnings("serial")
public class Trail extends BaseTrail<Trail> {
	public static final Trail dao = new Trail();
}
