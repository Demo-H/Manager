package com.tupperware.mgt.entity.datawindow;

import com.tupperware.mgt.entity.BaseData;

import java.util.List;

/**
 * Created by umt041 on 2019/1/9.
 */
public class GetTargetKpiResponse extends BaseData {

    private ExtraBean extra;
    private List<ModelsBean> models;

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public List<ModelsBean> getModels() {
        return models;
    }

    public void setModels(List<ModelsBean> models) {
        this.models = models;
    }

    public static class ExtraBean {
        /**
         * month : 1
         * year : 2019
         */

        private int month;
        private int year;

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class ModelsBean {
        private int year;
        private int month;
        private String dateType;
        private String kpi;
        private String kpiDesc;
        private long updateDatetime;
        private List<AnlVKpiStatusRptListBean> anlVKpiStatusRptList;

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

        public String getDateType() {
            return dateType;
        }

        public void setDateType(String dateType) {
            this.dateType = dateType;
        }

        public String getKpi() {
            return kpi;
        }

        public void setKpi(String kpi) {
            this.kpi = kpi;
        }

        public String getKpiDesc() {
            return kpiDesc;
        }

        public void setKpiDesc(String kpiDesc) {
            this.kpiDesc = kpiDesc;
        }

        public long getUpdateDatetime() {
            return updateDatetime;
        }

        public void setUpdateDatetime(long updateDatetime) {
            this.updateDatetime = updateDatetime;
        }

        public List<AnlVKpiStatusRptListBean> getAnlVKpiStatusRptList() {
            return anlVKpiStatusRptList;
        }

        public void setAnlVKpiStatusRptList(List<AnlVKpiStatusRptListBean> anlVKpiStatusRptList) {
            this.anlVKpiStatusRptList = anlVKpiStatusRptList;
        }

        public static class AnlVKpiStatusRptListBean {
            private Object rowId;
            private int year;
            private int month;
            private Object flagYearMonth;
            private String dateType;
            private String organNo;
            private String organName;
            private String organType;
            private String kpi;
            private String kpiDesc;
            private double trueAmt;
            private double diffAmt;
            private double planAmt;
            private double preAmt;
            private Object createDatetime;
            private long updateDatetime;

            public Object getRowId() {
                return rowId;
            }

            public void setRowId(Object rowId) {
                this.rowId = rowId;
            }

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

            public Object getFlagYearMonth() {
                return flagYearMonth;
            }

            public void setFlagYearMonth(Object flagYearMonth) {
                this.flagYearMonth = flagYearMonth;
            }

            public String getDateType() {
                return dateType;
            }

            public void setDateType(String dateType) {
                this.dateType = dateType;
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

            public String getKpi() {
                return kpi;
            }

            public void setKpi(String kpi) {
                this.kpi = kpi;
            }

            public String getKpiDesc() {
                return kpiDesc;
            }

            public void setKpiDesc(String kpiDesc) {
                this.kpiDesc = kpiDesc;
            }

            public double getTrueAmt() {
                return trueAmt;
            }

            public void setTrueAmt(double trueAmt) {
                this.trueAmt = trueAmt;
            }

            public double getDiffAmt() {
                return diffAmt;
            }

            public void setDiffAmt(double diffAmt) {
                this.diffAmt = diffAmt;
            }

            public double getPlanAmt() {
                return planAmt;
            }

            public void setPlanAmt(double planAmt) {
                this.planAmt = planAmt;
            }

            public double getPreAmt() {
                return preAmt;
            }

            public void setPreAmt(double preAmt) {
                this.preAmt = preAmt;
            }

            public Object getCreateDatetime() {
                return createDatetime;
            }

            public void setCreateDatetime(Object createDatetime) {
                this.createDatetime = createDatetime;
            }

            public long getUpdateDatetime() {
                return updateDatetime;
            }

            public void setUpdateDatetime(long updateDatetime) {
                this.updateDatetime = updateDatetime;
            }
        }
    }
}
