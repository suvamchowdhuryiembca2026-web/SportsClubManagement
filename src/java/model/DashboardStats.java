package model;

public class DashboardStats
{
    private int totalAthletes;
    private int pending;
    private int approved;
    private int totalSports;

    public int getTotalAthletes() {
        return totalAthletes;
    }

    public void setTotalAthletes(int totalAthletes) {
        this.totalAthletes = totalAthletes;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    public int getTotalSports() {
        return totalSports;
    }

    public void setTotalSports(int totalSports) {
        this.totalSports = totalSports;
    }
}