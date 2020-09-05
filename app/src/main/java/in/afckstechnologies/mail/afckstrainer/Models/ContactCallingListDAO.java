package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/30/2017.
 */

public class ContactCallingListDAO {
    String id="";
    String first_name="";
    String last_name="";
    String email_id="";
    String mobile_no="";
    String date="";
    String status="";
    String start_date="";
    String end_date="";
    String notes="";
    public ContactCallingListDAO()
    {

    }

    public ContactCallingListDAO(String id, String first_name, String last_name, String email_id, String mobile_no, String date, String status, String start_date, String end_date, String notes) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email_id = email_id;
        this.mobile_no = mobile_no;
        this.date = date;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
