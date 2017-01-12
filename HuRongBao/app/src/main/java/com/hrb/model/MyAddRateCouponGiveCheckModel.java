package com.hrb.model;

import com.google.gson.annotations.SerializedName;

public class MyAddRateCouponGiveCheckModel extends BaseModel{
	 /**
     * USER_NAME : 王巧巧
     * MOBILE : 15578823747
     */

		@SerializedName("USER_NAME")
        private String USER_NAME;
		
		@SerializedName("MOBILE")
        private String MOBILE;

        public String getUSER_NAME() {
            return USER_NAME;
        }

        public void setUSER_NAME(String USER_NAME) {
            this.USER_NAME = USER_NAME;
        }

        public String getMOBILE() {
            return MOBILE;
        }

        public void setMOBILE(String MOBILE) {
            this.MOBILE = MOBILE;
        }
    
}
