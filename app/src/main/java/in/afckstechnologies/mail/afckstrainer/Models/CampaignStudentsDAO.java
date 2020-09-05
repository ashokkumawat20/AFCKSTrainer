package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class CampaignStudentsDAO {

    String user_id="";
    String campaign_id="";
    String Student_Name="";
    String first_name="";
    String mobile_no="";
    String email_id="";
    String gender="";
    String sourse="";
    String numbers="";
    String notes="";
    String starred="";
    private boolean isSelected;
    public CampaignStudentsDAO()
    {
    }

    public CampaignStudentsDAO(String user_id, String campaign_id, String student_Name, String first_name, String mobile_no, String email_id, String gender, String sourse, String numbers, String notes, String starred, boolean isSelected) {
        this.user_id = user_id;
        this.campaign_id = campaign_id;
        Student_Name = student_Name;
        this.first_name = first_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.gender = gender;
        this.sourse = sourse;
        this.numbers = numbers;
        this.notes = notes;
        this.starred = starred;
        this.isSelected = isSelected;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStarred() {
        return starred;
    }

    public void setStarred(String starred) {
        this.starred = starred;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
