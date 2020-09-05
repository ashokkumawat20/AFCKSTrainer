package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 5/4/2017.
 */

public class BatchesForStudentsDAO {
    String id = "";
    String course_id = "";
    String branch_id = "";
    String batch_code = "";
    String start_date = "";
    String timings = "";
    String notes = "";
    String frequency = "";
    String fees = "";
    String duration = "";
    String course_name="";
    String branch_name="";
    String batchtype="";
    String completion_status="";
    String btach_end_date="";
    String faculty_Name="";
    public BatchesForStudentsDAO() {

    }

    public BatchesForStudentsDAO(String id, String course_id, String branch_id, String batch_code, String start_date, String timings, String notes, String frequency, String fees, String duration, String course_name, String branch_name, String batchtype, String completion_status, String btach_end_date, String faculty_Name) {
        this.id = id;
        this.course_id = course_id;
        this.branch_id = branch_id;
        this.batch_code = batch_code;
        this.start_date = start_date;
        this.timings = timings;
        this.notes = notes;
        this.frequency = frequency;
        this.fees = fees;
        this.duration = duration;
        this.course_name = course_name;
        this.branch_name = branch_name;
        this.batchtype = batchtype;
        this.completion_status = completion_status;
        this.btach_end_date = btach_end_date;
        this.faculty_Name = faculty_Name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBatchtype() {
        return batchtype;
    }

    public void setBatchtype(String batchtype) {
        this.batchtype = batchtype;
    }

    public String getCompletion_status() {
        return completion_status;
    }

    public void setCompletion_status(String completion_status) {
        this.completion_status = completion_status;
    }

    public String getBtach_end_date() {
        return btach_end_date;
    }

    public void setBtach_end_date(String btach_end_date) {
        this.btach_end_date = btach_end_date;
    }

    public String getFaculty_Name() {
        return faculty_Name;
    }

    public void setFaculty_Name(String faculty_Name) {
        this.faculty_Name = faculty_Name;
    }
}
