package model;

public class EventRegistration {

    private long   regId;
    private long   eventId;
    private long   userId;
    private String userType;           // ATHLETE / COACH
    private String paymentStatus;      // PENDING / PAID
    private String registrationStatus; // REGISTERED / CANCELLED
    private String registeredAt;

    // Joined fields (not in table — filled by DAO joins)
    private String eventName;
    private String sportType;
    private String eventDate;
    private String location;
    private double registrationFee;

    // ── Getters & Setters ─────────────────────────────────────────────────────

    public long   getRegId()                              { return regId; }
    public void   setRegId(long regId)                    { this.regId = regId; }

    public long   getEventId()                            { return eventId; }
    public void   setEventId(long eventId)                { this.eventId = eventId; }

    public long   getUserId()                             { return userId; }
    public void   setUserId(long userId)                  { this.userId = userId; }

    public String getUserType()                           { return userType; }
    public void   setUserType(String userType)            { this.userType = userType; }

    public String getPaymentStatus()                      { return paymentStatus; }
    public void   setPaymentStatus(String paymentStatus)  { this.paymentStatus = paymentStatus; }

    public String getRegistrationStatus()                 { return registrationStatus; }
    public void   setRegistrationStatus(String s)         { this.registrationStatus = s; }

    public String getRegisteredAt()                       { return registeredAt; }
    public void   setRegisteredAt(String registeredAt)    { this.registeredAt = registeredAt; }

    // Joined
    public String getEventName()                          { return eventName; }
    public void   setEventName(String eventName)          { this.eventName = eventName; }

    public String getSportType()                          { return sportType; }
    public void   setSportType(String sportType)          { this.sportType = sportType; }

    public String getEventDate()                          { return eventDate; }
    public void   setEventDate(String eventDate)          { this.eventDate = eventDate; }

    public String getLocation()                           { return location; }
    public void   setLocation(String location)            { this.location = location; }

    public double getRegistrationFee()                    { return registrationFee; }
    public void   setRegistrationFee(double fee)          { this.registrationFee = fee; }
}