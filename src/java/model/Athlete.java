
package model;
import java.util.Date;

public class Athlete {

    private long athleteId;
    private String fullName;
    private Date dob;
    private int age;
    private String gender;
    private String mobile;
    private String email;
    private String bloodGroup;
    private String status;
    private String createdAt;


    public long getAthleteId() 
    {
        return athleteId;
    }

    public void setAthleteId(long athleteId) {
        this.athleteId = athleteId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getCreatedAt() 
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) 
    {
        this.createdAt = createdAt;
    }
}