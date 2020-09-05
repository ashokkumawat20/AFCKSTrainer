package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by DHIRAJ on 1/11/2016.
 */
public class RequestChangeListDAO {
    String id="";
    String request_type_id="";
    String request_type_name="";
    String request_subject="";
    String request_body="";
    String user_id="";
    String assign_to_user_id="";
    String assign_user_name="";
    String create_user_name="";
    String user_name="";
    String expected_date="";
    String expected_time="";
    String date_time="";
    String status="";
    String ticket_comments="";
    String ticket_close_date="";
    String create_user_mobile_no="";
    String assign_user_mobile_no="";
    String ticket_priority_status="";
    String attachment_count="";
    String pending_days="";
    String buzz_count="";
    String read_status="";
    String Numbers="";
    String department_id="";
    public RequestChangeListDAO()
    {}

    public RequestChangeListDAO(String id, String request_type_id, String request_type_name, String request_subject, String request_body, String user_id, String assign_to_user_id, String assign_user_name, String create_user_name, String user_name, String expected_date, String expected_time, String date_time, String status, String ticket_comments, String ticket_close_date, String create_user_mobile_no, String assign_user_mobile_no, String ticket_priority_status, String attachment_count, String pending_days, String buzz_count, String read_status, String numbers, String department_id) {
        this.id = id;
        this.request_type_id = request_type_id;
        this.request_type_name = request_type_name;
        this.request_subject = request_subject;
        this.request_body = request_body;
        this.user_id = user_id;
        this.assign_to_user_id = assign_to_user_id;
        this.assign_user_name = assign_user_name;
        this.create_user_name = create_user_name;
        this.user_name = user_name;
        this.expected_date = expected_date;
        this.expected_time = expected_time;
        this.date_time = date_time;
        this.status = status;
        this.ticket_comments = ticket_comments;
        this.ticket_close_date = ticket_close_date;
        this.create_user_mobile_no = create_user_mobile_no;
        this.assign_user_mobile_no = assign_user_mobile_no;
        this.ticket_priority_status = ticket_priority_status;
        this.attachment_count = attachment_count;
        this.pending_days = pending_days;
        this.buzz_count = buzz_count;
        this.read_status = read_status;
        Numbers = numbers;
        this.department_id = department_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_type_id() {
        return request_type_id;
    }

    public void setRequest_type_id(String request_type_id) {
        this.request_type_id = request_type_id;
    }

    public String getRequest_type_name() {
        return request_type_name;
    }

    public void setRequest_type_name(String request_type_name) {
        this.request_type_name = request_type_name;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAssign_to_user_id() {
        return assign_to_user_id;
    }

    public void setAssign_to_user_id(String assign_to_user_id) {
        this.assign_to_user_id = assign_to_user_id;
    }

    public String getAssign_user_name() {
        return assign_user_name;
    }

    public void setAssign_user_name(String assign_user_name) {
        this.assign_user_name = assign_user_name;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getExpected_date() {
        return expected_date;
    }

    public void setExpected_date(String expected_date) {
        this.expected_date = expected_date;
    }

    public String getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(String expected_time) {
        this.expected_time = expected_time;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicket_comments() {
        return ticket_comments;
    }

    public void setTicket_comments(String ticket_comments) {
        this.ticket_comments = ticket_comments;
    }

    public String getTicket_close_date() {
        return ticket_close_date;
    }

    public void setTicket_close_date(String ticket_close_date) {
        this.ticket_close_date = ticket_close_date;
    }

    public String getCreate_user_mobile_no() {
        return create_user_mobile_no;
    }

    public void setCreate_user_mobile_no(String create_user_mobile_no) {
        this.create_user_mobile_no = create_user_mobile_no;
    }

    public String getAssign_user_mobile_no() {
        return assign_user_mobile_no;
    }

    public void setAssign_user_mobile_no(String assign_user_mobile_no) {
        this.assign_user_mobile_no = assign_user_mobile_no;
    }

    public String getTicket_priority_status() {
        return ticket_priority_status;
    }

    public void setTicket_priority_status(String ticket_priority_status) {
        this.ticket_priority_status = ticket_priority_status;
    }

    public String getAttachment_count() {
        return attachment_count;
    }

    public void setAttachment_count(String attachment_count) {
        this.attachment_count = attachment_count;
    }

    public String getPending_days() {
        return pending_days;
    }

    public void setPending_days(String pending_days) {
        this.pending_days = pending_days;
    }

    public String getBuzz_count() {
        return buzz_count;
    }

    public void setBuzz_count(String buzz_count) {
        this.buzz_count = buzz_count;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public String getNumbers() {
        return Numbers;
    }

    public void setNumbers(String numbers) {
        Numbers = numbers;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
}
