package com.tupperware.mgt.entity.datawindow;

import com.tupperware.mgt.entity.BaseData;

import java.util.List;

/**
 *
 */
public class KpiYMResponse extends BaseData{


    private List<ModelsBean> models;

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ModelsBean {
        /**
         * year : 2018
         * month : 12
         * kpi : actual_amt
         * monthTrueAmt : 2.536017858E8
         * monthDiffAmt : -1.765679802E8
         * monthPlanAmt : 430169766
         * monthPreAmt : 58.95
         * quarterTrueAmt : 9.9799314709E8
         * quarterDiffAmt : -3.1731359491E8
         * quarterPlanAmt : 1315306742
         * quarterPreAmt : 75.88
         * updateDatetime : 1546174636000
         */

        private int year;
        private int month;
        private String kpi;
        private double monthTrueAmt;
        private double monthDiffAmt;
        private double monthPlanAmt;
        private double monthPreAmt;
        private double quarterTrueAmt;
        private double quarterDiffAmt;
        private double quarterPlanAmt;
        private double quarterPreAmt;
        private long updateDatetime;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public String getKpi() {
            return kpi;
        }

        public void setKpi(String kpi) {
            this.kpi = kpi;
        }

        public double getMonthTrueAmt() {
            return monthTrueAmt;
        }

        public void setMonthTrueAmt(double monthTrueAmt) {
            this.monthTrueAmt = monthTrueAmt;
        }

        public double getMonthDiffAmt() {
            return monthDiffAmt;
        }

        public void setMonthDiffAmt(double monthDiffAmt) {
            this.monthDiffAmt = monthDiffAmt;
        }

        public double getMonthPlanAmt() {
            return monthPlanAmt;
        }

        public void setMonthPlanAmt(double monthPlanAmt) {
            this.monthPlanAmt = monthPlanAmt;
        }

        public double getMonthPreAmt() {
            return monthPreAmt;
        }

        public void setMonthPreAmt(double monthPreAmt) {
            this.monthPreAmt = monthPreAmt;
        }

        public double getQuarterTrueAmt() {
            return quarterTrueAmt;
        }

        public void setQuarterTrueAmt(double quarterTrueAmt) {
            this.quarterTrueAmt = quarterTrueAmt;
        }

        public double getQuarterDiffAmt() {
            return quarterDiffAmt;
        }

        public void setQuarterDiffAmt(double quarterDiffAmt) {
            this.quarterDiffAmt = quarterDiffAmt;
        }

        public double getQuarterPlanAmt() {
            return quarterPlanAmt;
        }

        public void setQuarterPlanAmt(double quarterPlanAmt) {
            this.quarterPlanAmt = quarterPlanAmt;
        }

        public double getQuarterPreAmt() {
            return quarterPreAmt;
        }

        public void setQuarterPreAmt(double quarterPreAmt) {
            this.quarterPreAmt = quarterPreAmt;
        }

        public long getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(long updateDatetime) {
            this.updateDatetime = updateDatetime;
        }
    }
}
