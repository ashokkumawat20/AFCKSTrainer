package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by admin on 2/18/2017.
 */

public class StCenterDAO {
    String id = "";
    String branch_name = "";
    String user_id = "";


    public StCenterDAO() {
    }

    public StCenterDAO(String id, String branch_name, String user_id) {
        this.id = id;
        this.branch_name = branch_name;
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
