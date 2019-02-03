package com.tupperware.mgt.entity.datawindow;

import com.tupperware.mgt.entity.BaseData;

import java.util.List;

/**
 * Created by umt041 on 2019/1/9.
 */
public class GetTargetKpiXyzResponse extends BaseData {

    private List<ModelBean> models;

    public List<ModelBean> getModels() {
        return models;
    }

    public void setModels(List<ModelBean> models) {
        this.models = models;
    }

    public static class ModelBean {
        /**
         * rowId : 1744528
         * flagYearMonth : Y2018M12
         * year : 2018
         * quarter : 4
         * month : 12
         * organNo : 00
         * organName : 鐗圭櫨鎯�
         * organType : TUP
         * organTypeName : 鍏徃
         * fieldName : net_amt
         * fieldDesc : 閿�鍞噣棰�
         * xyzType : 262
         * dateType : 鏈堢疮璁�
         * avgX : 45924.005419
         * avgY : 10894.430408
         * avgZ : 1098.360867
         * avgTotal : 15939.704268
         * updateDatetime : 1546258868000
         */

        private int rowId;
        private String flagYearMonth;
        private int year;
        private String quarter;
        private int month;
        private String organNo;
        private String organName;
        private String organType;
        private String organTypeName;
        private String fieldName;
        private String fieldDesc;
        private double avgX;
        private double avgY;
        private double avgZ;
        private double avgTotal;
        private long updateDatetime;

        public int getRowId() {
            return rowId;
        }

        public void setRowId(int rowId) {
            this.rowId = rowId;
        }

        public String getFlagYearMonth() {
            return flagYearMonth;
        }

        public void setFlagYearMonth(String flagYearMonth) {
            this.flagYearMonth = flagYearMonth;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getQuarter() {
            return quarter;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getOrganNo() {
            return organNo;
        }

        public void setOrganNo(String organNo) {
            this.organNo = organNo;
        }

        public String getOrganName() {
            return organName;
        }

        public void setOrganName(String organName) {
            this.organName = organName;
        }

        public String getOrganType() {
            return organType;
        }

        public void setOrganType(String organType) {
            this.organType = organType;
        }

        public String getOrganTypeName() {
            return organTypeName;
        }

        public void setOrganTypeName(String organTypeName) {
            this.organTypeName = organTypeName;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldDesc() {
            return fieldDesc;
        }

        public void setFieldDesc(String fieldDesc) {
            this.fieldDesc = fieldDesc;
        }

        public double getAvgX() {
            return avgX;
        }

        public void setAvgX(double avgX) {
            this.avgX = avgX;
        }

        public double getAvgY() {
            return avgY;
        }

        public void setAvgY(double avgY) {
            this.avgY = avgY;
        }

        public double getAvgZ() {
            return avgZ;
        }

        public void setAvgZ(double avgZ) {
            this.avgZ = avgZ;
        }

        public double getAvgTotal() {
            return avgTotal;
        }

        public void setAvgTotal(double avgTotal) {
            this.avgTotal = avgTotal;
        }

        public long getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(long updateDatetime) {
            this.updateDatetime = updateDatetime;
        }
    }
}
