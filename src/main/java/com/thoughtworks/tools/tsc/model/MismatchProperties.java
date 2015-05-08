package com.thoughtworks.tools.tsc.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MismatchProperties implements Comparable<MismatchProperties>{

    private Date workday;
    private double hoursInTW;
    private double hoursInBill;

    public MismatchProperties(Date workday, double hoursInTW, double hoursInTelstra) {
        this.workday = workday;
        this.hoursInTW = hoursInTW;
        this.hoursInBill = hoursInTelstra;
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

    public double getHoursInTelstra() {
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
                ", Hours in Telstra=" + hoursInBill +
                '}';
    }

    @Override
    public int compareTo(MismatchProperties o) {
        return workday.compareTo(o.workday);
    }
}
