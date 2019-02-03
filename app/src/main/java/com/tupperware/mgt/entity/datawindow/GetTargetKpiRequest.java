package com.tupperware.mgt.entity.datawindow;

/**
 * 获取kpi
 */
public class GetTargetKpiRequest {

   private String dateType;
   private int year;
   private int month;


    public GetTargetKpiRequest(String dateType, int year, int month) {
        this.dateType = dateType;
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
