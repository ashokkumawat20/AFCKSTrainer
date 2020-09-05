package in.afckstechnologies.mail.afckstrainer.Models;

/**
 * Created by DHIRAJ on 1/11/2016.
 */
public class RequestChangeAttachListDAO {
    String id="";
    String reqest_change_id="";
    String assign_to_user_id="";
    String path="";

    public RequestChangeAttachListDAO()
    {}

    public RequestChangeAttachListDAO(String id, String reqest_change_id, String assign_to_user_id, String path) {
        this.id = id;
        this.reqest_change_id = reqest_change_id;
        this.assign_to_user_id = assign_to_user_id;
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReqest_change_id() {
        return reqest_change_id;
    }

    public void setReqest_change_id(String reqest_change_id) {
        this.reqest_change_id = reqest_change_id;
    }

    public String getAssign_to_user_id() {
        return assign_to_user_id;
    }

    public void setAssign_to_user_id(String assign_to_user_id) {
        this.assign_to_user_id = assign_to_user_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
