package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class UserFeedbacksDAO {
    String id="";
    String user_id="";
    String Header="";
    String feedback="";
    String Footer="";
    String mobile_no="";
    String email_id="";
    String student_name="";
    String FeedbackDate="";
    String numbers="";

    public UserFeedbacksDAO()
    {
    }

    public UserFeedbacksDAO(String id, String user_id, String header, String feedback, String footer, String mobile_no, String email_id, String student_name, String feedbackDate, String numbers) {
        this.id = id;
        this.user_id = user_id;
        Header = header;
        this.feedback = feedback;
        Footer = footer;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.student_name = student_name;
        FeedbackDate = feedbackDate;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHeader() {
        return Header;
    }

    public void setHeader(String header) {
        Header = header;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFooter() {
        return Footer;
    }

    public void setFooter(String footer) {
        Footer = footer;
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

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getFeedbackDate() {
        return FeedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        FeedbackDate = feedbackDate;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
