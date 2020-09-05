package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok on 4/18/2017.
 */

public class EmpBuzzHistroyDetailsDAO {
    String id="";
    String task_id="";
    String buzz_dates="";
    String remarks="";

    String status="";
    String numbers="";
    public EmpBuzzHistroyDetailsDAO()
    {
    }

    public EmpBuzzHistroyDetailsDAO(String id, String task_id, String buzz_dates, String remarks, String status, String numbers) {
        this.id = id;
        this.task_id = task_id;
        this.buzz_dates = buzz_dates;
        this.remarks = remarks;
        this.status = status;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getBuzz_dates() {
        return buzz_dates;
    }

    public void setBuzz_dates(String buzz_dates) {
        this.buzz_dates = buzz_dates;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
