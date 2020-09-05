package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by admin on 2/20/2017.
 */

public class StCoursesDAO {

    String id = "";
    String type_name = "";
    String course_name = "";
    String course_code = "";
    String user_id = "";
    String type_name_id="";
    String numbers="";

    public StCoursesDAO() {

    }

    public StCoursesDAO(String id, String type_name, String course_name, String course_code, String user_id, String type_name_id, String numbers) {
        this.id = id;
        this.type_name = type_name;
        this.course_name = course_name;
        this.course_code = course_code;
        this.user_id = user_id;
        this.type_name_id = type_name_id;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getType_name_id() {
        return type_name_id;
    }

    public void setType_name_id(String type_name_id) {
        this.type_name_id = type_name_id;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
