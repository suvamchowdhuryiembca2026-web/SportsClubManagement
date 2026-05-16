package model;

public class Documents {

    private long docId;
    private long athleteId;
    private String photoPath;
    private String birthCertPath;
    private String idProofPath;

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(long athleteId) {
        this.athleteId = athleteId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getBirthCertPath() {
        return birthCertPath;
    }

    public void setBirthCertPath(String birthCertPath) {
        this.birthCertPath = birthCertPath;
    }

    public String getIdProofPath() {
        return idProofPath;
    }

    public void setIdProofPath(String idProofPath) {
        this.idProofPath = idProofPath;
    }
}