package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok Kumawat on 7/27/2017.
 */

public class FeedbackDateDAO {
    String faculty_id="";
    String feedback_date="";
    String format_feedback_date="";
    public FeedbackDateDAO()
    {

    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(String feedback_date) {
        this.feedback_date = feedback_date;
    }

    public String getFormat_feedback_date() {
        return format_feedback_date;
    }

    public void setFormat_feedback_date(String format_feedback_date) {
        this.format_feedback_date = format_feedback_date;
    }

    public FeedbackDateDAO(String faculty_id, String feedback_date, String format_feedback_date) {

        this.faculty_id = faculty_id;
        this.feedback_date = feedback_date;
        this.format_feedback_date = format_feedback_date;
    }

    @Override
    public String toString() {
        return format_feedback_date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FeedbackDateDAO) {
            FeedbackDateDAO c = (FeedbackDateDAO) obj;
            if (c.getFormat_feedback_date().equals(format_feedback_date))
                return true;
        }

        return false;
    }
}
