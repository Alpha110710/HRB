package com.hrb.model;

public class GetUserTransferInfoModel extends BaseModel{

   

    /**
     * PRODUCTS_TITLE : 担保贷-投资理财计划XXHB085001
     * FAIR_VALUE : 1007.26
     * ENABLE_AMOUNT : 1000.00
     * PERIOD : 24
     * FEE_RATE : 2
     */

        private String PRODUCTS_TITLE;
        private String FAIR_VALUE;
        private String ENABLE_AMOUNT;
        private String PERIOD;
        private String FEE_RATE;

        public String getPRODUCTS_TITLE() {
            return PRODUCTS_TITLE;
        }

        public void setPRODUCTS_TITLE(String PRODUCTS_TITLE) {
            this.PRODUCTS_TITLE = PRODUCTS_TITLE;
        }

        public String getFAIR_VALUE() {
            return FAIR_VALUE;
        }

        public void setFAIR_VALUE(String FAIR_VALUE) {
            this.FAIR_VALUE = FAIR_VALUE;
        }

        public String getENABLE_AMOUNT() {
            return ENABLE_AMOUNT;
        }

        public void setENABLE_AMOUNT(String ENABLE_AMOUNT) {
            this.ENABLE_AMOUNT = ENABLE_AMOUNT;
        }

        public String getPERIOD() {
            return PERIOD;
        }

        public void setPERIOD(String PERIOD) {
            this.PERIOD = PERIOD;
        }

        public String getFEE_RATE() {
            return FEE_RATE;
        }

        public void setFEE_RATE(String FEE_RATE) {
            this.FEE_RATE = FEE_RATE;
        }

}
