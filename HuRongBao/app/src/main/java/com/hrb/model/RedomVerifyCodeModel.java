package com.hrb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hrb.utils.android.QuickSetParcelableUtil;


public class RedomVerifyCodeModel extends BaseModel implements Parcelable {
	
	@SerializedName("CODE")
	private String code;

	@SerializedName("BYTE")
	private String byte_content;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getByteContent() {
		return byte_content;
	}

	public void setByteContent(String byte_content) {
		this.byte_content = byte_content;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		QuickSetParcelableUtil.write(dest, this);
	}

	public static final Creator<RedomVerifyCodeModel> CREATOR = new Creator<RedomVerifyCodeModel>() {

		@Override
		public RedomVerifyCodeModel createFromParcel(Parcel source) {
			RedomVerifyCodeModel obj = (RedomVerifyCodeModel) QuickSetParcelableUtil
					.read(source, RedomVerifyCodeModel.class);
			return obj;
		}

		@Override
		public RedomVerifyCodeModel[] newArray(int size) {
			return new RedomVerifyCodeModel[size];
		}

	};
}