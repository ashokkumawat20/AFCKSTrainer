package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class StudentsInBatchListDAO {

    String batch_code="";
    String Student_Name="";
    String first_name="";
    String mobile_no="";
    String email_id="";
    String gender="";
    String course_name="";
    String start_date="";
    String expiry_date="";
    String Fees="";
    String BaseFees="";
    String TotalMOS="";
    String TotalIGST="";
    String TotalSGST="";
    String TotalCGST="";
    String Status="";
    String previous_attendance="";
    String discontinue_reason="";
    public StudentsInBatchListDAO()
    {
    }

    public StudentsInBatchListDAO(String batch_code, String student_Name, String first_name, String mobile_no, String email_id, String gender, String course_name, String start_date, String expiry_date, String fees, String baseFees, String totalMOS, String totalIGST, String totalSGST, String totalCGST, String status, String previous_attendance, String discontinue_reason) {
        this.batch_code = batch_code;
        Student_Name = student_Name;
        this.first_name = first_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.gender = gender;
        this.course_name = course_name;
        this.start_date = start_date;
        this.expiry_date = expiry_date;
        Fees = fees;
        BaseFees = baseFees;
        TotalMOS = totalMOS;
        TotalIGST = totalIGST;
        TotalSGST = totalSGST;
        TotalCGST = totalCGST;
        Status = status;
        this.previous_attendance = previous_attendance;
        this.discontinue_reason = discontinue_reason;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }

    public String getBaseFees() {
        return BaseFees;
    }

    public void setBaseFees(String baseFees) {
        BaseFees = baseFees;
    }

    public String getTotalMOS() {
        return TotalMOS;
    }

    public void setTotalMOS(String totalMOS) {
        TotalMOS = totalMOS;
    }

    public String getTotalIGST() {
        return TotalIGST;
    }

    public void setTotalIGST(String totalIGST) {
        TotalIGST = totalIGST;
    }

    public String getTotalSGST() {
        return TotalSGST;
    }

    public void setTotalSGST(String totalSGST) {
        TotalSGST = totalSGST;
    }

    public String getTotalCGST() {
        return TotalCGST;
    }

    public void setTotalCGST(String totalCGST) {
        TotalCGST = totalCGST;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPrevious_attendance() {
        return previous_attendance;
    }

    public void setPrevious_attendance(String previous_attendance) {
        this.previous_attendance = previous_attendance;
    }

    public String getDiscontinue_reason() {
        return discontinue_reason;
    }

    public void setDiscontinue_reason(String discontinue_reason) {
        this.discontinue_reason = discontinue_reason;
    }
}
