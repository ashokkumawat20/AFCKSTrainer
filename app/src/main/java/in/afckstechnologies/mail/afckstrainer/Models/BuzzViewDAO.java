package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by Ashok Kumawat on 3/22/2018.
 */

public class BuzzViewDAO {
    String id="";
    String date_time = "";
    String assign_to_user_id = "";
    String create_user_name = "";
    String status = "";
    String request_subject="";
    String request_body="";
    String money_status="";
    String ticket_priority_status = "";
    String ticket_cat_status = "";
    String path = "";
    String task_id="";
    String buzz_count="";
    String numbers="";
    public BuzzViewDAO() {

    }

    public BuzzViewDAO(String id, String date_time, String assign_to_user_id, String create_user_name, String status, String request_subject, String request_body, String money_status, String ticket_priority_status, String ticket_cat_status, String path, String task_id, String buzz_count, String numbers) {
        this.id = id;
        this.date_time = date_time;
        this.assign_to_user_id = assign_to_user_id;
        this.create_user_name = create_user_name;
        this.status = status;
        this.request_subject = request_subject;
        this.request_body = request_body;
        this.money_status = money_status;
        this.ticket_priority_status = ticket_priority_status;
        this.ticket_cat_status = ticket_cat_status;
        this.path = path;
        this.task_id = task_id;
        this.buzz_count = buzz_count;
        this.numbers = numbers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getAssign_to_user_id() {
        return assign_to_user_id;
    }

    public void setAssign_to_user_id(String assign_to_user_id) {
        this.assign_to_user_id = assign_to_user_id;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_subject() {
        return request_subject;
    }

    public void setRequest_subject(String request_subject) {
        this.request_subject = request_subject;
    }

    public String getRequest_body() {
        return request_body;
    }

    public void setRequest_body(String request_body) {
        this.request_body = request_body;
    }

    public String getMoney_status() {
        return money_status;
    }

    public void setMoney_status(String money_status) {
        this.money_status = money_status;
    }

    public String getTicket_priority_status() {
        return ticket_priority_status;
    }

    public void setTicket_priority_status(String ticket_priority_status) {
        this.ticket_priority_status = ticket_priority_status;
    }

    public String getTicket_cat_status() {
        return ticket_cat_status;
    }

    public void setTicket_cat_status(String ticket_cat_status) {
        this.ticket_cat_status = ticket_cat_status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getBuzz_count() {
        return buzz_count;
    }

    public void setBuzz_count(String buzz_count) {
        this.buzz_count = buzz_count;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
