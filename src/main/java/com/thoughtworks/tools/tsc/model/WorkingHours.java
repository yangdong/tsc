package com.thoughtworks.tools.tsc.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkingHours implements Serializable,Comparable<WorkingHours> {

    private Date workday;
    private double workHours;

    public WorkingHours(Date workday, double workHours) {
        this.workday = workday;
        this.workHours = workHours;
    }

    public Date getWorkday() {
        return workday;
    }

    public void setWorkday(Date workday) {
        this.workday = workday;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return "WorkingHours{" +
                "workday=" + new SimpleDateFormat("yyyy-MM-dd E").format(workday) +
                ", workHours=" + workHours +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkingHours that = (WorkingHours) o;

        return !(workday != null ? !workday.equals(that.workday) : that.workday != null);

    }

    @Override
    public int hashCode() {
        return workday != null ? workday.hashCode() : 0;
    }

    @Override
    public int compareTo(WorkingHours o) {
        return workday.compareTo(o.workday);
    }
}
