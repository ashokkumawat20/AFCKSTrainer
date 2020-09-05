package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/27/2017.
 */

public class ShowDemandCourseDAO {

    String course_id="";
    String CourseName="";
    String course_type_id ="";

    public ShowDemandCourseDAO(String course_id, String courseName, String course_type_id) {
        this.course_id = course_id;
        CourseName = courseName;
        this.course_type_id = course_type_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getCourse_type_id() {
        return course_type_id;
    }

    public void setCourse_type_id(String course_type_id) {
        this.course_type_id = course_type_id;
    }

    @Override
    public String toString() {
        return CourseName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BatchesDAO) {
            ShowDemandCourseDAO c = (ShowDemandCourseDAO) obj;
            if (c.getCourseName().equals(CourseName) && c.getCourse_id() == course_id) return true;
        }

        return false;
    }
}
