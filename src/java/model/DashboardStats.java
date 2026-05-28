package model;

public class DashboardStats
{
    // ATHLETE
    private int totalAthletes;
    private int pendingAthletes;
    private int approvedAthletes;
    private int rejectedAthletes;

    // COACH
    private int totalCoaches;
    private int pendingCoaches;
    private int approvedCoaches;
    private int rejectedCoaches;

    // ─────────────────────────
    // ATHLETE GETTERS
    // ─────────────────────────

    public int getTotalAthletes() {
        return totalAthletes;
    }

    public void setTotalAthletes(int totalAthletes) {
        this.totalAthletes = totalAthletes;
    }

    public int getPendingAthletes() {
        return pendingAthletes;
    }

    public void setPendingAthletes(int pendingAthletes) {
        this.pendingAthletes = pendingAthletes;
    }

    public int getApprovedAthletes() {
        return approvedAthletes;
    }

    public void setApprovedAthletes(int approvedAthletes) {
        this.approvedAthletes = approvedAthletes;
    }

    public int getRejectedAthletes() {
        return rejectedAthletes;
    }

    public void setRejectedAthletes(int rejectedAthletes) {
        this.rejectedAthletes = rejectedAthletes;
    }

    // ─────────────────────────
    // COACH GETTERS
    // ─────────────────────────

    public int getTotalCoaches() {
        return totalCoaches;
    }

    public void setTotalCoaches(int totalCoaches) {
        this.totalCoaches = totalCoaches;
    }

    public int getPendingCoaches() {
        return pendingCoaches;
    }

    public void setPendingCoaches(int pendingCoaches) {
        this.pendingCoaches = pendingCoaches;
    }

    public int getApprovedCoaches() {
        return approvedCoaches;
    }

    public void setApprovedCoaches(int approvedCoaches) {
        this.approvedCoaches = approvedCoaches;
    }

    public int getRejectedCoaches() {
        return rejectedCoaches;
    }

    public void setRejectedCoaches(int rejectedCoaches) {
        this.rejectedCoaches = rejectedCoaches;
    }
}