package rms.vo.constant;


public enum BaseResult {
	//TODO i18n needed
	SUCCESS(1, "成功"),
	UNEXPECTED_ERROR(-999,"未知错误");

	private int type;
    private String info;

    BaseResult(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public int getType() {
		return type;
	}

    public String getInfo() {
		return info;
	}
    
    public static BaseResult typeOf(int type){
       for(BaseResult each : values()) {
           if(each.getType() == type) {
               return each;
           }
       }
        return null;
    }

}
