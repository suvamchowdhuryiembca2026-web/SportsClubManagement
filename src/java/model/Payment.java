package model;

public class Payment
{
    private long paymentId;

    private long athleteId;

    private String paymentType;

    private double amount;

    private String paymentStatus;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String paymentDate;

    public long getPaymentId()
    {
        return paymentId;
    }

    public void setPaymentId(long paymentId)
    {
        this.paymentId = paymentId;
    }

    public long getAthleteId()
    {
        return athleteId;
    }

    public void setAthleteId(long athleteId)
    {
        this.athleteId = athleteId;
    }

    public String getPaymentType()
    {
        return paymentType;
    }

    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getPaymentStatus()
    {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }

    public String getRazorpayOrderId()
    {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId)
    {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId()
    {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId)
    {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate)
    {
        this.paymentDate = paymentDate;
    }
}