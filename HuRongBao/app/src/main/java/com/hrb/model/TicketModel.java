package com.hrb.model;

import com.google.gson.annotations.SerializedName;

public class TicketModel extends BaseModel{
	/**
	 * 加息券的唯一id标识
	 */
	@SerializedName("RATE_COUPON_ID")
	private String id;
	/**
	 * 加息券利率
	 */
	@SerializedName("RATE_COUPON_POSITION")
	private String rate;
	
	/**
	 * 有效期
	 */
	@SerializedName("END_DATE")
	private String end_Time;
	
	/**
	 * 投资多少钱才可以用
	 */
	@SerializedName("CONDITIONS")
	private String investMoney;
	
	
	public String getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(String investMoney) {
		this.investMoney = investMoney;
	}

	/**
	 * 可投标期限（不用了）
	 */
	@SerializedName("time_Limit")
	private String time_Limit;
	
	/**
	 * 用于
	 */
	private String be_Use_For;
	
	/**
	 * 来源
	 */
	private String from;
	
	/**
	 * 使用时间
	 */
	private String use_Time;
	
	/**
	 * 受赠人手机
	 */
	private String tele_Num;
	
	/**
	 * 赠送时间
	 */
	private String give_Time;
	
	
	
	
	
	public String getGive_Time() {
		return give_Time;
	}

	public void setGive_Time(String give_Time) {
		this.give_Time = give_Time;
	}

	public String getTele_Num() {
		return tele_Num;
	}

	public void setTele_Num(String tele_Num) {
		this.tele_Num = tele_Num;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getUse_Time() {
		return use_Time;
	}

	public void setUse_Time(String use_Time) {
		this.use_Time = use_Time;
	}

	public String getBe_Use_For() {
		return be_Use_For;
	}

	public void setBe_Use_For(String be_Use_For) {
		this.be_Use_For = be_Use_For;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(String end_Time) {
		this.end_Time = end_Time;
	}

	public String getTime_Limit() {
		return time_Limit;
	}

	public void setTime_Limit(String time_Limit) {
		this.time_Limit = time_Limit;
	}
	
}
