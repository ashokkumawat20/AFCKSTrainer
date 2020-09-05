package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class StudentsFeedbackListDAO {

    String id = "";
    String BatchID = "";
    String first_name = "";
    String last_name = "";
    String mobile_no="";
    String email_id = "";
    String feedback_date = "";
    String Q1 = "";
    String Q2 = "";
    String Q3 = "";
    String Feedback = "";
    String numbers="";

    public StudentsFeedbackListDAO() {
    }

    public StudentsFeedbackListDAO(String id, String batchID, String first_name, String last_name, String mobile_no, String email_id, String feedback_date, String q1, String q2, String q3, String feedback, String numbers) {
        this.id = id;
        BatchID = batchID;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.feedback_date = feedback_date;
        Q1 = q1;
        Q2 = q2;
        Q3 = q3;
        Feedback = feedback;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
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

    public String getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(String feedback_date) {
        this.feedback_date = feedback_date;
    }

    public String getQ1() {
        return Q1;
    }

    public void setQ1(String q1) {
        Q1 = q1;
    }

    public String getQ2() {
        return Q2;
    }

    public void setQ2(String q2) {
        Q2 = q2;
    }

    public String getQ3() {
        return Q3;
    }

    public void setQ3(String q3) {
        Q3 = q3;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String feedback) {
        Feedback = feedback;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
