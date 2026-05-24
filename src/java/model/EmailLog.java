
package model;

public class EmailLog {
    private long mailId;
    private String userEmail;
    private String subject;
    private String status;
    private String sentAt;
    public long getMailId() { return mailId; }
    public void setMailId(long mailId) { this.mailId = mailId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSentAt() { return sentAt; }
    public void setSentAt(String sentAt) { this.sentAt = sentAt; }

}