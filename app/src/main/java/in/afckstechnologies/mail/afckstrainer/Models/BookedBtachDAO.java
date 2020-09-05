package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class BookedBtachDAO {

    String book_id="";
    String user_id="";
    String status="";
    String batch_code="";
    String Students_Name="";
    String numbers="";

    public BookedBtachDAO()
    {
    }


    public BookedBtachDAO(String book_id, String user_id, String status, String batch_code, String students_Name, String numbers) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.status = status;
        this.batch_code = batch_code;
        Students_Name = students_Name;
        this.numbers = numbers;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatch_code() {
        return batch_code;
    }

    public void setBatch_code(String batch_code) {
        this.batch_code = batch_code;
    }

    public String getStudents_Name() {
        return Students_Name;
    }

    public void setStudents_Name(String students_Name) {
        Students_Name = students_Name;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
