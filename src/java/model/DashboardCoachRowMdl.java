package model;

public class DashboardCoachRowMdl
{
    private long coachId;
    private String fullName;
    private String mobile;
    private String specialization;
    private String experience;
    private String status;
    private String coachingLevel;

    public long getCoachId() {
        return coachId;
    }

    public void setCoachId(long coachId) {
        this.coachId = coachId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCoachingLevel() {
    return coachingLevel;
}

public void setCoachingLevel(String coachingLevel) {
    this.coachingLevel = coachingLevel;
}
}