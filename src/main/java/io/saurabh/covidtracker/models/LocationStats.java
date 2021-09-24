package io.saurabh.covidtracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int latestTotalCases;
    private int preDayCasesDiff;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public void setPreDayCasesDiff(int preDayCases) {
        this.preDayCasesDiff = preDayCases;
    }

    public int getPreDayCasesDiff() {
        return preDayCasesDiff;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                ", preDayCases=" + preDayCasesDiff +
                '}';
    }
}
