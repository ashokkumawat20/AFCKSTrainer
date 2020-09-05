package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class TrainersDAO {

    String id = "";
    String first_name = "";
    String mobile_no = "";
    String email_id = "";
    String numbers = "";


    public TrainersDAO() {

    }

    public TrainersDAO(String id, String first_name, String mobile_no, String email_id, String numbers) {
        this.id = id;
        this.first_name = first_name;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.numbers = numbers;
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

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
