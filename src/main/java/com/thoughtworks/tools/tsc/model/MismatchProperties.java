package com.thoughtworks.tools.tsc.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MismatchProperties implements Comparable<MismatchProperties>{

    private Date workday;
    private double hoursInTW;
    private double hoursInTelstra;

    public MismatchProperties(Date workday, double hoursInTW, double hoursInTelstra) {
        this.workday = workday;
        this.hoursInTW = hoursInTW;
        this.hoursInTelstra = hoursInTelstra;
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
        return hoursInTelstra;
    }

    public void setHoursInTelstra(double hoursInTelstra) {
        this.hoursInTelstra = hoursInTelstra;
    }

    @Override
    public String toString() {
        return "Mismatch{" +
                "Date=" + new SimpleDateFormat("yyyy-MM-dd").format(workday) +
                ", Hours in TW=" + hoursInTW +
                ", Hours in Telstra=" + hoursInTelstra +
                '}';
    }

    @Override
    public int compareTo(MismatchProperties o) {
        return workday.compareTo(o.workday);
    }
}
