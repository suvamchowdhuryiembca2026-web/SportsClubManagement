package model;

public class Coach {

    private long   coachId;
    private String fullName;
    private String email;
    private String mobile;
    private int    experience;
    private String specialization;
    private String coachingLevel;
    private String qualification;
    private String clubName;
    private String availability;
    private String achievements;
    private String state;
    private String district;
    private String city;
    private String pinCode;
    private String fullAddress;
    private String gender;
    private String dob;
    private String bloodGroup;
    private String photoPath;
    private String certificatePath;
    private String idProofPath;
    private String qualDocPath;
    private String status;
    private String createdAt;
    private String rejectionReason;

    public long getCoachId()                          { return coachId; }
    public void setCoachId(long coachId)              { this.coachId = coachId; }

    public String getFullName()                       { return fullName; }
    public void setFullName(String fullName)          { this.fullName = fullName; }

    public String getEmail()                          { return email; }
    public void setEmail(String email)                { this.email = email; }

    public String getMobile()                         { return mobile; }
    public void setMobile(String mobile)              { this.mobile = mobile; }

    public int getExperience()                        { return experience; }
    public void setExperience(int experience)         { this.experience = experience; }

    public String getSpecialization()                 { return specialization; }
    public void setSpecialization(String s)           { this.specialization = s; }

    public String getCoachingLevel()                  { return coachingLevel; }
    public void setCoachingLevel(String coachingLevel){ this.coachingLevel = coachingLevel; }

    public String getQualification()                  { return qualification; }
    public void setQualification(String qualification){ this.qualification = qualification; }

    public String getClubName()                       { return clubName; }
    public void setClubName(String clubName)          { this.clubName = clubName; }

    public String getAvailability()                   { return availability; }
    public void setAvailability(String availability)  { this.availability = availability; }

    public String getAchievements()                   { return achievements; }
    public void setAchievements(String achievements)  { this.achievements = achievements; }

    public String getState()                          { return state; }
    public void setState(String state)                { this.state = state; }

    public String getDistrict()                       { return district; }
    public void setDistrict(String district)          { this.district = district; }

    public String getCity()                           { return city; }
    public void setCity(String city)                  { this.city = city; }

    public String getPinCode()                        { return pinCode; }
    public void setPinCode(String pinCode)            { this.pinCode = pinCode; }

    public String getFullAddress()                    { return fullAddress; }
    public void setFullAddress(String fullAddress)    { this.fullAddress = fullAddress; }

    public String getGender()                         { return gender; }
    public void setGender(String gender)              { this.gender = gender; }

    public String getDob()                            { return dob; }
    public void setDob(String dob)                    { this.dob = dob; }

    public String getBloodGroup()                     { return bloodGroup; }
    public void setBloodGroup(String bloodGroup)      { this.bloodGroup = bloodGroup; }

    public String getPhotoPath()                      { return photoPath; }
    public void setPhotoPath(String photoPath)        { this.photoPath = photoPath; }

    public String getCertificatePath()                { return certificatePath; }
    public void setCertificatePath(String p)          { this.certificatePath = p; }

    public String getIdProofPath()                    { return idProofPath; }
    public void setIdProofPath(String idProofPath)    { this.idProofPath = idProofPath; }

    public String getQualDocPath()                    { return qualDocPath; }
    public void setQualDocPath(String qualDocPath)    { this.qualDocPath = qualDocPath; }

    public String getStatus()                         { return status; }
    public void setStatus(String status)              { this.status = status; }

    public String getCreatedAt()                      { return createdAt; }
    public void setCreatedAt(String createdAt)        { this.createdAt = createdAt; }

    public String getRejectionReason()                { return rejectionReason; }
    public void setRejectionReason(String r)          { this.rejectionReason = r; }
}