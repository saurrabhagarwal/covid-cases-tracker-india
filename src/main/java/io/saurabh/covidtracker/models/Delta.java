package io.saurabh.covidtracker.models;

public class Delta {
    private Object confirmed = 0;
    private Object recovered = 0;
    private Object deceased = 0;
    private Object tested = 0;
    private Object vaccinated1 = 0;
    private Object vaccinated2 = 0;
    private Object other = 0;

    public Object getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Object confirmed) {
        this.confirmed = confirmed;
    }

    public Object getRecovered() {
        return recovered;
    }

    public void setRecovered(Object recovered) {
        this.recovered = recovered;
    }

    public Object getDeceased() {
        return deceased;
    }

    public void setDeceased(Object deceased) {
        this.deceased = deceased;
    }

    public Object getTested() {
        return tested;
    }

    public void setTested(Object tested) {
        this.tested = tested;
    }

    public Object getVaccinated1() {
        return vaccinated1;
    }

    public void setVaccinated1(Object vaccinated1) {
        this.vaccinated1 = vaccinated1;
    }

    public Object getVaccinated2() {
        return vaccinated2;
    }

    public void setVaccinated2(Object vaccinated2) {
        this.vaccinated2 = vaccinated2;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

    public Delta(Object confirmed, Object recovered, Object deceased, Object tested, Object vaccinated1, Object vaccinated2, Object other) {
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deceased = deceased;
        this.tested = tested;
        this.vaccinated1 = vaccinated1;
        this.vaccinated2 = vaccinated2;
        this.other = other;
    }

    public Delta() {
        super();
    }

    @Override
    public String toString() {
        return "[" +
                "confirmed=" + confirmed +
                ", recovered=" + recovered +
                ", deceased=" + deceased +
                ", tested=" + tested +
                ", vaccinated1=" + vaccinated1 +
                ", vaccinated2=" + vaccinated2 +
                ", other=" + other +
                ']';
    }
}
