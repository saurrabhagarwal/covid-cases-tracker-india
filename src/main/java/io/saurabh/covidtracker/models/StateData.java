package io.saurabh.covidtracker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateData {
    private String recordedDate;
    private String state;
    private Delta delta;
    private Delta delta7;
    private Delta total;

    public String getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(String recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }

    public Delta getDelta7() {
        return delta7;
    }

    public void setDelta7(Delta delta7) {
        this.delta7 = delta7;
    }

    public Delta getTotal() {
        return total;
    }

    public void setTotal(Delta total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "[" +
                "recordedDate='" + recordedDate + '\'' +
                ", state='" + state + '\'' +
                ", delta=" + delta +
                ", delta7=" + delta7 +
                ", total=" + total +
                ']';
    }

    public StateData(String recordedDate, String state, Delta delta, Delta delta7, Delta total) {
        this.recordedDate = recordedDate;
        this.state = state;
        this.delta = delta;
        this.delta7 = delta7;
        this.total = total;
    }

    public StateData() {
        super();
    }
}
