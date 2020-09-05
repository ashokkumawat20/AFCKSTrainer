package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactEnquiriesListDAO {
    String id = "";
    String full_name = "";
    String mobile_number = "";
    String looking_for = "";
    String requirement = "";
    String call_status = "";
    String caller_comments = "";
    String starred = "";
    String date_of_enquiry = "";

    public ContactEnquiriesListDAO() {

    }

    public ContactEnquiriesListDAO(String id, String full_name, String mobile_number, String looking_for, String requirement, String call_status, String caller_comments, String starred, String date_of_enquiry) {
        this.id = id;
        this.full_name = full_name;
        this.mobile_number = mobile_number;
        this.looking_for = looking_for;
        this.requirement = requirement;
        this.call_status = call_status;
        this.caller_comments = caller_comments;
        this.starred = starred;
        this.date_of_enquiry = date_of_enquiry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getCall_status() {
        return call_status;
    }

    public void setCall_status(String call_status) {
        this.call_status = call_status;
    }

    public String getCaller_comments() {
        return caller_comments;
    }

    public void setCaller_comments(String caller_comments) {
        this.caller_comments = caller_comments;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    public String getDate_of_enquiry() {
        return date_of_enquiry;
    }

    public void setDate_of_enquiry(String date_of_enquiry) {
        this.date_of_enquiry = date_of_enquiry;
    }
}
