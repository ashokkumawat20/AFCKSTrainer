package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class StudentsDAO {

    String batchid = "";
    String TotalFees = "";
    String user_id = "";
    String sbd_id = "";
    String Students_Name = "";
    String first_name = "";
    String mobile_no = "";
    String email_id = "";
    String gender = "";
    String corporate = "";

    String notes_id = "";

    String Discontinued = "";
    String Status = "";
    String numbers = "";
    String Notes = "";
    String Student_Name = "";
    String course_id = "";
    String branch_id = "";
    String sourse = "";

    String notes = "";
    String previous_attendance = "";
    String due_amount = "";
    private boolean isSelected;

    public StudentsDAO() {

    }

    public StudentsDAO(String batchid, String totalFees, String user_id, String sbd_id, String students_Name, String first_name, String mobile_no, String email_id, String gender, String corporate, String notes_id, String discontinued, String status, String numbers, String notes, String student_Name, String course_id, String branch_id, String sourse, String notes1, String previous_attendance, String due_amount, boolean isSelected) {
        this.batchid = batchid;
        TotalFees = totalFees;
        this.user_id = user_id;
        this.sbd_id = sbd_id;
        Students_Name = students_Name;
        this.first_name = first_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.gender = gender;
        this.corporate = corporate;
        this.notes_id = notes_id;
        Discontinued = discontinued;
        Status = status;
        this.numbers = numbers;
        Notes = notes;
        Student_Name = student_Name;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.sourse = sourse;
        this.notes = notes1;
        this.previous_attendance = previous_attendance;
        this.due_amount = due_amount;
        this.isSelected = isSelected;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

    public String getTotalFees() {
        return TotalFees;
    }

    public void setTotalFees(String totalFees) {
        TotalFees = totalFees;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSbd_id() {
        return sbd_id;
    }

    public void setSbd_id(String sbd_id) {
        this.sbd_id = sbd_id;
    }

    public String getStudents_Name() {
        return Students_Name;
    }

    public void setStudents_Name(String students_Name) {
        Students_Name = students_Name;
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

    public String getCorporate() {
        return corporate;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getNotes_id() {
        return notes_id;
    }

    public void setNotes_id(String notes_id) {
        this.notes_id = notes_id;
    }

    public String getDiscontinued() {
        return Discontinued;
    }

    public void setDiscontinued(String discontinued) {
        Discontinued = discontinued;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getPrevious_attendance() {
        return previous_attendance;
    }

    public void setPrevious_attendance(String previous_attendance) {
        this.previous_attendance = previous_attendance;
    }

    public String getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(String due_amount) {
        this.due_amount = due_amount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }
}
