package com.bigHead;

import java.util.Date;

public class SafetyEntity {
    private Long id;
    private String companyName;
    private String policyNumber;
    private String safeNumer;
    private Date activeDate;
    private Date writeDate;
    private Double protectMoney;
    private String sheetName;
    private Integer month;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getSafeNumer() {
        return safeNumer;
    }

    public void setSafeNumer(String safeNumer) {
        this.safeNumer = safeNumer;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Double getProtectMoney() {
        return protectMoney;
    }

    public void setProtectMoney(Double protectMoney) {
        this.protectMoney = protectMoney;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SafetyEntity that = (SafetyEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
