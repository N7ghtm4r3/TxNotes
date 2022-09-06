package com.tecknobit.txnotes.records;

public class FetcherDetails {

    // TODO: 06/09/2022 USE BASE CLASS BY LIBRARY

    private final String fetcherPlatform;
    private final long runningFromDate;
    private long lastFetcherActivity;
    private String fetcherStatus;
    private int refreshTime;

    public FetcherDetails(long lastFetcherActivity, String fetcherStatus, String fetcherPlatform, int refreshTime, long runningFromDate) {
        this.lastFetcherActivity = lastFetcherActivity;
        this.fetcherStatus = fetcherStatus;
        this.fetcherPlatform = fetcherPlatform;
        this.refreshTime = refreshTime;
        this.runningFromDate = runningFromDate;
    }

    public long getLastFetcherActivity() {
        return lastFetcherActivity;
    }

    public void setLastFetcherActivity(long lastFetcherActivity) {
        this.lastFetcherActivity = lastFetcherActivity;
    }

    public String getFetcherStatus() {
        return fetcherStatus;
    }

    public void setFetcherStatus(String fetcherStatus) {
        this.fetcherStatus = fetcherStatus;
    }

    public String getFetcherPlatform() {
        return fetcherPlatform;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public long getRunningFromDate() {
        return runningFromDate;
    }

}
