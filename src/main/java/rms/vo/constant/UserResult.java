package rms.vo.constant;

import com.jfinal.i18n.I18n;

/**
 * Validate the login information
 * @author Administrator
 *
 */
public enum UserResult {
	//TODO i18n needed
	SUCCESS(1, "成功"),
	PSW_ERROR(0, "密码错误"), 
	FORMAT_PASS(-1, "格式正确"), 
	PSW_AGAIN_ERROR(-2, "密码不一致"),
	PSW_FORMAT_ERROR(-3, "密码格式错误"), 
	ACC_FORMAT_ERROR(-4,"账户格式错误"), 
	ACC_EXIST(-5,"账户已存在"), 
	UNEXPECTED_ERROR(-999,"未知错误");

	private int type;
    private String info;

    UserResult(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
		return type;
	}

    public String getInfo() {
		return info;
	}
    
    public static UserResult typeOf(int type){
       for(UserResult each : values()) {
           if(each.getType() == type) {
               return each;
           }
       }
        return null;
    }

}
