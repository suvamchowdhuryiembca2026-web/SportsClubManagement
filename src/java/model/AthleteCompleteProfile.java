package model;

public class AthleteCompleteProfile
{
    private Athlete athlete;
    private Guardian guardian;
    private Address address;
    private SportsProfile sportsProfile;
    private Documents documents;

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Guardian getGuardian() {
        return guardian;
    }

    public void setGuardian(Guardian guardian) {
        this.guardian = guardian;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public SportsProfile getSportsProfile() {
        return sportsProfile;
    }

    public void setSportsProfile(SportsProfile sportsProfile) {
        this.sportsProfile = sportsProfile;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }
}