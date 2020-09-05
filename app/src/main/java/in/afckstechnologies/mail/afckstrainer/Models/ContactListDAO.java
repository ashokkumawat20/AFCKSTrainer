package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactListDAO {

    String name="";
    String mobileNumber="";
    String emailId="";
    String dateTime="";
    String callType="";
    public ContactListDAO()
    {

    }

    public ContactListDAO(String name, String mobileNumber, String emailId, String dateTime, String callType) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.dateTime = dateTime;
        this.callType = callType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
