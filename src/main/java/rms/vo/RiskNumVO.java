package rms.vo;

import java.io.Serializable;

import com.jfinal.json.FastJson;

public class RiskNumVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int high=0;
	private int mid=0;
	private int low=0;
	
	public void setAll(int high,int mid,int low){
		this.high=high;
		this.mid=mid;
		this.low=low;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}

	
}
