package com.thoughtworks.tools.tsc.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MismatchProperties implements Comparable<MismatchProperties>{

    private Date workday;
    private double hoursInTW;
    private double hoursInBill;

    public MismatchProperties(Date workday, double hoursInTW, double hoursInBill) {
        this.workday = workday;
        this.hoursInTW = hoursInTW;
        this.hoursInBill = hoursInBill;
    }

    public Date getWorkday() {
        return workday;
    }

    public void setWorkday(Date workday) {
        this.workday = workday;
    }

    public double getHoursInTW() {
        return hoursInTW;
    }

    public void setHoursInTW(double hoursInTW) {
        this.hoursInTW = hoursInTW;
    }

    public double getHoursInBill() {
        return hoursInBill;
    }

    public void setHoursInBill(double hoursInBill) {
        this.hoursInBill = hoursInBill;
    }

    @Override
    public String toString() {
        return "Mismatch{" +
                "Date=" + new SimpleDateFormat("yyyy-MM-dd").format(workday) +
                ", Hours in TW=" + hoursInTW +
                ", Hours in bill=" + hoursInBill +
                '}';
    }

    @Override
    public int compareTo(MismatchProperties o) {
        return workday.compareTo(o.workday);
    }
}
